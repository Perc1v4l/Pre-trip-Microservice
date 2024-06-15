package com.example.HealthData.SummaryClasses;

import com.example.HealthData.AverageData.WorkoutData;
import com.example.HealthData.Models.Workout;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class WorkoutSummary {
    private Map<LocalDateTime, WorkoutData> data = new HashMap<>();
    private double totalCalories;
    private int count;

    public void add(LocalDateTime dateTime, Workout workout) {
        WorkoutData workoutData = data.getOrDefault(dateTime, new WorkoutData());
        workoutData.add(workout);
        data.put(dateTime, workoutData);

        totalCalories += workout.getCalories();
        count++;
    }

    public void calculateAverage() {
        data.values().forEach(WorkoutData::calculateAverage);
    }

    public Map<LocalDateTime, WorkoutData> getData() {
        return data;
    }

    public double getAverageCalories() {
        return totalCalories / count;
    }
}
