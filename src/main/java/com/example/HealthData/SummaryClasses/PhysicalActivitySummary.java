package com.example.HealthData.SummaryClasses;

import com.example.HealthData.Models.PhysicalActivity;
import lombok.Data;

@Data
public class PhysicalActivitySummary {
    private PhysicalActivity latestActivity;
    private double totalSteps;
    private double totalDistance;
    private double totalCalories;
    private double averageSteps;
    private double averageDistance;
    private double averageCalories;
}