package com.ssafy.mbotc.service;

import java.util.Optional;

import com.ssafy.mbotc.entity.User;

public interface UserService {

	Optional<User> findByUserEmailAndUrl(String userEmail, String url);
	User updateUserToken(User user, String newToken);
	
}
