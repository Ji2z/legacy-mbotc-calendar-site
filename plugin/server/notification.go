package main

import (
	"bytes"
	"encoding/json"
	"fmt"
	"net/http"
	"regexp"
	"time"
	"io/ioutil"
	"strings"

	"github.com/pkg/errors"

	"github.com/mattermost/mattermost-server/v5/model"
)

type Notification struct {
	UserId    string   `json:"user_id"`
	Message   string   `json:"message"`
	StartTime string   `json:"start_time"`
	EndTime   string   `json:"end_time"`
	FileIds   []string `json:"file_ids"`
	ChannelId string   `json:"channel_id"`
	PostId    string   `json:"post_id"`
}

type DialogForm struct {
	Type       string `json:"type"`
	CallbackId string `json:"callback_id"`
	State      string `json:"state"`
	UserId     string `json:"user_id"`
	ChannelId  string `json:"channel_id"`
	TeamId     string `json:"team_id"`
	Submission Sub    `json:"submission"`
	Cancelled  bool   `json:"cancelled"`
}

type Sub struct {
	StartTime string `json:"start_time"`
	EndTime   string `json:"end_time"`
	Message   string `json:"message"`
}


// =================================================================================
// http Create Notification
// =================================================================================
// Create notification with command /mbotc create
func (p *Plugin) httpCreateNotificationWithCommand(w http.ResponseWriter, r *http.Request) {
	var err error
	notification, err := convertDialogForm(p, r)
	if err != nil {
		post := getConvertErrorPost(p, notification)
		p.API.SendEphemeralPost(notification.UserId, post)
		return
	}
	p.httpCreatePost(w, notification)
}

// Create notification with client
func (p *Plugin) httpCreateNotificationWithEditor(w http.ResponseWriter, r *http.Request) {
	fmt.Println("@@@@@@@@@@@@프론트에서 메시지 받아옴")
	notification := convertRequest(p, r)
	p.httpCreatePost(w, notification)
	fmt.Println("@@@@@@@@@@@@프론트에서 받아온 메시지 post함")
}

// Create notification with more action button
func (p *Plugin) httpCreateNotificationWithButton(r *http.Request) {
	var request struct {
		PostId string `json:"post_id"`
		UserId string `json:"user_id"`
	}
	err := json.NewDecoder(r.Body).Decode(&request)
	if err != nil {
		fmt.Print("PostId Decoding Error: ", err)
	}
	post, err := p.API.GetPost(request.PostId)

	if request.UserId != post.UserId {
		message := fmt.Sprintf("Only post owner can create notification")
		p.postEphemeralResponse(request.UserId, post.ChannelId, message)
		return
	}

	authErr := checkAuthentication(p, post.UserId, post.ChannelId)
	if authErr != nil {
		return
	}

	loc, _ := time.LoadLocation(timezone)
	currentTime := time.Now().In(loc)

	var notification Notification
	notification.UserId = post.UserId
	notification.Message = post.Message
	notification.StartTime = currentTime.Format("2006-01-02 15:04")
	notification.EndTime = currentTime.Format("2006-01-02") + " 23:59"
	notification.FileIds = post.FileIds
	notification.ChannelId = post.ChannelId
	notification.PostId = post.Id

	resPost := &model.Post{
		UserId:    p.botUserID,
		ChannelId: notification.ChannelId,
	}

	resp, err := postRequestToNotificationAPI(notification)
	if err != nil || resp.StatusCode != 200 {
		resPost.Message = "Registration failed"
		p.API.SendEphemeralPost(notification.UserId, resPost)
	} else {
		resPost.Message = "Registration success"
		reaction := &model.Reaction{
			UserId:    p.botUserID,
			PostId:    notification.PostId,
			EmojiName: "ok_hand",
		}
		p.API.AddReaction(reaction)
		p.API.SendEphemeralPost(notification.UserId, resPost)
	}
}


// =================================================================================
// Convert to Notification struct
// =================================================================================
// convert DialogFrom struct to Notification
func convertDialogForm(p *Plugin, r *http.Request) (Notification, error) {
	var notification Notification
	var dialogForm DialogForm

	err := json.NewDecoder(r.Body).Decode(&dialogForm)
	if err != nil {
		panic(err)
	}

	notification.UserId = dialogForm.UserId
	notification.Message = dialogForm.Submission.Message

	notification.StartTime = dialogForm.Submission.StartTime
	if dialogForm.Submission.EndTime == "" {
		notification.EndTime = dialogForm.Submission.StartTime
	} else {
		notification.EndTime = dialogForm.Submission.EndTime
	}
	notification.ChannelId = dialogForm.ChannelId
	re := regexp.MustCompile(`^\d{4}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])\s([01][0-9]|2[0-3]):([012345][0-9])$`)
	if !re.MatchString(notification.StartTime) || !re.MatchString(notification.EndTime) {
		return notification, errors.New("Validation Failed")
	}
	return notification, nil
}

// Return a post "an error occurred while converting DialogFrom struct to Notification"
func getConvertErrorPost(p *Plugin, notification Notification) *model.Post {
	if notification.StartTime == notification.EndTime {
		notification.EndTime = ""
	}

	return &model.Post{
		UserId:    p.botUserID,
		ChannelId: notification.ChannelId,
		Message: "Oops! Failed to Create Notification.\n" +
			"Your Input: \n" +
			"\nDate: " + notification.StartTime +
			"\nEnd date: " + notification.EndTime +
			"\nMessage: " + notification.Message,
	}
}

// convert Request form to Notification
func convertRequest(p *Plugin, r *http.Request) Notification {
	var notification Notification
	fmt.Println("@@@@@@@@@@@@convertRequest 시작")
	r.ParseMultipartForm(32 << 20) // maxMemory 32MB
	notification.UserId = r.PostFormValue("user_id")
	notification.Message = r.PostFormValue("message")
	notification.StartTime = r.PostFormValue("start_time")
	notification.EndTime = r.PostFormValue("end_time")
	if notification.EndTime == "" {
		notification.EndTime = notification.StartTime
	}
	notification.ChannelId = r.PostFormValue("channel_id")

	fmt.Println("@@@@@@@@@@@@file 제외한 formData 받아옴")
	fileheaders := r.MultipartForm.File["file"]
	fmt.Println("@@@@@@@ fileheaders len", len(fileheaders))
	for _, fileheader := range fileheaders {
		fmt.Println("@@@@@@ Filename", fileheader.Filename)
		file, err := fileheader.Open()
		if err != nil {
			fmt.Println("fileheader.Open() Error: ", err)
		}
		bytefile, err := ConvertFileToByte(file)
		if err != nil {
			fmt.Println("ConvertRequest Error: ", err)
		}
		fmt.Println("@@@@@@ mm에 file 업로드 전")
		notification.FileIds = append(notification.FileIds, UploadFileToMMChannel(p, bytefile, notification.ChannelId, fileheader.Filename))
		fmt.Println("@@@@@@ mm에 file 업로드 후 notification.FileIds에 append")
	}

	fmt.Println("@@@@@@@@@@@@convertRequest 끝")
	return notification
}


// =================================================================================
// Create notification
// =================================================================================
// Create mattermost post about notification
func (p *Plugin) httpCreatePost(w http.ResponseWriter, notification Notification) {
	fmt.Println("@@@@@@ httpCreatePost 들어옴")
	post := &model.Post{
		UserId:    p.botUserID,
		ChannelId: notification.ChannelId,
		FileIds:   notification.FileIds,
	}
	attachment, err := asSlackAttachment(p, notification)
	if err != nil {
		fmt.Println("asSlackAttachment Error: ", err)
		return
	}
	post.AddProp("attachments", attachment)

	resPost, appErr := p.API.CreatePost(post)
	if appErr != nil {
		fmt.Println("@@@@@@@@@@@@CreatePost 에러")
		http.Error(w, appErr.Error(), http.StatusInternalServerError)
		return
	}
	notification.PostId = resPost.Id
	postRequestToNotificationAPI(notification)
	fmt.Println("@@@@@@ httpCreatePost 끝")
}

// Send Post request to API for create notification
func postRequestToNotificationAPI(notification Notification) (*http.Response, error) {
	fmt.Println("@@@@@@ postRequestToNotificationAPI 들어옴")
	requestUrl := serviceAPIUrl + "/api/v1/notification"
	notificationJSON, err := json.Marshal(notification)
	if err != nil {
		fmt.Println("Notification Encoding Error: ", err)
	}
	resp, err := http.Post(requestUrl, "application/json", bytes.NewBuffer(notificationJSON))
	if err != nil {
		fmt.Println("ServiceAPI Request Error: ", err)
		panic(err)
	}
	defer resp.Body.Close()
	fmt.Println("@@@@@@ postRequestToNotificationAPI 끝")
	return resp, err
}

func asSlackAttachment(p *Plugin, notification Notification) ([]*model.SlackAttachment, error) {
	var text = notification.Message
	var fields []*model.SlackAttachmentField

	teamName, channelName := SearchTeamNameAndChannelName(p, notification.ChannelId)

	var postBy = teamName + " / " + channelName

	if notification.StartTime == notification.EndTime {
		fields = append(fields, &model.SlackAttachmentField{
			Title: ":calendar: Deadline",
			Value: notification.StartTime,
			Short: false,
		})
	} else {
		fields = append(fields, &model.SlackAttachmentField{
			Title: ":calendar: Start Time",
			Value: notification.StartTime,
			Short: true,
		})
		fields = append(fields, &model.SlackAttachmentField{
			Title: ":calendar: End Time",
			Value: notification.EndTime,
			Short: true,
		})
	}

	user, _ := p.API.GetUser(notification.UserId)
	fields = append(fields, &model.SlackAttachmentField{
		Title: ":lower_left_fountain_pen: Author",
		Value: user.Username,
		Short: false,
	})

	return []*model.SlackAttachment{
		{
			AuthorName: postBy,
			Color:      "#1352ab",
			Text:       text,
			Fields:     fields,
		},
	}, nil
}


// =================================================================================
// Create notification
// =================================================================================
// Get user's today notification list
func getNotificationList(p *Plugin, commandArgs *model.CommandArgs) {
	requestUrl := serviceAPIUrl + "/api/v1/notification/today"
	req, err := http.NewRequest("GET", requestUrl, nil)
	if err != nil {
		fmt.Println("NewRequest Error: ", err)
		panic(err)
	}

	req.Header.Add("userId", commandArgs.UserId)

	client := &http.Client{}
	resp, err := client.Do(req)
	if err != nil {
		fmt.Println("client.Do Error: ", err)
		panic(err)
	}
	defer resp.Body.Close()

	bytes, _ := ioutil.ReadAll(resp.Body)

	var dailyNotifications []DailyNotification
	json.Unmarshal(bytes, &dailyNotifications)

	post := &model.Post{
		UserId:    p.botUserID,
		ChannelId: commandArgs.ChannelId,
	}

	var text string

	text = "# Today's Notification\n" +
		"| Preview :loudspeaker: | Deadline :calendar: |\n" +
		"| --- | --- |\n"

	if len(dailyNotifications) == 0 {
		text += "| Nothing ... | - |\n"
	} else {
		for _, dn := range dailyNotifications {
			var message = strings.Replace(dn.Message, "\n", " ", -1)
			if len(message) >= 100 {
				message = message[:100] + " ..."
			}
			text += "| " + message + " | " + dn.EndTime + " | \n"
		}
	}

	loc, _ := time.LoadLocation(timezone)
	currentTime := time.Now().In(loc)
	text += "[See More](" + clientUrl + "/main/detail/" + currentTime.Format("20060102") + ")"
	var attachment = []*model.SlackAttachment{
		{
			Color: "#1352ab",
			Text:  text,
		},
	}
	post.AddProp("attachments", attachment)

	_ = p.API.SendEphemeralPost(commandArgs.UserId, post)
}
