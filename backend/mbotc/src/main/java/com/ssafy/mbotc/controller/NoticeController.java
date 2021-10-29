package com.ssafy.mbotc.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.ssafy.mbotc.entity.Notice;
import com.ssafy.mbotc.entity.User;
import com.ssafy.mbotc.entity.response.ResNoticeList;
import com.ssafy.mbotc.entity.response.ResRedisChannel;
import com.ssafy.mbotc.entity.response.ResRedisTeam;
import com.ssafy.mbotc.entity.response.ResRedisUser;
import com.ssafy.mbotc.service.NoticeService;
import com.ssafy.mbotc.service.RedisService;
import com.ssafy.mbotc.service.UserService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiKeyAuthDefinition;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/notification")
public class NoticeController {

	@Autowired
	private NoticeService noticeService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RedisService redisService;
	
	// 한달 전체 공지 갖고오기 ; 해당하는 연도별 + 월별 알림을 모두 가져온다.
	@GetMapping(value = "/month")
	@ApiOperation(
			value = "Get All Notices by Year and Month", 
			notes = "- http://localhost:8080/api/v1/notification?year=2021&month=10\n- header : { \"auth\" : \"user's token\" }")
	@ApiResponses({
		@ApiResponse(code = 200, message = "SUCCESS"),
		@ApiResponse(code = 404, message = "USER NOT FOUND")
	})
	public ResponseEntity<ResNoticeList> getNoticeByMonth(@RequestHeader HashMap<String,String> header, @RequestParam String year, @RequestParam String month){
		String authToken = header.get("auth");
		Optional<User> target = userService.findByToken(authToken);
		if(!target.isPresent()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "USER NOT FOUND");
		}
		String keyid = target.get().getUserId();
		
		//토큰을 기준으로 redis에 저장되어있는 구독 팀, 채널 가져옴
		ResRedisUser redisUserinfo = redisService.getUserSettings(keyid);
		
		//구독 팀 갯수
		int N = redisUserinfo.getTeams().size();
		List<ResRedisTeam> teams = redisUserinfo.getTeams();
		
		//channel id 담기
		List<String> subscribeChannelidlist = new ArrayList<>();
		
		//channel list
		List<ResRedisChannel> channelsList = new ArrayList<ResRedisChannel>();
		for(int i = 0; i< N; i++) {
			channelsList= teams.get(i).getSubscribe();
			int K = channelsList.size();
			for(int j = 0; j < K; j++) {
				if(channelsList.get(j).isShow() == true) {
					subscribeChannelidlist.add(channelsList.get(j).getChannelId());
				}
			}
		}
			
		ResNoticeList result = new ResNoticeList();
		result.setSubscribe(subscribeChannelidlist.toString().substring(1,subscribeChannelidlist.size()-1));
		List<Notice> total = new ArrayList<Notice>();
		
		//구독 채널의 한달치 공지
		for(String subscribe : subscribeChannelidlist) {
			List<Notice> temp = noticeService.getNoticeByYearAndMonth(year, month, subscribe);
			for (int i = 0; i < temp.size(); i++) {
				total.add(temp.get(i));
			}
		}
		result.setNotifications(total);

		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
	
	//일별 알람 가져오기
	@GetMapping(value = "/day")
	@ApiOperation(
			value = "Get All Notices by Year and Month And Day", 
			notes = "- http://localhost:8080/api/v1/notification?year=2021&month=10&day=8\n- header : { \"auth\" : \"user's token\" }")
	@ApiResponses({
		@ApiResponse(code = 200, message = "SUCCESS"),
		@ApiResponse(code = 404, message = "USER NOT FOUND")
	})
	public ResponseEntity<ResNoticeList> getNoticeByDay(@RequestHeader HashMap<String,String> header, @RequestParam String year, @RequestParam String month, @RequestParam String day){
		String authToken = header.get("auth");
		
		Optional<User> target = userService.findByToken(authToken);
		if(!target.isPresent()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "USER NOT FOUND");
		}
		String keyid = target.get().getUserId();
		
		//토큰을 기준으로 redis에 저장되어있는 구독 팀, 채널 가져옴
		ResRedisUser redisUserinfo = redisService.getUserSettings(keyid);
		
		//구독 팀 갯수
		int N = redisUserinfo.getTeams().size();
		List<ResRedisTeam> teams = redisUserinfo.getTeams();
		
		//channel id 담기
		List<String> subscribeChannelidlist = new ArrayList<>();
		
		//channel list
		List<ResRedisChannel> channelsList = new ArrayList<ResRedisChannel>();
		for(int i = 0; i< N; i++) {
			channelsList= teams.get(i).getSubscribe();
			int K = channelsList.size();
			for(int j = 0; j < K; j++) {
				if(channelsList.get(j).isShow() == true) {
					subscribeChannelidlist.add(channelsList.get(j).getChannelId());
				}
			}
		}
			
		ResNoticeList result = new ResNoticeList();
		result.setSubscribe(subscribeChannelidlist.toString().substring(1,subscribeChannelidlist.size()-1));
		List<Notice> total = new ArrayList<Notice>();
		
		//구독 채널의 한달치 공지
		for(String subscribe : subscribeChannelidlist) {
			List<Notice> temp = noticeService.getNoticeByYearAndMonthAndDay(year, month, day,subscribe);
			for (int i = 0; i < temp.size(); i++) {
				total.add(temp.get(i));
			}
		}
		result.setNotifications(total);

		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
	
	// 공지 세부정보 갖고오기
	@GetMapping(value = "/post/{postId}")
	@ApiOperation(
			value = "Get a Notice by notice Id", 
			notes = "- http://localhost:8080/api/v1/notification/post/p1\n- header : { \"auth\" : \"user's token\" }")
	@ApiResponses({
		@ApiResponse(code = 200, message = "SUCCESS"),
		@ApiResponse(code = 401, message = "UNAUTHRIZED")
	})
	public ResponseEntity<Notice> getNoticeByMonth1(@RequestHeader HashMap<String,String> header, @PathVariable String postId){
		String authToken = header.get("auth");
		Optional<User> target = userService.findByToken(authToken);
		if(!target.isPresent()) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "UNAUTHRIZED");
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(noticeService.findByNoticeId(postId));
	}
	
	
}
