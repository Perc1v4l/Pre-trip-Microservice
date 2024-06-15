package com.example.HealthData.Controllers;

import com.example.HealthData.Models.Workout;
import com.example.HealthData.Services.WorkoutService;
import com.example.HealthData.SummaryClasses.WorkoutSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/workout")
public class WorkoutController {
    @Autowired
    private WorkoutService service;

    @GetMapping
    public WorkoutSummary getSummary(
        @RequestParam String userIdfv,
        @RequestParam String periodType) {
        return service.getSummaryByPeriod(userIdfv, periodType);
    }

    @PostMapping
    public Workout save(@RequestBody Workout workout) {
        return service.save(workout);
    }
}