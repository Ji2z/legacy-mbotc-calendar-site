package com.ssafy.mbotc.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

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
	
	
}
