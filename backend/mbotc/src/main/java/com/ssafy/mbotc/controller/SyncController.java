package com.ssafy.mbotc.controller;

import java.util.HashMap;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ssafy.mbotc.entity.User;
import com.ssafy.mbotc.service.SyncService;
import com.ssafy.mbotc.service.SyncService2;
import com.ssafy.mbotc.service.UserService;

@Controller
@RequestMapping("/sync")
public class SyncController {

	@Autowired
	SyncService2 syncService;
	
	@Autowired
	UserService userService;
	
	@GetMapping
	public ResponseEntity<String> setUserSetting(@RequestHeader HashMap<String,String> header){
		String authToken = header.get("auth");
		Optional<User> target = userService.findByToken(authToken);
		if(!target.isPresent()) {
			
		}
		syncService.syncWithUser(authToken, target.get().getUrl(), target.get().getUserId());
		return ResponseEntity.status(HttpStatus.OK).body("SUCCESS");
	}
}
