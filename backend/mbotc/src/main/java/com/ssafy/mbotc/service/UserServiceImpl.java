package com.ssafy.mbotc.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.mbotc.dao.UserRepository;
import com.ssafy.mbotc.entity.User;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;
	
	@Override
	public Optional<User> findByUserEmailAndUrl(String userEmail, String url) {
		Optional<User> user = userRepository.findByUserEmailAndUrl(userEmail, url);
		return user;
	}
	
	@Override
	public User updateUserToken(User user, String newToken) {
		user.setToken(newToken);
		return userRepository.save(user);
	}

}
