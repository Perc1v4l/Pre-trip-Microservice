package com.example.HealthData.Services;

import com.example.HealthData.Models.HealthData;
import com.example.HealthData.Models.HealthDataSummary;
import com.example.HealthData.Models.User;
import com.example.HealthData.Repositories.HealthDataRepository;
import com.example.HealthData.Repositories.UserRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.OptionalDouble;

@Service
public class HealthDataService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HealthDataRepository healthDataRepository;

    @Transactional
    public void saveHealthData(String idfv, int bloodPressureSystolic, int bloodPressureDiastolic, int pulse, double bloodAlcoholLevel) {
        User user = userRepository.findByIdfv(idfv);
        if (user == null) {
            user = new User();
            user.setIdfv(idfv);
            user = userRepository.save(user);
        }

        HealthData healthData = new HealthData();
        healthData.setUserIdfv(user.getIdfv());
        healthData.setBloodPressureSystolic(bloodPressureSystolic);
        healthData.setBloodPressureDiastolic(bloodPressureDiastolic);
        healthData.setPulse(pulse);
        healthData.setBloodAlcoholLevel(bloodAlcoholLevel);

        healthDataRepository.save(healthData);
    }
    @Transactional(readOnly = true)
    public HealthDataSummary getHealthDataSummary(String idfv, Date startDate, Date endDate) {
        List<HealthData> healthDataList = healthDataRepository.findByUserIdfvAndDateRange(idfv, startDate, endDate);

        if (healthDataList.isEmpty()) {
            return null;
        }

        HealthData latestData = healthDataList.get(healthDataList.size() - 1);

        OptionalDouble avgBloodPressureSystolic = healthDataList.stream().mapToInt(HealthData::getBloodPressureSystolic).average();
        OptionalDouble avgBloodPressureDiastolic = healthDataList.stream().mapToInt(HealthData::getBloodPressureDiastolic).average();
        OptionalDouble avgPulse = healthDataList.stream().mapToInt(HealthData::getPulse).average();
        OptionalDouble avgBloodAlcoholLevel = healthDataList.stream().mapToDouble(HealthData::getBloodAlcoholLevel).average();

        HealthDataSummary summary = new HealthDataSummary();
        summary.setLatestData(latestData);
        summary.setAverageBloodPressureSystolic(avgBloodPressureSystolic.isPresent() ? avgBloodPressureSystolic.getAsDouble() : 0);
        summary.setAverageBloodPressureDiastolic(avgBloodPressureDiastolic.isPresent() ? avgBloodPressureDiastolic.getAsDouble() : 0);
        summary.setAveragePulse(avgPulse.isPresent() ? avgPulse.getAsDouble() : 0);
        summary.setAverageBloodAlcoholLevel(avgBloodAlcoholLevel.isPresent() ? avgBloodAlcoholLevel.getAsDouble() : 0);

        return summary;
    }
}
