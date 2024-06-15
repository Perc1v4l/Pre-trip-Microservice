package com.example.HealthData.Controllers;

import com.example.HealthData.Models.Sleep;
import com.example.HealthData.Services.SleepService;
import com.example.HealthData.SummaryClasses.SleepSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sleep")
public class SleepController {
    @Autowired
    private SleepService service;

    @GetMapping
    public SleepSummary getSummary(
        @RequestParam String userIdfv,
        @RequestParam String periodType) {
        return service.getSummaryByPeriod(userIdfv, periodType);
    }

    @PostMapping
    public Sleep save(@RequestBody Sleep sleep) {
        return service.save(sleep);
    }
}
