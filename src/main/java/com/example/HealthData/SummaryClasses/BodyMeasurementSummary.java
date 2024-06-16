package com.example.HealthData.SummaryClasses;

import com.example.HealthData.Models.BodyMeasurement;
import lombok.Data;

@Data
public class BodyMeasurementSummary {
    private BodyMeasurement latestMeasurement;
    private double averageHeight;
    private double averageWeight;
}