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
import org.springframework.stereotype.Service;

import com.ssafy.mbotc.dao.ChannelRepository;
import com.ssafy.mbotc.dao.TeamRepository;
import com.ssafy.mbotc.entity.Channel;
import com.ssafy.mbotc.entity.Team;
import com.ssafy.mbotc.entity.response.ResRedisChannel;
import com.ssafy.mbotc.entity.response.ResRedisTeam;
import com.ssafy.mbotc.entity.response.ResRedisUser;

@Service
public class SyncService {
	
	 public String[] colors = {
	            "#39add1", // light blue
	            "#3079ab", // dark blue
	            "#c25975", // mauve
	            "#e15258", // red
	            "#f9845b", // orange
	            "#838cc7", // lavender
	            "#7d669e", // purple
	            "#53bbb4", // aqua
	            "#51b46d", // green
	            "#e0ab18", // mustard
	            "#637a91", // dark gray
	            "#f092b0", // pink
	            "#b7c0c7"  // light gray
	    };
	
	@Autowired
	TeamRepository teamRepository;
	
	@Autowired
	ChannelRepository channelRepository;
	
	@Autowired
	RedisService redisService;
	
	JSONParser parser = new JSONParser();
	
	public boolean syncTAndCAPI(String token, String mattermostUrl, String userId) {
		String GET_TEAM_LIST = mattermostUrl + "/api/v4/users/"+userId+"/teams";
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
		// 팀 정보를 저장할 List<Team>
		List<Team> teams = new ArrayList<>();
		// 팀별 채널목록을 저장할 List[]
		List<Channel>[] channels = null;
		// 새로운 사용자 userSetting 설정용
		List<ResRedisTeam> forNTeamList = new ArrayList<>();
		
		try {
			JSONArray content = (JSONArray) parser.parse(body);
			channels = new ArrayList[content.size()];
			
			// redis에 구독채널 업데이트 하기 위해 저장되어 있는 값 불러오기
			ResRedisUser userSetting = redisService.getUserSettings(userId);
			
			// 팀 돌면서 저장
			for (int i = 0; i < content.size(); i++) {
				channels[i] = new ArrayList<>();
				JSONObject temp = (JSONObject) content.get(i);
				Team team = new Team();
				team.setName((String)temp.get("display_name"));
				team.setToken((String)temp.get("id"));
				teams.add(team);
				
				// 새로운 사용자 설정용
				ResRedisTeam forNTeam = new ResRedisTeam(team.getToken(), team.getName(), colors[i%colors.length]);
				List<ResRedisChannel> forNChannelList = new ArrayList<>();
				
				// 현재 팀에서 참여하고 있는 채널 불러오기
				channels[i] = getChannelsFromMM(token, mattermostUrl, userId, team.getToken());
				
				// team token을 기준으로 db에서 존재하는지 여부 확인 후 존재한다면 channel token 갖고오기
				Optional<Team> isTeam = teamRepository.findByToken(team.getToken());
				if(isTeam.isPresent()) { // 존재하는 팀 => 채널 검색
					List<Channel> savedChannels = channelRepository.findAllByTeamId(isTeam.get().getId());
					for (int j = 0; j < channels[i].size(); j++) {
						Channel c1 = channels[i].get(j);
						for (int k = 0; k < savedChannels.size(); k++) {
							Channel c2 = savedChannels.get(k);
							if(c1.getToken().equals(c2.getTeam())) // DB에 이미 저장되어있는 채널
								break;
							if(k+1==savedChannels.size()) { // DB에 없는 채널
								c1.setTeam(team);
								channelRepository.save(c1);
								// redis에서 기존에 있던 팀들 중 이름이 같은 팀을 찾아서 채널을 추가해준다.
								if(userSetting != null) { 
									for (int t = 0; t < userSetting.getTeams().size(); t++) {
										ResRedisTeam rTeam = userSetting.getTeams().get(t);
										if(rTeam.getTeamId().equals(isTeam.get().getToken())) {
											rTeam.getSubscribe().add(new ResRedisChannel(c1.getToken(), c1.getName(), true));
											userSetting.getTeams().set(t, rTeam);
										}
									}
								}
							}
						} // DB에 저장되어있는 채널 for
						if(userSetting == null) { // 새로운 사용자라면..!!
							forNChannelList.add(new ResRedisChannel(c1.getToken(),c1.getName(),true));
						}
					} // mm API에서 불러온 채널 for
				}else { // 저장 안된 팀 => 채널까지 모두 저장
					teamRepository.save(team);
					ResRedisTeam rTeam = new ResRedisTeam(team.getToken(), team.getName(), colors[i%colors.length]);
					List<ResRedisChannel> channelList = new ArrayList<>();
					for (int j = 0; j < channels[i].size(); j++) {
						Channel tempChannel = channels[i].get(j);
						tempChannel.setTeam(team);
						channelRepository.save(tempChannel);
						channelList.add(new ResRedisChannel(tempChannel.getToken(), tempChannel.getName(), true));
					}
					if(userSetting != null) {
						rTeam.setSubscribe(channelList);
						List<ResRedisTeam> changeTeamList = userSetting.getTeams();
						changeTeamList.add(rTeam);
						userSetting.setTeams(changeTeamList);
					}else {
						forNTeam.setSubscribe(channelList);
						forNTeamList.add(forNTeam);
					}
				}
			}
			
			if(userSetting != null)
				redisService.setUserSettings(userId, userSetting);
			else
				redisService.setUserSettings(userId, new ResRedisUser(forNTeamList,"light"));
				
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	// redis에 저장되지 않은 새로운 유저라면 redis에 저장해준다.
	public void forNewUser(String userId) {
		
	}
	
	public List<Channel> getChannelsFromMM(String token, String mattermostUrl, String userId, String teamId) {
		String GET_CHANNEL_LIST = mattermostUrl + "/api/v4/users/"+userId+"/teams/"+teamId+"/channels";
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
				String c = (String) temp.get("type");
				if(c.equals("O") || c.equals("P")) { // 공개채널이나 비공개채널
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
