package com.example.HealthData.Services;

import com.example.HealthData.Models.PhysicalActivity;
import com.example.HealthData.Models.User;
import com.example.HealthData.Repositories.PhysicalActivityRepository;
import com.example.HealthData.Repositories.UserRepository;
import com.example.HealthData.SummaryClasses.PhysicalActivitySummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class PhysicalActivityService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PhysicalActivityRepository physicalActivityRepository;

    @Transactional
    public PhysicalActivity save(PhysicalActivity activity) {
        User user = userRepository.findByIdfv(activity.getUserIdfv());
        if (user == null) {
            user = new User();
            user.setIdfv(activity.getUserIdfv());
            user = userRepository.save(user);
        }
        return physicalActivityRepository.save(activity);
    }

    @Transactional(readOnly = true)
    public PhysicalActivitySummary getPhysicalActivitySummary(String idfv, Date startDate, Date endDate) {
        List<PhysicalActivity> physicalActivityList = physicalActivityRepository.findByUserIdfvAndDateRange(idfv, startDate, endDate);

        if (physicalActivityList.isEmpty()) {
            return null;
        }

        PhysicalActivity latestActivity = physicalActivityList.get(physicalActivityList.size() - 1);

        double totalSteps = physicalActivityList.stream().mapToDouble(PhysicalActivity::getSteps).sum();
        double totalDistance = physicalActivityList.stream().mapToDouble(PhysicalActivity::getDistance).sum();
        double totalCalories = physicalActivityList.stream().mapToDouble(PhysicalActivity::getCalories).sum();

        double averageSteps = physicalActivityList.stream().mapToDouble(PhysicalActivity::getSteps).average().orElse(0);
        double averageDistance = physicalActivityList.stream().mapToDouble(PhysicalActivity::getDistance).average().orElse(0);
        double averageCalories = physicalActivityList.stream().mapToDouble(PhysicalActivity::getCalories).average().orElse(0);

        PhysicalActivitySummary summary = new PhysicalActivitySummary();
        summary.setLatestActivity(latestActivity);
        summary.setTotalSteps(totalSteps);
        summary.setTotalDistance(totalDistance);
        summary.setTotalCalories(totalCalories);
        summary.setAverageSteps(averageSteps);
        summary.setAverageDistance(averageDistance);
        summary.setAverageCalories(averageCalories);

        return summary;
    }
}