package com.problem.practice.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.problem.practice.entity.Problem;
import com.problem.practice.entity.User;
import com.problem.practice.payload.UserDto;
import com.problem.practice.repository.UserRepository;
import com.problem.practice.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService{
	
	@Autowired
	private UserRepository urepo; 
	
	@Override
	public boolean checkAlreadyPresent(String emailId) {
		List<User> findAll = urepo.findAll();
		
		return findAll.stream().anyMatch(user->emailId.equalsIgnoreCase(user.getEmailId()));
	}

	@Override
	public void saveUser(UserDto user) {
		User u = new User(user.getEmailId(),user.getUserName(),user.getPassword(),new ArrayList<Problem>());
		urepo.save(u);
	}

	@Override
	public boolean checkPassword(String email, String password) {
		return  urepo.findById(email).get().getPassword().equals(password);
	}

	@Override
	public UserDto getUser(String email) {
		User user = urepo.findById(email).get();
		UserDto udto = new UserDto(user.getEmailId(),user.getUserName(),user.getPassword());
		return udto;
	}

}
