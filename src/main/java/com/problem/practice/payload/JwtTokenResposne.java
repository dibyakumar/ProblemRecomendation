package com.problem.practice.payload;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JwtTokenResposne {
	
	private String token;
	private String refreshToken;
	private LocalDateTime time;

}
