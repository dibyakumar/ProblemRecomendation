package com.problem.practice.service.impl;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.problem.practice.entity.Problem;
import com.problem.practice.entity.UniqueId;
import com.problem.practice.entity.User;
import com.problem.practice.exception.NotFoundException;
import com.problem.practice.payload.ProblemDto;
import com.problem.practice.repository.UniqueIdRepository;
import com.problem.practice.repository.UserRepository;
import com.problem.practice.service.ProblemService;

import jakarta.servlet.http.HttpSession;

@Service
public class ProblemServiceImpl implements ProblemService{

	@Autowired
	private UniqueIdRepository idrepo;
	
	@Autowired
	private UserRepository urepo;
	
	private  static Queue<Problem> scheduler = new LinkedList<>(); 
	
	private LocalDate processedDate;
	
	@Override
	public String saveProb(ProblemDto problem, HttpSession sessionGeneration) {
		
		
		User curruser =  getUser(sessionGeneration);
		
		
		Problem prob = new Problem();
		prob.setProblemLink(problem.getLink());
		prob.setProblemId(generateId()+"");
		prob.setInterval(problem.getReview());
		prob.setReview(problem.getReview());
		prob.setPracticeDate(LocalDate.now());
		
		List<Problem> problems = curruser.getProblems();
		problems.add(prob); 
		
		urepo.save(curruser);
		
		return "Problem Saved !!";
	}
	
	
	// will schedule for practice
	private void scheduleProblem(User user) {
		List<Problem> problems = user.getProblems();
																					// it will create collection according to the passed argument
		scheduler = problems.stream().filter(prob->prob.getInterval()==0).collect(Collectors.toCollection(LinkedList::new));
	}
	
	// will update the interval according to the day it practiced 
	private void processProblem(User user) {
		processedDate = LocalDate.now();
		List<Problem> problems = user.getProblems();
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
	
	public User getUser(HttpSession session) {
		return urepo.findById(session.getAttribute("user").toString()).get(); 
	}
	
	private Integer generateId() {
		Optional<UniqueId> uniqueId = idrepo.findById("123");
		if(uniqueId.isEmpty()) {
			UniqueId id = new UniqueId();
			id.setId("123");
			id.setCounterValue(1);
			idrepo.save(id);
			return 1;
		}
		
		Integer id = uniqueId.get().getCounterValue()+1;
		
		uniqueId.get().setCounterValue(id);
		idrepo.save(uniqueId.get());
		
		return id;
		
	}


	@Override
	public ProblemDto getRecomendation(HttpSession sessionGeneration) {
		User curruser =  getUser(sessionGeneration);
		
		//schedule and process  once in a day 
		if(processedDate==null || processedDate.isBefore(LocalDate.now())) {
			// update interval
			processProblem(curruser);
			//schedule problem according to the interval
			scheduleProblem(curruser);
		}
		
		
		if(scheduler.isEmpty())
			throw new NotFoundException("No Problems Scheduled For Today !!");
		
		Problem problem = scheduler.poll();
		ProblemDto dto = new ProblemDto(Integer.parseInt(problem.getProblemId()),problem.getProblemLink(),problem.getReview());
		
		//update the practice date 
		List<Problem> problems = curruser.getProblems();
		for(Problem prob : problems) {
			if(prob.getProblemId() == problem.getProblemId()) {
				prob.setPracticeDate(processedDate);
				break;
			}
		}
		urepo.save(curruser);
		return dto;
	}


	@Override
	public String reviewProb(Integer probId, Integer review, HttpSession sessionGeneration) {
		User currUser =  getUser(sessionGeneration);
		
		List<Problem> problems = currUser.getProblems();
		Optional<Problem> findAny = problems.stream().filter(prob->Integer.parseInt(prob.getProblemId())==probId).findAny();
		if(findAny.isEmpty()) {
			throw new NotFoundException("Problem Not Found with Id : "+probId);
		}
		
		findAny.get().setReview(review);
		if(review>3)
		findAny.get().setInterval(review+findAny.get().getInterval());
		else
			findAny.get().setInterval(review);
		//update user
		urepo.save(currUser);
		
		return "Review Updated !!";
	}


	@Override
	public List<ProblemDto> getStruggleProblem(HttpSession sessionGeneration) {
		User curruser =  getUser(sessionGeneration);
		List<Problem> problems = curruser.getProblems();
		List<ProblemDto> dtos = new ArrayList<>();
		for(Problem prob : problems) {
			if(prob.getReview()<=3) {
				ProblemDto dto = new ProblemDto(Integer.parseInt(prob.getProblemId()), prob.getProblemLink(), prob.getReview());
				dtos.add(dto);
			}
		}
		
		return dtos;
	}


	@Override
	public List<ProblemDto> getEasyProblem(HttpSession sessionGeneration) {
		User curruser =  getUser(sessionGeneration);
		List<Problem> problems = curruser.getProblems();
		List<ProblemDto> dtos = new ArrayList<>();
		for(Problem prob : problems) {
			if(prob.getReview()>3) {
				ProblemDto dto = new ProblemDto(Integer.parseInt(prob.getProblemId()), prob.getProblemLink(), prob.getReview());
				dtos.add(dto);
			}
		}
		
		return dtos;
	}


	@Override
	public List<ProblemDto> getAllProblem(HttpSession sessionGeneration) {
		User curruser =  getUser(sessionGeneration);
		List<Problem> problems = curruser.getProblems();
		List<ProblemDto> dtos = new ArrayList<>();
		for(Problem prob : problems) {
			
				ProblemDto dto = new ProblemDto(Integer.parseInt(prob.getProblemId()), prob.getProblemLink(), prob.getReview());
				dtos.add(dto);
			
		}
		
		return dtos;
	}


	

}
