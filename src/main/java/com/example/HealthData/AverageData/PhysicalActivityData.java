package com.example.HealthData.AverageData;

import com.example.HealthData.Models.PhysicalActivity;

public class PhysicalActivityData {
    private double steps;
    private double distance;
    private double calories;
    private int count;

    public void add(PhysicalActivity activity) {
        steps += activity.getSteps();
        distance += activity.getDistance();
        calories += activity.getCalories();
        count++;
    }

    public void calculateAverage() {
        steps /= count;
        distance /= count;
        calories /= count;
    }
}
