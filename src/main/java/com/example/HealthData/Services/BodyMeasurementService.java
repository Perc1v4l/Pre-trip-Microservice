package com.example.HealthData.Services;

import com.example.HealthData.Models.BodyMeasurement;
import com.example.HealthData.Models.User;
import com.example.HealthData.Repositories.BodyMeasurementRepository;
import com.example.HealthData.Repositories.UserRepository;
import com.example.HealthData.SummaryClasses.BodyMeasurementSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class BodyMeasurementService {
    @Autowired
    private BodyMeasurementRepository repository;
    @Autowired
    private UserRepository userRepository;

    public List<BodyMeasurement> getAll() {
        return repository.findAll();
    }

    public BodyMeasurement save(BodyMeasurement measurement) {
        User user = userRepository.findByIdfv(measurement.getUserIdfv());
        if (user == null) {
            user = new User();
            user.setIdfv(measurement.getUserIdfv());
            user = userRepository.save(user);
        }

        return repository.save(measurement);
    }

    @Transactional(readOnly = true)
    public BodyMeasurementSummary getBodyMeasurementSummary(String idfv, Date startDate, Date endDate) {
        List<BodyMeasurement> bodyMeasurementList = repository.findAllByUserIdfvAndMeasurementDateBetween(idfv, startDate, endDate);

        if (bodyMeasurementList.isEmpty()) {
            return null;
        }

        BodyMeasurement latestMeasurement = bodyMeasurementList.get(bodyMeasurementList.size() - 1);

        double averageHeight = bodyMeasurementList.stream().mapToDouble(BodyMeasurement::getHeight).average().orElse(0);
        double averageWeight = bodyMeasurementList.stream().mapToDouble(BodyMeasurement::getWeight).average().orElse(0);

        BodyMeasurementSummary summary = new BodyMeasurementSummary();
        summary.setLatestMeasurement(latestMeasurement);
        summary.setAverageHeight(averageHeight);
        summary.setAverageWeight(averageWeight);

        return summary;
    }
}
