package com.example.HealthData.Models;

import lombok.Data;

@Data
public class HealthDataSummary {
    private HealthData latestData;
    private double averageBloodPressureSystolic;
    private double averageBloodPressureDiastolic;
    private double averagePulse;
    private double averageBloodAlcoholLevel;
    private double averageTemperature;
}
