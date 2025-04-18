package com.problem.practice.controller;

import org.springframework.http.ResponseEntity;

import com.problem.practice.payload.JwtTokenResposne;
import com.problem.practice.payload.UserDto;


public interface Authentication {
public ResponseEntity<String> signup(UserDto user);
	
	public ResponseEntity<JwtTokenResposne> signin(String Email,String password);
	
//	public ResponseEntity<UserDto> userDetails(HttpServletRequest request);
	
	public ResponseEntity<JwtTokenResposne> refreshToken(String refreshToken);

}
