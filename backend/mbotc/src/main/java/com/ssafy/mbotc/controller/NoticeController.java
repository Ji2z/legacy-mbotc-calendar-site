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

import com.ssafy.mbotc.entity.Notice;
import com.ssafy.mbotc.entity.User;
import com.ssafy.mbotc.entity.response.ResNoticeList;
import com.ssafy.mbotc.entity.response.ResRedisChannel;
import com.ssafy.mbotc.entity.response.ResRedisTeam;
import com.ssafy.mbotc.entity.response.ResRedisUser;
import com.ssafy.mbotc.service.NoticeService;
import com.ssafy.mbotc.service.RedisService;
import com.ssafy.mbotc.service.UserService;

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
	@GetMapping
	public ResponseEntity<ResNoticeList> getNoticeByMonth(@RequestHeader HashMap<String,String> header, @RequestParam String year, @RequestParam String month){
		String authToken = header.get("auth");
		
		//토큰을 기준으로 redis에 저장되어있는 구독 팀, 채널 가져옴

		//getusersetting redisservice
		/**0. 구독 채널아이디를 저장할 빈 리스트를 만들어 둔다. => List<String> subscribeList = new ArrayList<>() // 함수에서 반환하는 ResNoticeList는 reponse라는 변수로 만든다.
				1. authtoken을 가지고 User 갖고온다.
				2. redisService.getUserSetting(User.getUser_id) => ResRedisUserSetting 
				3. List<ResRedisTeam> teams = ResRedisUserSetting.getTeams();
				4. teams.size() 반복문을 돈다.
				    4-1. List<ResRedisChannel> channels = teams.get(i).getSubscribe()
				    4-2. channels.size() 반목문을 돈다.
				        4-2-1. channels.get(i).getShow == true일 경우에만 channel_id를 갖고와서 0번 subscribeList에 add한다.
				5. 0번 subscribeList에 구독 채널 아이디를 다 저장했다면 subscribeList.size()만큼 반복문을 돈다.
				    5-1 . subscribeList.get(i)해서 얻을 수 있는 채널 아이디로 DB notice 테이블에서 설정된 기간 기준으로 List<Notice>를 갖고온다.
				    5-1-1. 얻어온 List<Notice>를 반환값인 response.notifications에 add한다.
				6. response.set함수들을 통해 구독 채널명들과, List<Notice>를 설정하고 return에 넘긴다.
				**/
		Optional<User> target = userService.findByToken(authToken);
		String keyid = target.get().getUserId();
		
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
		result.setSubscribe(subscribeChannelidlist.toString());
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
	
	// 공지 세부정보 갖고오기
	@GetMapping(value = "/post/{postId}")
	public ResponseEntity<Notice> getNoticeByMonth1(@RequestHeader HashMap<String,String> header, @PathVariable String postId){
		return ResponseEntity.status(HttpStatus.OK).body(noticeService.findByNoticeId(postId));
	}
	
	
}
