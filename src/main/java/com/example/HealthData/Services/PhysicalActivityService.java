package com.example.HealthData.Services;

import com.example.HealthData.Models.PhysicalActivity;
import com.example.HealthData.Repositories.PhysicalActivityRepository;
import com.example.HealthData.SummaryClasses.PhysicalActivitySummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;


@Service
public class PhysicalActivityService {
    @Autowired
    private PhysicalActivityRepository repository;

    public List<PhysicalActivity> getAll() {
        return repository.findAll();
    }

    public PhysicalActivity save(PhysicalActivity activity) {
        return repository.save(activity);
    }

    @Transactional(readOnly = true)
    public List<PhysicalActivity> getByPeriod(String idfv, LocalDate startDate, LocalDate endDate) {
        return repository.findByUserIdfvAndDateRange(idfv, startDate, endDate);
    }

    public PhysicalActivitySummary getSummaryByPeriod(String userId, String periodType) {
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
            case "year":
                startDate = now.minusYears(1);
                break;
            default:
                throw new IllegalArgumentException("Invalid period type: " + periodType);
        }

        List<PhysicalActivity> activities = getByPeriod(userId, startDate, now);

        // Aggregate data by interval
        PhysicalActivitySummary summary = new PhysicalActivitySummary();
        for (PhysicalActivity activity : activities) {
            LocalDate activityDate = activity.getActivityDate();
            LocalDateTime activityDateTime = activityDate.atStartOfDay();
            LocalDateTime intervalDateTime = null;

            switch (periodType.toLowerCase()) {
                case "day":
                    intervalDateTime = activityDateTime.truncatedTo(ChronoUnit.HOURS);
                    break;
                case "week":
                case "month":
                    intervalDateTime = activityDateTime.truncatedTo(ChronoUnit.DAYS);
                    break;
                case "half-year":
                case "year":
                    intervalDateTime = activityDateTime.withDayOfMonth(1).truncatedTo(ChronoUnit.MONTHS);
                    break;
            }
            summary.add(intervalDateTime, activity);
        }

        // Calculate average
        summary.calculateAverage();

        return summary;
    }
}
