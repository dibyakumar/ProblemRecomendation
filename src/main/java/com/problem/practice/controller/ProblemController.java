package com.problem.practice.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.problem.practice.payload.ProblemDto;

public interface ProblemController {
	public ResponseEntity<String> addProblem(ProblemDto problem);
	
	public ResponseEntity<ProblemDto> getRecomendation();
	
	public ResponseEntity<String> reviewProb(Integer probId,Integer review);
	
	public ResponseEntity<List<ProblemDto>> getAllProblem(String type);
}
