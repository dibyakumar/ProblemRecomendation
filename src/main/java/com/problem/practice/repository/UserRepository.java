package com.problem.practice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.problem.practice.entity.User;

public interface UserRepository extends MongoRepository<User, String>{

}
