package com.example.HealthData.SummaryClasses;

import com.example.HealthData.Models.Sleep;
import lombok.Data;

@Data
public class SleepSummary {
    private Sleep latestSleep;
    private double totalSleepDuration;
    private double averageSleepDuration;
}