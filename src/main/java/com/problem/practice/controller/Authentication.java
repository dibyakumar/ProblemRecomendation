package com.problem.practice.controller;

import org.springframework.http.ResponseEntity;

import com.problem.practice.payload.UserDto;

import jakarta.servlet.http.HttpServletRequest;

public interface Authentication {
public ResponseEntity<String> signup(UserDto user,HttpServletRequest request);
	
	public ResponseEntity<String> signin(String Email,String password,HttpServletRequest request);
	
	public ResponseEntity<UserDto> userDetails(HttpServletRequest request);
	
	public ResponseEntity<String> logOUT(HttpServletRequest request);

}
