package com.problem.practice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.problem.practice.config.jwtConfig.JwtAuthFilter;

@Configuration
@EnableWebSecurity
public class SecurityFilterChainConfig {

	@Autowired
	private JwtAuthFilter jwtFilter;
	@Autowired
	private AuthenticationProvider authProvider;
	
	@Bean
	 DefaultSecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		
		http.csrf(csrf->csrf.disable())
		.authorizeHttpRequests(request->{
				try {
					request.requestMatchers("/auth/**").permitAll()
					.anyRequest().authenticated()
					.and()
					.sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
					.authenticationProvider(authProvider)
					.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
				}catch(Exception e) {
					
				}
		}
				);
		return http.build();
	}
}
