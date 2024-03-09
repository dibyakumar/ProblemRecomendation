package com.problem.practice.service;

import java.util.List;

import com.problem.practice.payload.ProblemDto;

import jakarta.servlet.http.HttpSession;

public interface ProblemService {

	String saveProb(ProblemDto problem, HttpSession sessionGeneration);

	ProblemDto getRecomendation(HttpSession sessionGeneration);

	String reviewProb(Integer probId, Integer review, HttpSession sessionGeneration);

	List<ProblemDto> getStruggleProblem(HttpSession sessionGeneration);

	List<ProblemDto> getEasyProblem(HttpSession sessionGeneration);

	List<ProblemDto> getAllProblem(HttpSession sessionGeneration);

	

}
