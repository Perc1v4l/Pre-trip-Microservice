package com.example.HealthData.Services;

import com.example.HealthData.Models.BodyMeasurement;
import com.example.HealthData.Repositories.BodyMeasurementRepository;
import com.example.HealthData.SummaryClasses.BodyMeasurementSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class BodyMeasurementService {
    @Autowired
    private BodyMeasurementRepository repository;

    public List<BodyMeasurement> getAll() {
        return repository.findAll();
    }

    public BodyMeasurement save(BodyMeasurement measurement) {
        return repository.save(measurement);
    }

    public List<BodyMeasurement> getByPeriod(String userIdfv, LocalDate start, LocalDate end) {
        return repository.findAllByUserIdfvAndMeasurementDateBetween(userIdfv, start, end);
    }

    public BodyMeasurementSummary getSummaryByPeriod(String userIdfv, String periodType) {
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

        List<BodyMeasurement> measurements = getByPeriod(userIdfv, startDate, now);

        BodyMeasurementSummary summary = new BodyMeasurementSummary();
        for (BodyMeasurement measurement : measurements) {
            LocalDate measurementDate = measurement.getMeasurementDate();
            LocalDateTime intervalDateTime = null;

            switch (periodType.toLowerCase()) {
                case "day":
                    intervalDateTime = measurementDate.atStartOfDay().truncatedTo(ChronoUnit.HOURS);
                    break;
                case "week":
                case "month":
                    intervalDateTime = measurementDate.atStartOfDay().truncatedTo(ChronoUnit.DAYS);
                    break;
                case "half-year":
                case "year":
                    intervalDateTime = measurementDate.withDayOfMonth(1).atStartOfDay().truncatedTo(ChronoUnit.MONTHS);
                    break;
            }

            summary.add(intervalDateTime, measurement);
        }

        summary.calculateAverage();

        return summary;
    }
}
