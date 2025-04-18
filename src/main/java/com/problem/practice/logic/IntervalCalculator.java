package com.problem.practice.logic;

import org.springframework.stereotype.Component;

import com.problem.practice.entity.Problem;

@Component
public class IntervalCalculator {
   
//	private double easeFactor = 1.3; // Initial ease factor
//    private int previousInterval = 1; // Initial interval in days
	
	/*
	Example: Calculating Review Interval and Updating Ease Factor

	Initial Problem:
	- Interval: 1 day
	- Ease Factor: 2.5
	- Review Quality (Q): 4 (Good recall)

	Step 1: Define Quality Multiplier
	Q = 4 → Multiplier = 1.2

	Step 2: Calculate New Interval
	New Interval = Previous Interval × Ease Factor × Multiplier
	New Interval = 1 × 2.5 × 1.2 = 3 days

	Step 3: Update Ease Factor
	New Ease Factor = Ease Factor + (0.1 - (5 - Q) × 0.08)
	New Ease Factor = 2.5 + (0.1 - (5 - 4) × 0.08)
	New Ease Factor = 2.5 + 0.02 = 2.52

	Updated Problem:
	- Interval: 3 days
	- Ease Factor: 2.52
	- Practice Date: Today (LocalDate.now())
	*/

    public int calculateNewInterval(int review,Problem prob) {
        // Define the quality multiplier
        double multiplier = switch (review) {
            case 5 -> 1.3;
            case 4 -> 1.2;
            case 3 -> 1.0;
            case 2 -> 0.7;
            case 1 -> 0.5;
            default -> throw new IllegalArgumentException("Invalid review quality");
        };
        
        double easeFactor = prob.getEaseFactor();

        // Calculate the new interval
        int newInterval = (int) Math.round(prob.getInterval() * easeFactor * multiplier);

        // Update the ease factor
         easeFactor += (0.1 - (5 - review) * 0.08);
        easeFactor = Math.max(1.3, easeFactor); // Ensure ease factor doesn't go below 1.3
        
        prob.setEaseFactor(easeFactor);
        	
        return newInterval;
   
   }
}