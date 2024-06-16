package com.example.HealthData.Controllers;

import com.example.HealthData.Models.Workout;
import com.example.HealthData.Services.WorkoutService;
import com.example.HealthData.SummaryClasses.WorkoutSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/workout")
public class WorkoutController {
    @Autowired
    private WorkoutService service;

    @GetMapping("/data")
    public ResponseEntity<?> getWorkoutData(@RequestParam String idfv,
                                            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date startDate,
                                            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date endDate) {
        try {
            WorkoutSummary summary = service.getWorkoutSummary(idfv, startDate, endDate);
            if (summary == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No workout data found for the given period.");
            }
            return ResponseEntity.ok(summary);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to retrieve workout data: " + e.getMessage());
        }
    }

    @PostMapping
    public Workout save(@RequestBody Workout workout) {
        return service.save(workout);
    }
}