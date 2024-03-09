package com.problem.practice.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "UniqueId")
public class UniqueId {
	@Id
	private String id;
	private Integer counterValue;
}
