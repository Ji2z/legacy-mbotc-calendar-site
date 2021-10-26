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
import com.ssafy.mbotc.entity.ResNoticeList;
import com.ssafy.mbotc.service.NoticeService;

@RestController
@RequestMapping("/notification")
public class NoticeController {

	@Autowired
	private NoticeService noticeService;
	
	// 한달 전체 공지 갖고오기 ; 해당하는 연도별 + 월별 알림을 모두 가져온다.
	@GetMapping
	public ResponseEntity<ResNoticeList> getNoticeByMonth(@RequestHeader HashMap<String,String> header, @RequestParam String year, @RequestParam String month){
		String authToken = header.get("auth");
		
		//토큰을 기준으로 redis에 저장되어있는 구독 팀, 채널 가져옴
		
		ResNoticeList result = new ResNoticeList();
		result.setSubscribe("구독하고있는 채널목록");
		result.setNotifications(new ArrayList<>());
		String[] subscribeList = result.getSubscribe().split(",");
		for(String subscribe : subscribeList) {
			List<Notice> temp = noticeService.findAllByYearAndMonth(year, month, subscribe);
			for (int i = 0; i < temp.size(); i++) {
				result.getNotifications().add(temp.get(i));
			}	
		}
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
	
	// 공지 세부정보 갖고오기
	@GetMapping(value = "/post/")
	public ResponseEntity<Notice> getNoticeByMonth1(@RequestHeader HashMap<String,String> header, @PathVariable String postId){
		return ResponseEntity.status(HttpStatus.OK).body(noticeService.findByNoticeId(postId));
	}
	
	// user token update
//	@PatchMapping
//	public ResponseEntity<User> updateUserToken(@RequestBody User user) {
//		Optional<User> target = userService.findByUserEmailAndUrl(user.getUserEmail(), user.getUrl());
//		if(!target.isPresent()) {
//			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "USER NOT FOUND");
//		}
//		return ResponseEntity.status(HttpStatus.OK).body(userService.updateUserToken(target.get(), user.getToken()));
//	}
	
	
}
