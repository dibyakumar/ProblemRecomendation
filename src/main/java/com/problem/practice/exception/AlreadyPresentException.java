package com.problem.practice.exception;

public class AlreadyPresentException extends RuntimeException{
	
	public AlreadyPresentException(String message) {
		super(message);
	}

}
