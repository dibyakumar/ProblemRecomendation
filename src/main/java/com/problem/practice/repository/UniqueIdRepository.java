package com.problem.practice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.problem.practice.entity.UniqueId;

public interface UniqueIdRepository extends MongoRepository<UniqueId, String>{

}
