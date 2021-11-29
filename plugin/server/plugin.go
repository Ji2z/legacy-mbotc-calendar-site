package main

import (
	"io/ioutil"
	"path/filepath"
	"sync"
	"fmt"
	"github.com/mattermost/mattermost-server/v5/model"
	"github.com/mattermost/mattermost-server/v5/plugin"

	"github.com/pkg/errors"
)

const (
	botUserName    = "mbotc"
	botDisplayName = "MBotC"
	botDescription = "Created by the MBotC plugin."
	clientUrl      = "https://k5a103.p.ssafy.io"
	serviceAPIUrl  = "http://k5a103.p.ssafy.io:8080"
	pluginId       = "com.mattermost.plugin.mbotc"
	timezone       = "Asia/Seoul"
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
	fmt.Println("@@@@@@@@@@@@OnActivate 시작")
	if p.API.GetConfig().ServiceSettings.SiteURL == nil {
		fmt.Println("SiteURL에서 오류")
		return errors.New("We couldn't find a siteURL. Please set a siteURL and restart the plugin")
	}
	fmt.Println(p.API.GetConfig().ServiceSettings.SiteURL)
	botUserID, err := p.Helpers.EnsureBot(&model.Bot{
		Username:    botUserName,
		DisplayName: botDisplayName,
		Description: botDescription,
	})

	if err != nil {
		fmt.Println("@@@@@@@@@@@@봇 계정 생성 실패")
		return errors.Wrap(err, "failed to create bot account")
	}
	p.botUserID = botUserID
	fmt.Printf("######botUserID : %s", p.botUserID)
	bundlePath, err := p.API.GetBundlePath()
	if err != nil {
		fmt.Println("@@@@@@@@@@@@bundle path 가져오기 실패")
		return errors.Wrap(err, "couldn't get bundle path")
	}

	profileImage, err := ioutil.ReadFile(filepath.Join(bundlePath, "assets", "profile.png"))
	if err != nil {
		fmt.Println("@@@@@@@@@@@@read profile image 실패")
		return errors.Wrap(err, "couldn't read profile image")
	}

	if appErr := p.API.SetProfileImage(botUserID, profileImage); appErr != nil {
		fmt.Println("@@@@@@@@@@@@set profile image 실패")
		return errors.Wrap(appErr, "couldn't set profile image")
	}

	command, err := p.getCommand()
	if err != nil {
		fmt.Println("@@@@@@@@@@@@get Command 실패")
		return errors.Wrap(err, "failed to get command")
	}

	err = p.API.RegisterCommand(command)
	if err != nil {
		fmt.Println("@@@@@@@@@@@@Register Command 실패")
		return errors.WithMessage(err, "OnActivate: failed to register command")
	}

	fmt.Println("@@@@@@@@@@@@OnActivate 모두 성공")
	return nil
}
