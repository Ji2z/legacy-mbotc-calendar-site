package com.ssafy.mbotc.service;

import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import com.ssafy.mbotc.entity.User;
import com.ssafy.mbotc.entity.response.ResRedisChannel;
import com.ssafy.mbotc.entity.response.ResRedisCheckList;
import com.ssafy.mbotc.entity.response.ResRedisCheckList.Post;
import com.ssafy.mbotc.entity.response.ResRedisTeam;
import com.ssafy.mbotc.entity.response.ResRedisUser;

@Service
public class RedisService {

	@Autowired
	private StringRedisTemplate redisTemplate;
	
	JSONParser parser = new JSONParser();
	
	public ResRedisCheckList getUserChecklist(String keyid) {
		ValueOperations<String, String> value = redisTemplate.opsForValue();
		ResRedisCheckList userChecklist = new ResRedisCheckList();
		
		try {
			JSONObject content = (JSONObject) parser.parse(value.get(keyid));
			System.out.println(content.toString());
			JSONArray posts = (JSONArray) content.get("posts");
			List<Post> postList = new ArrayList<>();
			for(int i = 0; i< posts.size(); i++) {
				ResRedisCheckList postobj = new ResRedisCheckList();
				Post post = postobj.new Post();
				JSONObject postP = (JSONObject) posts.get(i);
				post.setPostId((String) postP.get("postId"));
				post.setDone((Boolean) postP.get("isDone"));
				postList.add(post);
			}
			userChecklist.setPosts(postList);
			
		}catch(Exception e) {
			System.out.println("Getting post error");
			e.printStackTrace();
			return null;
		}
		return userChecklist;
	}
	
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
	
	//user post checklist
	public void setUserChecklists(String keyid, ResRedisCheckList userCheckList) {
		String formatDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
		String userkey = keyid.concat("_").concat(formatDate);		
		System.out.println(userkey);
		ValueOperations<String, String> value = redisTemplate.opsForValue();
		value.set(userkey, userCheckList.toString());

	}
}
