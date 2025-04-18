package com.problem.practice.exception;

import org.springframework.http.HttpStatusCode;

import lombok.Data;

@Data
public class NotFoundException extends RuntimeException{
	
	private String message;
	private HttpStatusCode status;
	
	public NotFoundException(String message) {
		super(message);
		this.message = message;
	}
	
	public NotFoundException(String message,int status) {
		super(message);
		this.message = message;
		this.status = HttpStatusCode.valueOf(status);
	}

}
