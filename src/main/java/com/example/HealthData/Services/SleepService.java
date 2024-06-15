package com.example.HealthData.Services;

import com.example.HealthData.Models.Sleep;
import com.example.HealthData.Repositories.SleepRepository;
import com.example.HealthData.SummaryClasses.SleepSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class SleepService {
    @Autowired
    private SleepRepository repository;

    public List<Sleep> getAll() {
        return repository.findAll();
    }

    public Sleep save(Sleep sleep) {
        return repository.save(sleep);
    }

    public List<Sleep> getByPeriod(String userIdfv, LocalDate start, LocalDate end) {
        return repository.findAllByUserIdfvAndSleepDateBetween(userIdfv, start, end);
    }

    public SleepSummary getSummaryByPeriod(String userIdfv, String periodType) {
        LocalDate now = LocalDate.now();
        LocalDate startDate;

        switch (periodType.toLowerCase()) {
            case "day":
                startDate = now;
                break;
            case "week":
                startDate = now.minusWeeks(1);
                break;
            case "month":
                startDate = now.minusMonths(1);
                break;
            case "half-year":
                startDate = now.minusMonths(6);
                break;
            default:
                throw new IllegalArgumentException("Invalid period type: " + periodType);
        }

        List<Sleep> sleepRecords = getByPeriod(userIdfv, startDate, now);

        SleepSummary summary = new SleepSummary();
        for (Sleep sleep : sleepRecords) {
            LocalDate sleepDate = sleep.getSleepDate();
            LocalDateTime intervalDateTime = null;

            switch (periodType.toLowerCase()) {
                case "day":
                    intervalDateTime = sleepDate.atStartOfDay().truncatedTo(ChronoUnit.HOURS);
                    break;
                case "week":
                case "month":
                    intervalDateTime = sleepDate.atStartOfDay().truncatedTo(ChronoUnit.DAYS);
                    break;
                case "half-year":
                    intervalDateTime = sleepDate.withDayOfMonth(1).atStartOfDay().truncatedTo(ChronoUnit.MONTHS);
                    break;
            }

            summary.add(intervalDateTime, sleep);
        }

        summary.calculateAverage();

        return summary;
    }
}
