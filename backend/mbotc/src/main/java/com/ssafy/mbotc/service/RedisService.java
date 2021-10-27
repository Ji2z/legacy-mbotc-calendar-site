package com.ssafy.mbotc.service;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import com.ssafy.mbotc.entity.response.ResRedisChannel;
import com.ssafy.mbotc.entity.response.ResRedisTeam;
import com.ssafy.mbotc.entity.response.ResRedisUser;

@Service
public class RedisService {

	@Autowired
	private StringRedisTemplate redisTemplate;
	
	JSONParser parser = new JSONParser();
	
	public ResRedisUser getUserSettings(String token) {
		ValueOperations<String, String> value = redisTemplate.opsForValue();
		ResRedisUser userSetting = new ResRedisUser();
		try {
			JSONObject content = (JSONObject) parser.parse(value.get(token));
			System.out.println(content.toString());
			userSetting.setTheme((String) content.get("theme"));
			JSONArray teams = (JSONArray) content.get("teams");
			List<ResRedisTeam> teamList = new ArrayList<>();
			for (int i = 0; i < teams.size(); i++) {
				ResRedisTeam team = new ResRedisTeam();
				JSONObject teamJ = (JSONObject) teams.get(i);
				team.setTeamId((String) teamJ.get("teamId"));
				team.setTeamName((String) teamJ.get("teamName"));
				team.setColor((String) teamJ.get("color"));
				
				JSONArray channels = (JSONArray) teamJ.get("subscribe");
				List<ResRedisChannel> channelList = new ArrayList<>();
				for (int j = 0; j < channels.size(); j++) {
					ResRedisChannel channel = new ResRedisChannel();
					JSONObject channelJ = (JSONObject) channels.get(i);
					channel.setChannelId((String) channelJ.get("channelId"));
					channel.setChannelName((String) channelJ.get("channelName"));
					channel.setShow(((String) channelJ.get("show")).equals("true")?true:false);
					channelList.add(channel);
				}
				team.setSubscribe(channelList);
				teamList.add(team);
			}
			userSetting.setTeams(teamList);
		}catch(Exception e) {
			System.out.println("에러!!");
			e.printStackTrace();
			return null;
		}
		return userSetting;
	}
	
	public void setUserSettings(String userToken, ResRedisUser userSetting) {
		ValueOperations<String, String> value = redisTemplate.opsForValue();
		System.out.println(userSetting.toString());
		value.set(userToken, userSetting.toString());
	}
}
