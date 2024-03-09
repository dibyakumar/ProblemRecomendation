package com.problem.practice.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.problem.practice.controller.Authentication;
import com.problem.practice.exception.AlreadyPresentException;
import com.problem.practice.exception.NotFoundException;
import com.problem.practice.exception.ProblemRecomendWebAppException;
import com.problem.practice.payload.UserDto;
import com.problem.practice.service.AuthService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/auth")
public class AuthenticationImpl implements Authentication{

	@Autowired
	private AuthService authService;
	
	@PostMapping("/signup")
	@Override
	public ResponseEntity<String> signup(@RequestBody UserDto user, HttpServletRequest request) {
		if(authService.checkAlreadyPresent(user.getEmailId())) {
			throw new AlreadyPresentException("User Already Present with the Email ID : "+user.getEmailId());
		}
		else {
			HttpSession session = request.getSession(false);
			if(null != session &&  session.getAttribute("user")!=null) 
				throw new ProblemRecomendWebAppException("Please LogOut First !!");
			
			authService.saveUser(user);
		}
		return new ResponseEntity<>("Signed up Successfully !!!",HttpStatus.OK);
	}

	@PostMapping("/signin")
	@Override
	public ResponseEntity<String> signin(@RequestParam String email,@RequestParam String password, HttpServletRequest request) {
		if(!authService.checkAlreadyPresent(email)) {
			throw new NotFoundException("Please Sign up !!");
		}
		if(!authService.checkPassword(email,password)) {
			return new ResponseEntity<String>("Password In Correct!!",HttpStatus.UNAUTHORIZED);
		}
		HttpSession session  = request.getSession();
		if(session.getAttribute("user") != null) return new ResponseEntity<String>("Please LogOut First !! ",HttpStatus.BAD_REQUEST); 
		session.setAttribute("user", email);
		return new ResponseEntity<String>("Welcome",HttpStatus.OK);
	}

	@GetMapping("/getDetails")
	@Override
	public ResponseEntity<UserDto> userDetails(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		String email = null;
		// if the user is logged in
		if(session != null) {
			email = session.getAttribute("user").toString();
		}
		else throw new NotFoundException("Please Signin !!");
		
		return new ResponseEntity<UserDto>(authService.getUser(email),HttpStatus.OK);
		
	}

	@GetMapping("/logout")
	@Override
	public ResponseEntity<String> logOUT(HttpServletRequest request) {
		request.getSession().invalidate();
		return new ResponseEntity<String>("LoggedOut SuccessFully !! ",HttpStatus.OK);
	}
	

}
