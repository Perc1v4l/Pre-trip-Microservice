package com.example.HealthData.AverageData;

import com.example.HealthData.Models.Sleep;

public class SleepData {
    private double sleepDuration;
    private int count;

    public void add(Sleep sleep) {
        sleepDuration += sleep.getSleepDuration();
        count++;
    }

    public void calculateAverage() {
        sleepDuration /= count;
    }

    public double getSleepDuration() {
        return sleepDuration;
    }
}