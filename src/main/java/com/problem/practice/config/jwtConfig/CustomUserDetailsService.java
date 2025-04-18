package com.problem.practice.config.jwtConfig;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.problem.practice.entity.User;
import com.problem.practice.exception.NotFoundException;
import com.problem.practice.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService{
	
	@Autowired
	private UserRepository urepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user = urepo.findById(username);
		if(user.isPresent()) {
		return new CustomUserDetails(user.get()); 
		}
		throw new NotFoundException("User name not found with ID : "+username);
	}

}
