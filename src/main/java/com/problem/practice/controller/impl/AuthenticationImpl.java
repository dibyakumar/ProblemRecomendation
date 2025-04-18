package com.problem.practice.controller.impl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.problem.practice.config.jwtConfig.JwtConfigService;
import com.problem.practice.controller.Authentication;
import com.problem.practice.exception.ServiceException;
import com.problem.practice.payload.JwtTokenResposne;
import com.problem.practice.payload.UserDto;
import com.problem.practice.service.impl.AuthServiceImpl;

@RestController
@RequestMapping("/auth")
public class AuthenticationImpl implements Authentication{
	
	@Autowired
	private AuthenticationManager authManager;
	@Autowired
	private JwtConfigService jwtService;

	@Autowired
	private AuthServiceImpl authService;
	
	@PostMapping("/signup")
	@Override
	public ResponseEntity<String> signup(@RequestBody UserDto user) {
			authService.saveUser(user);
		return new ResponseEntity<>("Signed up Successfully !!!",HttpStatus.OK);
	}

	@PostMapping("/signin")
	@Override
	public ResponseEntity<JwtTokenResposne> signin(@RequestParam String email,@RequestParam String password) {
		authManager.authenticate(new UsernamePasswordAuthenticationToken(email,password));
		String token = jwtService.generateToken(email);
		return ResponseEntity.ok(JwtTokenResposne.builder().token(token).refreshToken(jwtService.generateRefreshToken(email)).time(LocalDateTime.now()).build());
	}

//	@GetMapping("/getDetails")
//	@Override
//	public ResponseEntity<UserDto> userDetails(HttpServletRequest request) {
//		HttpSession session = request.getSession(false);
//		String email = null;
//		// if the user is logged in
//		if(session != null) {
//			email = session.getAttribute("user").toString();
//		}
//		else throw new NotFoundException("Please Signin !!");
//		
//		return new ResponseEntity<UserDto>(authService.getUser(email),HttpStatus.OK);
//		
//	}

	@GetMapping("/refreshToken")
	@Override
	public ResponseEntity<JwtTokenResposne> refreshToken(@RequestParam String refreshToken) {
		if(!jwtService.validateJwtToken(refreshToken)) {
			throw new ServiceException("Invalid/ExpiredToken",401);
		}
		String userName = jwtService.extractUserName(refreshToken);
		return ResponseEntity.ok(JwtTokenResposne.builder().token(jwtService.generateToken(userName)).refreshToken(jwtService.generateRefreshToken(userName)).time(LocalDateTime.now()).build());
	}
	

}
