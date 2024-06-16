package com.example.HealthData.SummaryClasses;

import com.example.HealthData.Models.Workout;
import lombok.Data;

@Data
public class WorkoutSummary {
    private Workout latestWorkout;
    private double totalCalories;
    private double averageCalories;
}