package com.example.HealthData.SummaryClasses;

import com.example.HealthData.Models.Sleep;
import com.example.HealthData.AverageData.SleepData;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class SleepSummary {
    private Map<LocalDateTime, SleepData> data = new HashMap<>();
    private double totalSleepDuration;
    private int count;

    public void add(LocalDateTime dateTime, Sleep sleep) {
        SleepData sleepData = data.getOrDefault(dateTime, new SleepData());
        sleepData.add(sleep);
        data.put(dateTime, sleepData);

        totalSleepDuration += sleep.getSleepDuration();
        count++;
    }

    public void calculateAverage() {
        data.values().forEach(SleepData::calculateAverage);
    }

    public Map<LocalDateTime, SleepData> getData() {
        return data;
    }

    public double getAverageSleepDuration() {
        return totalSleepDuration / count;
    }
}
