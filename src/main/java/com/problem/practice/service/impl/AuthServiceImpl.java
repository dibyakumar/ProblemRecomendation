package com.problem.practice.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.problem.practice.entity.User;
import com.problem.practice.exception.ServiceException;
import com.problem.practice.payload.UserDto;
import com.problem.practice.repository.UserRepository;

@Service
public class AuthServiceImpl {
	
	@Autowired
	private UserRepository urepo; 
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	

	public boolean checkAlreadyPresent(String emailId) {
		List<User> findAll = urepo.findAll();
		
		return findAll.stream().anyMatch(user->emailId.equalsIgnoreCase(user.getEmailId()));
	}

	public void saveUser(UserDto user) {
		if(urepo.existsById(user.getEmailId())){
			throw new ServiceException("User With same Email Id already present !! ",400);
		}
		
		User u = new User(user.getEmailId(),user.getUserName(),passwordEncoder.encode(user.getPassword()),"USER");
		urepo.save(u);
	}

	public boolean checkPassword(String email, String password) {
		return  urepo.findById(email).get().getPassword().equals(password);
	}

	public UserDto getUser(String email) {
		User user = urepo.findById(email).get();
		UserDto udto = new UserDto(user.getEmailId(),user.getUserName(),user.getPassword());
		return udto;
	}

}
