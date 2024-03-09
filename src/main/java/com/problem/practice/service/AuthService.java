package com.problem.practice.service;

import com.problem.practice.payload.UserDto;

public interface AuthService {

	boolean checkAlreadyPresent(String emailId);

	void saveUser(UserDto user);

	boolean checkPassword(String email, String password);

	UserDto getUser(String email);

}
