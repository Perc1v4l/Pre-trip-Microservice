package com.example.HealthData.SummaryClasses;

import com.example.HealthData.AverageData.BodyMeasurementData;
import com.example.HealthData.Models.BodyMeasurement;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class BodyMeasurementSummary {
    private Map<LocalDateTime, BodyMeasurementData> data = new HashMap<>();
    private double totalHeight;
    private double totalWeight;
    private int count;

    public void add(LocalDateTime dateTime, BodyMeasurement measurement) {
        BodyMeasurementData measurementData = data.getOrDefault(dateTime, new BodyMeasurementData());
        measurementData.add(measurement);
        data.put(dateTime, measurementData);

        totalHeight += measurement.getHeight();
        totalWeight += measurement.getWeight();
        count++;
    }

    public void calculateAverage() {
        data.values().forEach(BodyMeasurementData::calculateAverage);
    }

    public Map<LocalDateTime, BodyMeasurementData> getData() {
        return data;
    }

    public double getAverageHeight() {
        return totalHeight / count;
    }

    public double getAverageWeight() {
        return totalWeight / count;
    }
}