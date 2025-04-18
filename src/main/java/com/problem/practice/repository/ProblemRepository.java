package com.problem.practice.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.problem.practice.entity.Problem;

public interface ProblemRepository extends MongoRepository<Problem, String>{

	List<Problem> findByUserId(String emailId);

}
