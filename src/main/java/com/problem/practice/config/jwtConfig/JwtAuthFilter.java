package com.problem.practice.config.jwtConfig;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter{
	@Autowired
	private JwtConfigService jwtService;
	@Autowired
	private CustomUserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String header = request.getHeader("Authorization");
		
		if(null == header || !header.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}
		
		String token = header.substring(7);
		String username;
		try{
		 username = jwtService.extractUserName(token);
		}
		catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"Invalid or expired token\"}"); 
            return;
		}
		UserDetails userFromDb = userDetailsService.loadUserByUsername(username);
		
		if(null != username && (username.equals(userFromDb.getUsername())) && null == SecurityContextHolder.getContext().getAuthentication()) {
			try {
			if(jwtService.validateJwtToken(token)) {
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userFromDb,null, userFromDb.getAuthorities());
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
			}catch(JwtException | IllegalArgumentException e) {
				 response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		            response.setContentType("application/json");
		            response.getWriter().write("{\"error\": \"Invalid or expired token\"}");
		            return;
			}
			
		}
		filterChain.doFilter(request, response);
	}

}
