package com.ssafy.mbotc.controller;

import java.util.Optional;

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

import com.ssafy.mbotc.entity.User;
import com.ssafy.mbotc.entity.response.ResRedisCheckList;
import com.ssafy.mbotc.entity.response.ResRedisUser;
import com.ssafy.mbotc.service.RedisService;
import com.ssafy.mbotc.service.UserService;

@Controller
@RequestMapping("/redis")
public class RedisController {
	
	@Autowired
	RedisService redisService;
	
	@Autowired
	UserService userService;
	
	@PostMapping(value = "/user")
    public ResponseEntity<String> setUserSetting(@RequestParam String userToken, @RequestBody ResRedisUser body){
        Optional<User> target = userService.findByToken(userToken);
        if(!target.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "USER NOT FOUND");
        }
        String keyid = target.get().getUserId();
        redisService.setUserSettings(keyid, body);
        return ResponseEntity.status(HttpStatus.OK).body("SUCCESS");
    }

    @GetMapping(value = "/user")
    public ResponseEntity<ResRedisUser> getUserSetting(@RequestParam String userToken){
        Optional<User> target = userService.findByToken(userToken);
        if(!target.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "USER NOT FOUND");
        }
        String keyid = target.get().getUserId();
        ResRedisUser response = redisService.getUserSettings(keyid);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
	
	@PostMapping(value = "/checklist")
	public ResponseEntity<String> setUserChecklist(@RequestParam String userToken, @RequestBody ResRedisCheckList body){
		Optional<User> target = userService.findByToken(userToken);
		String keyid = target.get().getUserId();
		redisService.setUserChecklists(keyid, body);
		return ResponseEntity.status(HttpStatus.OK).body("SUCCESS");
		
	}
	
	@GetMapping(value = "/checklist")
	public ResponseEntity<ResRedisCheckList> getUserChecklist(@RequestParam String usertoken){
		Optional<User> target = userService.findByToken(usertoken);
		String keyid = target.get().getUserId();
		ResRedisCheckList response = redisService.getUserChecklist(keyid);
		if(response == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "POSTS NOT FOUND");
		}
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
}
