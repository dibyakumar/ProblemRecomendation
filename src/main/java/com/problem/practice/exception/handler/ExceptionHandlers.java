package com.problem.practice.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.problem.practice.exception.AlreadyPresentException;
import com.problem.practice.exception.NotFoundException;
import com.problem.practice.exception.ProblemRecomendWebAppException;
import com.problem.practice.payload.ErrorResponse;

@ControllerAdvice
public class ExceptionHandlers {
	
	@ExceptionHandler
	public ResponseEntity<ErrorResponse> valueAlreadyPresnt(AlreadyPresentException ex){
		ErrorResponse response = new ErrorResponse(ex.getMessage(),System.currentTimeMillis()+"",400,"Bad Request");
		return new ResponseEntity<ErrorResponse>(response,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler
	public ResponseEntity<ErrorResponse> notFound(NotFoundException ex){
		ErrorResponse response = new ErrorResponse(ex.getMessage(),System.currentTimeMillis()+"",400,"Bad Request");;
		return new ResponseEntity<ErrorResponse>(response,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler
	public ResponseEntity<ErrorResponse> problemRecommendException(ProblemRecomendWebAppException ex){
		ErrorResponse response = new ErrorResponse(ex.getMessage(),System.currentTimeMillis()+"",400,"Bad Request");;
		return new ResponseEntity<ErrorResponse>(response,HttpStatus.BAD_REQUEST);
	}
}
