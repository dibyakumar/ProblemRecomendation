package com.problem.practice.payload;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(value = Include.NON_NULL)
public class ProblemDto {
	private Integer id;
	private String link;
	private Integer review;
	private LocalDate dueOn;
	private String status;
	private String interval;
	private LocalDate dateOfLastPractice;
}
