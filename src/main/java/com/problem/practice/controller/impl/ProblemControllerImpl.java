package com.problem.practice.controller.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.problem.practice.controller.ProblemController;
import com.problem.practice.exception.ProblemRecomendWebAppException;
import com.problem.practice.payload.ProblemDto;
import com.problem.practice.service.ProblemService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/prob")
public class ProblemControllerImpl implements ProblemController{

	@Autowired
	private ProblemService probService;
	
	@PostMapping("/add")
	@Override
	public ResponseEntity<String> addProblem(@RequestBody ProblemDto problem, HttpServletRequest request) {
		HttpSession sessionGeneration = sessionGeneration(request);
		
		return new ResponseEntity<String>(probService.saveProb(problem,sessionGeneration),HttpStatus.OK);
	}

	@GetMapping("/getProblem")
	@Override
	public ResponseEntity<ProblemDto> getRecomendation(HttpServletRequest request) {
		HttpSession sessionGeneration = sessionGeneration(request);
		
		return new ResponseEntity<ProblemDto>(probService.getRecomendation(sessionGeneration),HttpStatus.OK);
	}
	
	@PostMapping("/review")
	@Override
	public ResponseEntity<String> reviewProb(@RequestParam Integer probId,@RequestParam Integer review, HttpServletRequest request) {
		HttpSession sessionGeneration = sessionGeneration(request);
		return new ResponseEntity<String>(probService.reviewProb(probId,review,sessionGeneration),HttpStatus.OK);
	}

	@GetMapping("/getSturggleProb")
	@Override
	public ResponseEntity<List<ProblemDto>> getProbOnWhichUserStruggle(HttpServletRequest request) {
		HttpSession sessionGeneration = sessionGeneration(request);
		return new ResponseEntity<List<ProblemDto>>(probService.getStruggleProblem(sessionGeneration),HttpStatus.OK);
	}

	@GetMapping("/getEasyProb")
	@Override
	public ResponseEntity<List<ProblemDto>> getProbWhichSolvedEasily(HttpServletRequest request) {
		HttpSession sessionGeneration = sessionGeneration(request);
		return new ResponseEntity<List<ProblemDto>>(probService.getEasyProblem(sessionGeneration),HttpStatus.OK);
	}

	@GetMapping("/getAllProblem")
	@Override
	public ResponseEntity<List<ProblemDto>> getAllProblem(HttpServletRequest request) {
		HttpSession sessionGeneration = sessionGeneration(request);
		return new ResponseEntity<List<ProblemDto>>(probService.getAllProblem(sessionGeneration),HttpStatus.OK);
	}
	
	public HttpSession sessionGeneration(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if(session == null) {
			throw new ProblemRecomendWebAppException("Please LogIn First!!");
		}
		return session;
	}

	
	
}
