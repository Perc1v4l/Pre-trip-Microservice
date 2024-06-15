package com.example.HealthData.SummaryClasses;

import com.example.HealthData.Models.PhysicalActivity;
import com.example.HealthData.AverageData.PhysicalActivityData;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class PhysicalActivitySummary {
    private Map<LocalDateTime, PhysicalActivityData> data = new HashMap<>();
    private double totalSteps;
    private double totalDistance;
    private double totalCalories;
    private int count;

    public void add(LocalDateTime dateTime, PhysicalActivity activity) {
        PhysicalActivityData activityData = data.getOrDefault(dateTime, new PhysicalActivityData());
        activityData.add(activity);
        data.put(dateTime, activityData);

        totalSteps += activity.getSteps();
        totalDistance += activity.getDistance();
        totalCalories += activity.getCalories();
        count++;
    }

    public void calculateAverage() {
        data.values().forEach(PhysicalActivityData::calculateAverage);
    }

}
