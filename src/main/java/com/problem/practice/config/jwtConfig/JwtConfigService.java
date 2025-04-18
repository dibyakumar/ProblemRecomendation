package com.problem.practice.config.jwtConfig;

import java.security.Key;
import java.util.Date;

import org.springframework.http.HttpStatusCode;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.problem.practice.entity.User;
import com.problem.practice.exception.NotFoundException;
import com.problem.practice.exception.ServiceException;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtConfigService {
	private static final String SECRETE = "Ti8rDVaXBmB8hUOFU3OVAklmA8HG5lMKPv1q5Jy1R7WPsD38m8XAv9QZWLgGS7/j"; // base64
	private static final long JWT_EXPIRATION = 900000L;
	private static final long REFRESH_TOKEN_EXPIRATION_TIME = 900000l;
	
	private boolean isTokenValid(String token,UserDetails userdetails) {
		final String userName = extractUserName(token);
		return validateJwtToken(token) && userName.equals(userdetails.getUsername());
	}
	
	public String generateToken(String userName) {
		Date now = new Date();
		Date expiryDate = new Date(now.getTime()+JWT_EXPIRATION);
		
		Key key = Keys.hmacShaKeyFor(SECRETE.getBytes());
		
		return Jwts.builder()
		.setSubject(userName)
		.setIssuedAt(now)
		.setExpiration(expiryDate)
		.signWith(key,SignatureAlgorithm.HS256).compact();
	}
	
	public boolean validateJwtToken(String token) {
		Key key = Keys.hmacShaKeyFor(SECRETE.getBytes());
		try {
			Jwts.parser().setSigningKey(key).build().parseClaimsJws(token);
			return true;
		}catch(JwtException | IllegalArgumentException e) {
			throw new ServiceException("Invalid/Expire Token", 401);
		}
	}


	public String extractUserName(String token) {
		Key key = Keys.hmacShaKeyFor(SECRETE.getBytes());
		return Jwts.parser().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
	}
	
	public String generateRefreshToken(String userId) {
		Key key = Keys.hmacShaKeyFor(SECRETE.getBytes());
		
	    return Jwts.builder()
	        .setSubject(userId)
	        .setIssuedAt(new Date())
	        .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION_TIME)) // e.g., 7 days
	        .signWith(key,SignatureAlgorithm.HS256)
	        .compact();
	}

}
