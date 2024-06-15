package com.example.HealthData.AverageData;

import com.example.HealthData.Models.BodyMeasurement;

public class BodyMeasurementData {
    private double height;
    private double weight;
    private int count;

    public void add(BodyMeasurement measurement) {
        height += measurement.getHeight();
        weight += measurement.getWeight();
        count++;
    }

    public void calculateAverage() {
        height /= count;
        weight /= count;
    }

    public double getHeight() {
        return height;
    }

    public double getWeight() {
        return weight;
    }
}