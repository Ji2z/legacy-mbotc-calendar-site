package com.ssafy.mbotc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import com.ssafy.mbotc.entity.response.ResRedisUser;
import com.ssafy.mbotc.service.RedisService;

@Controller
@RequestMapping("/redis")
public class RedisController {
	
	@Autowired
	RedisService redisService;
	
	@PostMapping(value = "/user")
	public ResponseEntity<String> setUserSetting(@RequestParam String userToken, @RequestBody ResRedisUser body){
		redisService.setUserSettings(userToken, body);
		return ResponseEntity.status(HttpStatus.OK).body("SUCCESS");
	}

	@GetMapping(value = "/user")
	public ResponseEntity<ResRedisUser> getUserSetting(@RequestParam String userToken){
		ResRedisUser response = redisService.getUserSettings(userToken);
		if(response == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "USER NOT FOUND");
		}
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
}
