package com.problem.practice.entity;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "Problem")
public class Problem {
	@Id
	private String problemId;
	private String problemLink;
	private Integer review;
	private Integer interval;
	private LocalDate practiceDate;
	private String userId;
	private Double easeFactor;
}
