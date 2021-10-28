package main

import (
	"fmt"
	"strings"

	"github.com/mattermost/mattermost-plugin-api/experimental/command"
	"github.com/mattermost/mattermost-server/v5/model"
	"github.com/mattermost/mattermost-server/v6/plugin"
	"github.com/pkg/errors"
)

const helpText = "###### Mattermost MBotC Plugin - Slash Command Help\n" +
	"* `/mbotc help` - 도움말\n" +
	"* `/mbotc register` - 글을 등록할 수 있습니다."

const defaultRoomText = "not set (using your Mattermost email as the default)"

type CommandHandlerFunc func(p *Plugin, c *plugin.Context, header *model.CommandArgs, args ...string) *model.CommandResponse

type CommandHandler struct {
	handlers       map[string]CommandHandlerFunc
	defaultHandler CommandHandlerFunc
}

var webexCommandHandler = CommandHandler{
	handlers: map[string]CommandHandlerFunc{
		"help":       executeHelp,
		"info":       executeInfo,
		"start":      executeStart,
		"room":       executeRoom,
		"room-reset": executeRoomReset,
		"reset-room": executeRoomReset,
		"join":       executeStartWithArg, // Used as an alias for /webex <@username>/<room id> to allow for Autocomplete suggestions
	},
	defaultHandler: executeStartWithArg,
}

func (ch CommandHandler) Handle(p *Plugin, c *plugin.Context, header *model.CommandArgs, args ...string) *model.CommandResponse {
	for n := len(args); n > 0; n-- {
		h := ch.handlers[strings.Join(args[:n], "/")]
		if h != nil {
			return h(p, c, header, args[n:]...)
		}
	}
	return ch.defaultHandler(p, c, header, args...)
}

func executeHelp(p *Plugin, c *plugin.Context, header *model.CommandArgs, args ...string) *model.CommandResponse {
	return p.help(header)
}

func (p *Plugin) help(header *model.CommandArgs) *model.CommandResponse {
	p.postCommandResponse(header, helpText)
	return &model.CommandResponse{}
}

func (p *Plugin) ExecuteCommand(c *plugin.Context, commandArgs *model.CommandArgs) (*model.CommandResponse, *model.AppError) {
	args := strings.Fields(commandArgs.Command)
	if len(args) == 0 || args[0] != "/webex" {
		return p.help(commandArgs), nil
	}

	if !p.getConfiguration().IsValid() {
		return p.responsef(commandArgs, "The Webex plugin has not been configured correctly: the sitename has not been set. Please contact your system administrator."), nil
	}

	return webexCommandHandler.Handle(p, c, commandArgs, args[1:]...), nil
}

func (p *Plugin) getCommand() (*model.Command, error) {
	iconData, err := command.GetIconData(p.API, "assets/icon.svg")
	if err != nil {
		return nil, errors.Wrap(err, "failed to get icon data")
	}

	return &model.Command{
		Trigger:              "mbotc",
		DisplayName:          "mbotc",
		Description:          "Integration with MBotC.",
		AutoComplete:         true,
		AutoCompleteDesc:     "Available commands: help, register",
		AutoCompleteHint:     "[command]",
		AutocompleteData:     getAutocompleteData(),
		AutocompleteIconData: iconData,
	}, nil
}

func getAutocompleteData() *model.AutocompleteData {
	// 여기가 실제로 /mbotc 입력중에 보이는 부분
	webexAutocomplete := model.NewAutocompleteData("mbotc", "[command]", "Available commands: help, info, start, <room id/@username>, room, room-reset")

	// 여기가 "/mbotc "까지 쳤을 때 보이는 커맨드들
	help := model.NewAutocompleteData("help", "", "도움말")
	webexAutocomplete.AddCommand(help)

	register := model.NewAutocompleteData("register", "", "글을 등록합니다.")
	webexAutocomplete.AddCommand(register)

	return webexAutocomplete
}

func (p *Plugin) postCommandResponse(args *model.CommandArgs, text string) {
	post := &model.Post{
		UserId:    p.botUserID,
		ChannelId: args.ChannelId,
		Message:   text,
	}
	_ = p.API.SendEphemeralPost(args.UserId, post)
}

func (p *Plugin) responsef(commandArgs *model.CommandArgs, format string, args ...interface{}) *model.CommandResponse {
	p.postCommandResponse(commandArgs, fmt.Sprintf(format, args...))
	return &model.CommandResponse{}
}
