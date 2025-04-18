package com.problem.practice.util;

public enum TypeOfProblem {
	 	ALL("all"),
	    STRUGGLED("struggled"),
	    EASY("Easy");

	    private final String description;

	    TypeOfProblem(String description) {
	        this.description = description;
	    }

	    public String getDescription() {
	        return description;
	    }
}
