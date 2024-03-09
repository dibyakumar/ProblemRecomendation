package com.problem.practice.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProblemDto {
	private Integer id;
	private String link;
	private Integer review;
}
