package com.example.HealthData.Controllers;

import com.example.HealthData.Models.BodyMeasurement;
import com.example.HealthData.Services.BodyMeasurementService;
import com.example.HealthData.SummaryClasses.BodyMeasurementSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/body-measurement")
public class BodyMeasurementController {
    @Autowired
    private BodyMeasurementService service;

    @GetMapping
    public BodyMeasurementSummary getSummary(
        @RequestParam String userIdfv,
        @RequestParam String periodType) {
        return service.getSummaryByPeriod(userIdfv, periodType);
    }

    @PostMapping
    public BodyMeasurement save(@RequestBody BodyMeasurement measurement) {
        return service.save(measurement);
    }
}