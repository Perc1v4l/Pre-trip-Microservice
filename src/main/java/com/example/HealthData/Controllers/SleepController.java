package com.example.HealthData.Controllers;

import com.example.HealthData.Models.Sleep;
import com.example.HealthData.Services.SleepService;
import com.example.HealthData.SummaryClasses.SleepSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;

@RestController
@RequestMapping("/sleep")
public class SleepController {
    @Autowired
    private SleepService service;

    @GetMapping("/data")
    public ResponseEntity<?> getSleepData(@RequestParam String idfv,
                                          @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
                                          @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {
        try {
            SleepSummary summary = service.getSleepSummary(idfv, startDate, endDate);
            if (summary == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No sleep data found for the given period.");
            }
            return ResponseEntity.ok(summary);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to retrieve sleep data: " + e.getMessage());
        }
    }

    @PostMapping
    public Sleep save(@RequestBody Sleep sleep) {
        return service.save(sleep);
    }
}
