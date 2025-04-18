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
import com.problem.practice.payload.ProblemDto;
import com.problem.practice.service.impl.ProblemServiceImpl;

@RestController
@RequestMapping("/prob")
public class ProblemControllerImpl implements ProblemController{

	@Autowired
	private ProblemServiceImpl probService;
	
	@PostMapping("/add")
	@Override
	public ResponseEntity<String> addProblem(@RequestBody ProblemDto problem) {
		return new ResponseEntity<String>(probService.saveProb(problem),HttpStatus.OK);
	}

	@GetMapping("/getProblem")
	@Override
	public ResponseEntity<ProblemDto> getRecomendation() {
		return new ResponseEntity<ProblemDto>(probService.getRecomendation(),HttpStatus.OK);
	}
	
	@PostMapping("/review")
	@Override
	public ResponseEntity<String> reviewProb(@RequestParam Integer probId,@RequestParam Integer review) {
		return new ResponseEntity<String>(probService.reviewProb(probId,review),HttpStatus.OK);
	}

	/*
	 * TypeOfProblem :
	 * 
	 * ALL
	 * STRUGGLED
	 * EASY
	*/
	@GetMapping("/problem")
	@Override
	public ResponseEntity<List<ProblemDto>> getAllProblem(@RequestParam String type) {
		return new ResponseEntity<List<ProblemDto>>(probService.getAllProblem(type),HttpStatus.OK);
	}
	
	
	
}
