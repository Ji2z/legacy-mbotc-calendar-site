package com.ssafy.mbotc.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;

import com.ssafy.mbotc.dao.ChannelRepository;
import com.ssafy.mbotc.dao.TeamRepository;
import com.ssafy.mbotc.entity.Channel;
import com.ssafy.mbotc.entity.Team;

public class SyncService {
	
	@Autowired
	TeamRepository teamRepository;
	
	@Autowired
	ChannelRepository channelRepository;
	
	JSONParser parser = new JSONParser();
	
	public boolean syncTAndCAPI(String token, String mattermostUrl, String userId) {
		String GET_TEAM_LIST = "http://" + mattermostUrl + "/api/v4/users/"+userId+"/teams";
		StringBuilder response = new StringBuilder();
		
		try {
            URL url = new URL(GET_TEAM_LIST);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", "bearer "+token);
            conn.setRequestProperty("Content-Type", "application/json");
            
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
            if (conn.getResponseCode() != 200) {
                System.out.println("Failed: HTTP error code : " + conn.getResponseCode());
            	throw new RuntimeException("Failed: HTTP error code : " + conn.getResponseCode());
            } 
            
            String line = null;
            while((line = br.readLine()) != null){
                response.append(line);
            }
            br.close();           
            conn.disconnect();
        } catch (IOException e) {
            System.out.println("RestCall Fail : " + e.getMessage());
            return false;
        }
		
		System.out.println(response.toString());
		parseByTeam(response.toString(), token, mattermostUrl, userId);
		
		return true;
	}
	
	public void parseByTeam(String body, String token, String mattermostUrl, String userId) {
		// 팀 종류를 저장할 map <teamName, Idx>
		Map<String, Integer> teamIdx = new HashMap<>();
		// 팀 정보를 저장할 List<Team>
		List<Team> teams = new ArrayList<>();
		// 팀별 채널목록을 저장할 List[]
		List<Channel>[] channels = null;
		
		// 팀 돌면서 저장
		try {
			JSONArray content = (JSONArray) parser.parse(body);
			channels = new ArrayList[content.size()];
			for (int i = 0; i < content.size(); i++) {
				channels[i] = new ArrayList<>();
				JSONObject temp = (JSONObject) content.get(i);
				Team team = new Team();
				team.setName((String)temp.get("display_name"));
				team.setToken((String)temp.get("id"));
				teams.add(team);
				teamIdx.put(team.getToken(), i);
				channels[i] = getChannelsFromMM(token, mattermostUrl, userId, team.getToken());
				
				// redis에 구독채널 업데이트 하기 위해 저장되어 있는 값 불러오기
				
				
				// team token을 기준으로 db에서 존재하는지 여부 확인 후 존재한다면 channel token 갖고오기
				Optional<Team> isTeam = teamRepository.findByToken(team.getToken());
				if(isTeam.get() != null) { // 존재하는 팀 => 채널 검색
					List<Channel> savedChannels = channelRepository.findAllByTeamId(isTeam.get().getId());
					for (int j = 0; j < channels[i].size(); j++) {
						Channel c1 = channels[i].get(j);
						for (int k = 0; k < savedChannels.size(); k++) {
							Channel c2 = savedChannels.get(k);
							if(c1.getToken().equals(c2.getTeam())) // DB에 이미 저장되어있는 채널
								break;
							if(k+1==savedChannels.size()) { // DB에 없는 채널
								channelRepository.save(c1);
							}
						}
						
					}
				}else { // 저장 안된 팀 => 채널까지 모두 저장
					teamRepository.save(team);
					for (int j = 0; j < channels[i].size(); j++) {
						channelRepository.save(channels[i].get(i));
					}
				}
			}
			
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	public List<Channel> getChannelsFromMM(String token, String mattermostUrl, String userId, String teamId) {
		String GET_CHANNEL_LIST = "http://" + mattermostUrl + "/api/v4/users/"+userId+"/teams/"+teamId+"/channels";
		StringBuilder response = new StringBuilder();
		List<Channel> channels = new ArrayList<>();
		
		try {
            URL url = new URL(GET_CHANNEL_LIST);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", "bearer "+token);
            conn.setRequestProperty("Content-Type", "application/json");
            
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
            if (conn.getResponseCode() != 200) {
                System.out.println("Failed: HTTP error code : " + conn.getResponseCode());
            	throw new RuntimeException("Failed: HTTP error code : " + conn.getResponseCode());
            } 
            
            String line = null;
            while((line = br.readLine()) != null){
                response.append(line);
            }
            br.close();           
            conn.disconnect();
        } catch (IOException e) {
            System.out.println("RestCall Fail : " + e.getMessage());
        }
		
		System.out.println(response.toString());
		channels = parseByChannel(response.toString());
		
		return channels;
	}
	
	public List<Channel> parseByChannel(String body) {
		// 채널 정보를 저장할 List<Channel>
		List<Channel> channels = new ArrayList<>();
		
		// 채널 돌면서 저장
		try {
			JSONArray content = (JSONArray) parser.parse(body);
			for (int i = 0; i < content.size(); i++) {
				JSONObject temp = (JSONObject) content.get(i);
				char c = (char) temp.get("type");
				if(c=='O' || c=='P') { // 공개채널이나 비공개채널
					Channel channel = new Channel();
					channel.setName((String)temp.get("display_name"));
					channel.setToken((String)temp.get("id"));
					channels.add(channel);
				}	
			}	
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return channels;
	}
}
