package com.problem.practice.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.problem.practice.payload.ProblemDto;

import jakarta.servlet.http.HttpServletRequest;

public interface ProblemController {
	public ResponseEntity<String> addProblem(ProblemDto problem,HttpServletRequest request);
	
	public ResponseEntity<ProblemDto> getRecomendation(HttpServletRequest request);
	
	public ResponseEntity<String> reviewProb(Integer probId,Integer review,HttpServletRequest request);
	
	public ResponseEntity<List<ProblemDto>> getProbOnWhichUserStruggle(HttpServletRequest request);
	
	public ResponseEntity<List<ProblemDto>> getProbWhichSolvedEasily(HttpServletRequest request);	
	
	public ResponseEntity<List<ProblemDto>> getAllProblem(HttpServletRequest request);
}
