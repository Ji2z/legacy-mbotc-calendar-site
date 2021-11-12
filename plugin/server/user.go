package main

import (
	"fmt"
	"net/http"

	"github.com/mattermost/mattermost-server/v5/model"
	"github.com/pkg/errors"
)

func checkAuthentication(p *Plugin, commandArgs *model.CommandArgs) error {
	resp, err := checkUserExists(p, commandArgs.UserId)

	if err != nil {
		p.postCommandResponse(commandArgs, "Oops! Something wrong")
		return err
	}

	if resp.StatusCode == 404 {
		var text = "Please login first to use MBotC service.\n" +
		"[Login here](" + clientUrl + ")"
		p.postCommandResponse(commandArgs, text)
		return errors.New("USER NOT FOUND")
	}

	return nil
}

// Check if user exists in Database
func checkUserExists(p *Plugin, userId string) (*http.Response, error) {
	requestUrl := serviceAPIUrl + "/api/v1/user"
	req, err := http.NewRequest("GET", requestUrl, nil)
	if err != nil {
		fmt.Println("NewRequest Error: ", err)
		panic(err)
	}
	req.Header.Add("userId", userId)

	client := &http.Client{}
	resp, err := client.Do(req)
	if err != nil {
		fmt.Println("client.Do Error: ", err)
	}
	defer resp.Body.Close()

	return resp, err
}
