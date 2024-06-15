package com.example.HealthData.Controllers;


import com.example.HealthData.Models.PhysicalActivity;
import com.example.HealthData.Services.PhysicalActivityService;
import com.example.HealthData.SummaryClasses.PhysicalActivitySummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/physical-activity")
public class PhysicalActivityController {
    @Autowired
    private PhysicalActivityService service;

    @GetMapping
    public PhysicalActivitySummary getSummary(
        @RequestParam String userIdfv,
        @RequestParam String periodType) {
        return service.getSummaryByPeriod(userIdfv, periodType);
    }

    @PostMapping
    public PhysicalActivity save(@RequestBody PhysicalActivity activity) {
        return service.save(activity);
    }
}