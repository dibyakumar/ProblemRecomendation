package com.problem.practice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.problem.practice.config.jwtConfig.CustomUserDetailsService;

@Configuration
public class ApplicationConfig {
	@Autowired
	private CustomUserDetailsService userDetailsService;
	
	@Bean
	 AuthenticationProvider getAuthenticationprovider() {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(userDetailsService);
		daoAuthenticationProvider.setPasswordEncoder(getPasswordEncoder());
		return daoAuthenticationProvider;
	}
	
	@Bean
	AuthenticationManager getAuthenticationManager(AuthenticationConfiguration conf) throws Exception{
		return conf.getAuthenticationManager();
	}
	
	@Bean
	PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
