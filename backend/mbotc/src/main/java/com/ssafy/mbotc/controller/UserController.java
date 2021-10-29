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

import com.ssafy.mbotc.entity.User;
import com.ssafy.mbotc.service.SyncService;
import com.ssafy.mbotc.service.UserService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private SyncService syncservice;
	
	// after login, user token update
	@PatchMapping
	@ApiOperation(
			value = "Update userToken from DB", 
			notes = "- http://localhost:8080/api/v1/user\n - no header")
	@ApiResponses({
		@ApiResponse(code = 200, message = "SUCCESS"),
		@ApiResponse(code = 404, message = "USER NOT FOUND")
	})
	public ResponseEntity<User> updateUserToken(@RequestBody User user) {
		Optional<User> target = userService.findByUserEmailAndUrl(user.getUserEmail(), user.getUrl());
		if(!target.isPresent()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "USER NOT FOUND");
		}
		User userResult = userService.updateUserToken(target.get(), user.getToken());
		syncservice.syncWithUser(userResult.getToken(), userResult.getUrl(), userResult.getUserId());
		return ResponseEntity.status(HttpStatus.OK).body(userResult);
	}
	
	// for new user (register)
	@PostMapping
	@ApiOperation(
			value = "Insert new User to DB (register user)", 
			notes = "- http://localhost:8080/api/v1/user\n - no header")
	@ApiResponses({
		@ApiResponse(code = 200, message = "SUCCESS"),
		@ApiResponse(code = 409, message = "USER ALREADY EXIST")
	})
	public ResponseEntity<User> saveUserInfo(@RequestBody User user) {
		Optional<User> userInfo = userService.findByUserEmailAndUrl(user.getUserEmail(), user.getUrl());
		if(userInfo.isPresent()) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "USER ALREADY EXIST");
		}
		User userResult = userService.save(user);		
		syncservice.syncWithUser(userResult.getToken(), userResult.getUrl(), userResult.getUserId());
		return ResponseEntity.status(HttpStatus.OK).body(userResult);
	}
	
	// user delete
	@DeleteMapping
	@ApiOperation(
			value = "Delete User from DB", 
			notes = "- http://localhost:8080/api/v1/user\n - header : {\"auth\" : \"user's token\" }")
	@ApiResponses({
		@ApiResponse(code = 200, message = "SUCCESS"),
		@ApiResponse(code = 401, message = "UNAUTHORIZED"),
		@ApiResponse(code = 404, message = "USER NOT FOUND")
	})
	public ResponseEntity<String> deleteUserInfo(@RequestHeader HashMap<String,String> header, @RequestBody User user){
		String authToken = header.get("auth");
		Optional<User> target = userService.findByToken(authToken);
		
		if(!target.isPresent()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "USER IS NOT FOUND");
		}
		
		if(!target.get().getUserEmail().equals(user.getUserEmail())) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "UNAUTHORIZED");
		}
		userService.delete(target.get());
		return ResponseEntity.status(HttpStatus.OK).body("SUCCESS");
	}
	
	
}
