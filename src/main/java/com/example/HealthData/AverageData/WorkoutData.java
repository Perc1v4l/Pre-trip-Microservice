package com.example.HealthData.AverageData;

import com.example.HealthData.Models.Workout;

public class WorkoutData {
    private double calories;
    private int count;

    public void add(Workout workout) {
        calories += workout.getCalories();
        count++;
    }

    public void calculateAverage() {
        calories /= count;
    }

    public double getCalories() {
        return calories;
    }
}
