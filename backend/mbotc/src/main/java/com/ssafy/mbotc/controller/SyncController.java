package com.ssafy.mbotc.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ssafy.mbotc.entity.response.ResRedisUser;
import com.ssafy.mbotc.service.SyncService;

@Controller
@RequestMapping("/sync")
public class SyncController {

	@Autowired
	SyncService syncService;
	
	@GetMapping
	public ResponseEntity<String> setUserSetting(@RequestHeader HashMap<String,String> header){
		String authToken = header.get("auth");
		
		
		return ResponseEntity.status(HttpStatus.OK).body("SUCCESS");
	}
}
