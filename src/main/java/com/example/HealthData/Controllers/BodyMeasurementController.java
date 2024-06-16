package com.example.HealthData.Controllers;

import com.example.HealthData.Models.BodyMeasurement;
import com.example.HealthData.Services.BodyMeasurementService;
import com.example.HealthData.SummaryClasses.BodyMeasurementSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/body-measurement")
public class BodyMeasurementController {
    @Autowired
    private BodyMeasurementService service;

    @GetMapping("/data")
    public ResponseEntity<?> getBodyMeasurementData(@RequestParam String idfv,
                                                    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
                                                    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {
        try {
            BodyMeasurementSummary summary = service.getBodyMeasurementSummary(idfv, startDate, endDate);
            if (summary == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No body measurement data found for the given period.");
            }
            return ResponseEntity.ok(summary);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to retrieve body measurement data: " + e.getMessage());
        }
    }

    @PostMapping
    public BodyMeasurement save(@RequestBody BodyMeasurement measurement) {
        return service.save(measurement);
    }
}