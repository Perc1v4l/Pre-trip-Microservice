package com.example.HealthData.Controllers;

import com.example.HealthData.Models.HealthDataSummary;
import com.example.HealthData.Services.HealthDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/api/health")

public class HealthDataController {
    @Autowired
    private HealthDataService healthDataService;

    @PostMapping("/data")
    public ResponseEntity<String> saveHealthData(@RequestBody Map<String, Object> healthData) {
        try {
            String idfv = (String) healthData.get("idfv");
            int bloodPressureSystolic = (int) healthData.get("blood_pressure_systolic");
            int bloodPressureDiastolic = (int) healthData.get("blood_pressure_diastolic");
            int pulse = (int) healthData.get("pulse");
            double bloodAlcoholLevel = (double) healthData.get("blood_alcohol_level");

            healthDataService.saveHealthData(idfv, bloodPressureSystolic, bloodPressureDiastolic, pulse, bloodAlcoholLevel);
            return ResponseEntity.status(HttpStatus.CREATED).body("Health data saved successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to save health data: " + e.getMessage());
        }
    }
    @GetMapping("/data")
    public ResponseEntity<?> getHealthData(@RequestParam String idfv,
                                           @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
                                           @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {
        try {
            HealthDataSummary summary = healthDataService.getHealthDataSummary(idfv, startDate, endDate);
            if (summary == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No health data found for the given period.");
            }
            return ResponseEntity.ok(summary);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to retrieve health data: " + e.getMessage());
        }
    }
}
