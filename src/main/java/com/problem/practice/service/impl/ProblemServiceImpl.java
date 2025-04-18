package com.problem.practice.service.impl;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.problem.practice.entity.Problem;
import com.problem.practice.exception.NotFoundException;
import com.problem.practice.logic.IntervalCalculator;
import com.problem.practice.payload.ProblemDto;
import com.problem.practice.repository.ProblemRepository;
import com.problem.practice.util.ProjectUtility;
import com.problem.practice.util.TypeOfProblem;


@Service
public class ProblemServiceImpl {
	
	@Autowired
	private ProblemRepository problemRepo;
	
	@Autowired
	private ProjectUtility projectUtil;
	
	@Autowired
	private IntervalCalculator intervalCalculator;
	
	private  Queue<Problem> scheduler = new LinkedList<>(); 
	
	private LocalDate processedDate;
	
	public String saveProb(ProblemDto problem) {
		
		String currentUserId = projectUtil.getCurrentUserId();
		
		Problem prob = new Problem();
		prob.setProblemLink(problem.getLink());
		prob.setProblemId(projectUtil.generateId()+"");
		prob.setInterval(problem.getReview());
		prob.setReview(problem.getReview());
		prob.setPracticeDate(LocalDate.now());
		prob.setUserId(currentUserId);
		prob.setEaseFactor(2.5); // ease factor
		
		
		problemRepo.save(prob);
		
		return "Problem Saved !!";
	}
	
	
	// will schedule for practice
	private void scheduleProblem(String userId) {
		List<Problem> problems = problemRepo.findByUserId(userId);
																					// it will create collection according to the passed argument
		scheduler = problems.stream().filter(prob->prob.getInterval()==0).collect(Collectors.toCollection(LinkedList::new));
	}
	
	// will update the interval according to the day it practiced 
	private void processProblem(String userId) {
		processedDate = LocalDate.now();
		List<Problem> problems = problemRepo.findByUserId(userId);
		for(Problem prob : problems) {
			if(prob.getInterval() != 0 ) {
				// this will give the no of days between practiceDate and current date
				long between = ChronoUnit.DAYS.between(prob.getPracticeDate(), processedDate);
				if(prob.getInterval() == (int)between || prob.getInterval() < between) {
					prob.setInterval(0);
				}
				
			}
		}
	}
	




	public ProblemDto getRecomendation() {
		String currentUserId = projectUtil.getCurrentUserId();
		
		//schedule and process  once in a day 
		if(processedDate==null || processedDate.isBefore(LocalDate.now())) {
			// update interval
			processProblem(currentUserId);
			//schedule problem according to the interval
			scheduleProblem(currentUserId);
		}
		
		
		if(scheduler.isEmpty())
			throw new NotFoundException("No Problems Scheduled For Today !!");
		
		Problem problem = scheduler.poll();
		List<ProblemDto> dto = processProblems(List.of(problem));
		
		//update the practice date 
		problem.setPracticeDate(processedDate);
		problemRepo.save(problem);
		return  dto.get(0);
	}


	public String reviewProb(Integer probId, Integer review) {
		Optional<Problem> problems = problemRepo.findById(probId.toString());
		if(problems.isEmpty()) {
			throw new NotFoundException("Problem Not Found with Id : "+probId);
		}
		
		
		int calculateNewInterval = intervalCalculator.calculateNewInterval(review, problems.get());
		
		problems.get().setInterval(calculateNewInterval);
		
		// dont update the review before calculating the interval 
		problems.get().setReview(review);
		
		//update prob
		problemRepo.save(problems.get());
		
		return "Review Updated !!";
	}


	public List<ProblemDto> getAllProblem(String type) {
		
		String currentUserId = projectUtil.getCurrentUserId();
		// process all problems for the user
		processProblem(currentUserId);
		
		List<Problem> probByUserId = problemRepo.findByUserId(currentUserId);
		
		
		if(TypeOfProblem.EASY.toString().equals(type.toUpperCase())) {
		
			List<Problem> allEasyProblems = probByUserId.stream().filter(prob->prob.getReview()>3).collect(Collectors.toList());
			return processProblems(allEasyProblems);
			
		}
		
		else if(TypeOfProblem.STRUGGLED.toString().equals(type.toUpperCase())){
			List<Problem> allHardProblems = probByUserId.stream().filter(prob->prob.getReview()<=3).collect(Collectors.toList());
			return processProblems(allHardProblems);
		}
		
		else {
			return processProblems(probByUserId);
		}
		
	}
	
	private List<ProblemDto> processProblems(List<Problem> problems){
		List<ProblemDto> dtos = new ArrayList<>();
		for(Problem prob : problems) {
				long dueOn = prob.getInterval() -  ChronoUnit.DAYS.between(prob.getPracticeDate(), processedDate);
				LocalDate dueDate = LocalDate.now().plusDays(dueOn);
				ProblemDto dto = ProblemDto.builder().id(Integer.parseInt(prob.getProblemId())).link(prob.getProblemLink()).review(prob.getReview())
						.dateOfLastPractice(prob.getPracticeDate()).dueOn(dueDate).build();
				dtos.add(dto);
			
		}
		return dtos;
	}


	

}
