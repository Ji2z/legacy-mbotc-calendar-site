package com.ssafy.mbotc.controller;

import java.util.HashMap;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.ssafy.mbotc.dao.UserRepository;
import com.ssafy.mbotc.entity.User;
import com.ssafy.mbotc.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	// user token update
	@PatchMapping
	public ResponseEntity<User> updateUserToken(@RequestBody User user) {
		Optional<User> target = userService.findByUserEmailAndUrl(user.getUserEmail(), user.getUrl());
		if(!target.isPresent()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "USER NOT FOUND");
		}
		return ResponseEntity.status(HttpStatus.OK).body(userService.updateUserToken(target.get(), user.getToken()));
	}
	
	//user init
	@PostMapping
	public ResponseEntity<User> saveUserInfo(@RequestBody User user) {
		Optional<User> userInfo = userService.findByUserEmailAndUrl(user.getUserEmail(), user.getUrl());
		if(userInfo.isPresent()) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "USER ALREADY EXIST");
		}
		return ResponseEntity.status(HttpStatus.OK).body(userService.save(user));
	}
	
	//user delete
	@DeleteMapping
	public ResponseEntity<User> deleteUserInfo(@RequestHeader HashMap<String,String> header, @RequestBody User user){
		String authToken = header.get("auth");
		
		Optional<User> target = userService.findByToken(authToken);
		User userinfo = target.get();
		
		if(!target.isPresent()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "USER IS NOT FOUND");
		}
		
		if(!target.get().getUserEmail().equals(user.getUserEmail())) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "USER INFO IS NOT CORRECT");
		}
		userService.delete(userinfo);
		return ResponseEntity.status(HttpStatus.OK).body(userinfo);
	}
	
	
}
