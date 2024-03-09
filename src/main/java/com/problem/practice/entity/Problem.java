package com.problem.practice.entity;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Problem {
	private String problemId;
	private String problemLink;
	private Integer review;
	private Integer interval;
	private LocalDate practiceDate;
}
