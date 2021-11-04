package com.ssafy.mbotc.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.ssafy.mbotc.entity.Channel;
import com.ssafy.mbotc.entity.Notice;
import com.ssafy.mbotc.entity.User;
import com.ssafy.mbotc.entity.request.ReqNoticePost;
import com.ssafy.mbotc.entity.response.ResNoticeList;
import com.ssafy.mbotc.entity.response.ResRedisChannel;
import com.ssafy.mbotc.entity.response.ResRedisTeam;
import com.ssafy.mbotc.entity.response.ResRedisUser;
import com.ssafy.mbotc.service.ChannelService;
import com.ssafy.mbotc.service.NoticeService;
import com.ssafy.mbotc.service.RedisService;
import com.ssafy.mbotc.service.UserService;

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
	private ChannelService channelService;
	
	@Autowired
	private RedisService redisService;
	
	@Autowired
	private SimpMessagingTemplate template;
	
	// 사이트에서 공지 등록하기 -> 플러그인에 전송
	@PostMapping
	public ResponseEntity<String> postFromSite(@RequestHeader HashMap<String,String> header, @RequestBody Notice notice, @RequestParam String channelId){
		String authToken = header.get("auth");
		Optional<User> target = userService.findByToken(authToken);
		if(!target.isPresent()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "USER NOT FOUND");
		}
		
		Optional<Channel> channel = channelService.findByToken(channelId);
		if(!channel.isPresent()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "CHANNEL NOT FOUND");
		}
		notice.setChannel(channel.get());
		notice.setUser(target.get());
		if(notice.getEndTime() == null) {
			notice.setEndTime(notice.getStartTime());
		}
		
		// 저장된 결과
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm");
		
		Notice result = noticeService.save(notice);
		try {
			template.convertAndSend("/sub/notification/" + authToken, result);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("소켓 통신 에러!!");
		}
		
		// mm plugin에 보내는 request
//		ReqNoticePost response = new ReqNoticePost();
//		response.setChannel_id(result.getChannel().getToken());
//		response.setUser_name(target.get().getUserName());
//		response.setMessage(result.getContent());
//		response.setTime(df.format(result.getTime()));
//		response.setStart_time(df.format(result.getStartTime()));
//		response.setEnd_time(df.format(result.getEndTime()));
//		if(result.getFiles()!= null)
//			response.setFile_ids(result.getFiles().split(","));
		//botService.postNoticeToMattermost(response, target.get().getUrl());
		
		return ResponseEntity.status(HttpStatus.OK).body("SUCCESS");
	}
	
	// 한달 전체 공지 갖고오기 ; 해당하는 연도별 + 월별 알림을 모두 가져온다.
	@GetMapping(value = "/month")
	@ApiOperation(
			value = "Get All Notices by Year and Month", 
			notes = "- http://localhost:8080/api/v1/notification/month?year=2021&month=05\n- header : { \"auth\" : \"user's token\" }")
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
			notes = "- http://localhost:8080/api/v1/notification/day?year=2021&month=10&day=08\n- header : { \"auth\" : \"user's token\" }")
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
	
	//일별 알람 가져오기
	@GetMapping(value = "/today")
	@ApiOperation(
			value = "Get All today's Notices for plugin", 
			notes = "- http://localhost:8080/api/v1/notification/today\n- header : { \"auth\" : \"user's token\" }")
	@ApiResponses({
		@ApiResponse(code = 200, message = "SUCCESS"),
		@ApiResponse(code = 404, message = "USER NOT FOUND")
	})
	public ResponseEntity<List<ReqNoticePost>> getNoticeByDay(@RequestHeader HashMap<String,String> header){
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
			
		List<ReqNoticePost> result = new ArrayList<>();
			
		//구독 채널의 오늘 공지
		for(String subscribe : subscribeChannelidlist) {
			List<ReqNoticePost> temp = noticeService.getTodayNoticeList(subscribe);
			for (int i = 0; i < temp.size(); i++) {
				result.add(temp.get(i));
			}
		}
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
