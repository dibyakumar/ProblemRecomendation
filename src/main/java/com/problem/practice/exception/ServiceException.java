package com.problem.practice.exception;

import lombok.Data;

@Data
public class ServiceException extends RuntimeException{
	
	private String message;
	private int status;
	
	public ServiceException(String message) {
		super(message);
		this.message = message;
	}
	
	public ServiceException(String message,int status) {
		super(message);
		this.message = message;
		this.status = status;
	}
}
