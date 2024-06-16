package com.example.HealthData.Controllers;


import com.example.HealthData.Models.PhysicalActivity;
import com.example.HealthData.Services.PhysicalActivityService;
import com.example.HealthData.SummaryClasses.PhysicalActivitySummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/physical-activity")
public class PhysicalActivityController {
    @Autowired
    private PhysicalActivityService service;

    @GetMapping("/data")
    public ResponseEntity<?> getPhysicalActivity(@RequestParam String idfv,
                                                 @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
                                                 @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {
        try {
            PhysicalActivitySummary summary = service.getPhysicalActivitySummary(idfv, startDate, endDate);
            if (summary == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No physical activity data found for the given period.");
            }
            return ResponseEntity.ok(summary);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to retrieve physical activity data: " + e.getMessage());
        }
    }

    @PostMapping
    public PhysicalActivity save(@RequestBody PhysicalActivity activity) {
        return service.save(activity);
    }
}