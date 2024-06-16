package com.example.HealthData.Services;

import com.example.HealthData.Models.Sleep;
import com.example.HealthData.Models.User;
import com.example.HealthData.Repositories.SleepRepository;
import com.example.HealthData.Repositories.UserRepository;
import com.example.HealthData.SummaryClasses.SleepSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

@Service
public class SleepService {
    @Autowired
    private SleepRepository repository;
    @Autowired
    private UserRepository userRepository;

    public List<Sleep> getAll() {
        return repository.findAll();
    }

    public Sleep save(Sleep sleep) {
        User user = userRepository.findByIdfv(sleep.getUserIdfv());
        if (user == null) {
            user = new User();
            user.setIdfv(sleep.getUserIdfv());
            user = userRepository.save(user);
        }
        return repository.save(sleep);
    }

    @Transactional(readOnly = true)
    public SleepSummary getSleepSummary(String idfv, Date startDate, Date endDate) {
        List<Sleep> sleepList = repository.findAllByUserIdfvAndSleepDateBetween(idfv, startDate, endDate);

        if (sleepList.isEmpty()) {
            return null;
        }

        Sleep latestSleep = sleepList.get(sleepList.size() - 1);

        double totalSleepDuration = sleepList.stream().mapToDouble(Sleep::getSleepDuration).sum();
        double averageSleepDuration = sleepList.stream().mapToDouble(Sleep::getSleepDuration).average().orElse(0);

        SleepSummary summary = new SleepSummary();
        summary.setLatestSleep(latestSleep);
        summary.setTotalSleepDuration(totalSleepDuration);
        summary.setAverageSleepDuration(averageSleepDuration);

        return summary;
    }
}
