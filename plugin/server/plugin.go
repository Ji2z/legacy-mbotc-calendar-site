package main

import (
	"fmt"
	"net/http"
	"io/ioutil"
	"path/filepath"
	"strings"
	"sync"

	"github.com/mattermost/mattermost-server/v6/plugin"
)

const (
	botUserName    = "mbotc"
	botDisplayName = "MBotC"
	botDescription = "Created by the MBotC plugin."
)

// Plugin implements the interface expected by the Mattermost server to communicate between the server and plugin processes.
type Plugin struct {
	plugin.MattermostPlugin

	// botUserID of the created bot account.
	botUserID string

	// configurationLock synchronizes access to the configuration.
	configurationLock sync.RWMutex

	// configuration is the active plugin configuration. Consult getConfiguration and
	// setConfiguration for usage.
	configuration *configuration
}

// OnActivate checks if the configurations is valid and ensures the bot account exists
func (p *Plugin) OnActivate() error {
	// 웹엑스 설정 가져오기 -> MBotC와 상관 無
	config := p.getConfiguration()

	// 봇을 생성하고 err 발생 시 err에 값 저장
	botUserID, err := p.Helpers.EnsureBot(&model.Bot{
		Username:    botUserName,
		DisplayName: botDisplayName,
		Description: botDescription,
	})
	// err에 값이 들어있으면 return해버리고
	if err != nil {
		return errors.Wrap(err, "failed to ensure bot account")
	}
	// botUserID에 성공적으로 값이 들어오면 할당한다.
	p.botUserID = botUserID

	// 플러그인을 설치했을 경우 그 경로
	bundlePath, err := p.API.GetBundlePath()
	if err != nil {
		return errors.Wrap(err, "couldn't get bundle path")
	}

	// 번들경로/assets/profile.png에서 파일 읽어옴
	profileImage, err := ioutil.ReadFile(filepath.Join(bundlePath, "assets", "mbotc-logo.png"))
	if err != nil {
		return errors.Wrap(err, "couldn't read profile image")
	}

	// API 사용해서 bot 프로필 이미지 적용
	if appErr := p.API.SetProfileImage(botUserID, profileImage); appErr != nil {
		return errors.Wrap(appErr, "couldn't set profile image")
	}

	// p.store = NewStore(p)

	// command.go의 getCommand() - /webex 했을 때 나오는 설명
	command, err := p.getCommand()
	if err != nil {
		return errors.Wrap(err, "failed to get command")
	}

	// 86에서 가져온 command 등록
	err = p.API.RegisterCommand(command)
	if err != nil {
		return errors.WithMessage(err, "OnActivate: failed to register command")
	}

	return nil
}

func (p *Plugin) GetPluginURLPath() string {
	return "/plugins/" + manifest.ID
}

func (p *Plugin) GetPluginURL() string {
	return strings.TrimRight(p.GetSiteURL(), "/") + p.GetPluginURLPath()
}

func (p *Plugin) GetSiteURL() string {
	return *p.API.GetConfig().ServiceSettings.SiteURL
}

func (p *Plugin) errorf(f string, args ...interface{}) {
	p.API.LogError(fmt.Sprintf(f, args...))
}

// ServeHTTP demonstrates a plugin that handles HTTP requests by greeting the world.
func (p *Plugin) ServeHTTP(c *plugin.Context, w http.ResponseWriter, r *http.Request) {
	fmt.Fprint(w, "Hello, world!")
}

// See https://developers.mattermost.com/extend/plugins/server/reference/
