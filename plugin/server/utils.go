package main

import (
	"bytes"
	"fmt"
	"io"
	"mime/multipart"
)

// Convert multipart file to []byte
func ConvertFileToByte(file multipart.File) ([]byte, error) {
	buf := bytes.NewBuffer(nil)
	if _, err := io.Copy(buf, file); err != nil {
		return nil, err
	}
	return buf.Bytes(), nil
}

// Upload file to mattermost channel and return file id
func UploadFileToMMChannel(p *Plugin, file []byte, channelId string, fileName string) string {
	resp, err := p.API.UploadFile(file, channelId, fileName)
	if err != nil {
		fmt.Println("UploadFile Error: ", err)
	}
	return resp.Id
}

// Get teamName and channelName by channelId
func SearchTeamNameAndChannelName(p *Plugin, channelId string) (teamName string, channelName string) {
	channel, err := p.API.GetChannel(channelId)
	if err != nil {
		fmt.Print("GetChannel Error", err)
	}
	team, err := p.API.GetTeam(channel.TeamId)
	if err != nil {
		fmt.Print("GetTeam Error", err)
	}

	return team.DisplayName, channel.DisplayName
}
