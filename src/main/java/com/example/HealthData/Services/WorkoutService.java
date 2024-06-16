package com.example.HealthData.Services;

import com.example.HealthData.Models.User;
import com.example.HealthData.Models.Workout;
import com.example.HealthData.Repositories.UserRepository;
import com.example.HealthData.Repositories.WorkoutRepository;
import com.example.HealthData.SummaryClasses.WorkoutSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class WorkoutService {
    @Autowired
    private WorkoutRepository repository;
    @Autowired
    private UserRepository userRepository;

    public List<Workout> getAll() {
        return repository.findAll();
    }

    public Workout save(Workout workout) {
        User user = userRepository.findByIdfv(workout.getUserIdfv());
        if (user == null) {
            user = new User();
            user.setIdfv(workout.getUserIdfv());
            user = userRepository.save(user);
        }

        return repository.save(workout);
    }

    public List<Workout> getByPeriod(String userIdfv, LocalDateTime start, LocalDateTime end) {
        return repository.findAllByUserIdfvAndStartTimeBetween(userIdfv, start, end);
    }

    public WorkoutSummary getSummaryByPeriod(String userIdfv, String periodType) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startDateTime;

        switch (periodType.toLowerCase()) {
            case "day":
                startDateTime = now.truncatedTo(ChronoUnit.DAYS);
                break;
            case "week":
                startDateTime = now.minusWeeks(1);
                break;
            case "month":
                startDateTime = now.minusMonths(1);
                break;
            case "half-year":
                startDateTime = now.minusMonths(6);
                break;
            case "year":
                startDateTime = now.minusYears(1);
                break;
            default:
                throw new IllegalArgumentException("Invalid period type: " + periodType);
        }

        List<Workout> workouts = getByPeriod(userIdfv, startDateTime, now);

        WorkoutSummary summary = new WorkoutSummary();
        for (Workout workout : workouts) {
            LocalDateTime intervalDateTime = null;

            switch (periodType.toLowerCase()) {
                case "day":
                    intervalDateTime = workout.getWorkoutStart().truncatedTo(ChronoUnit.HOURS);
                    break;
                case "week":
                case "month":
                    intervalDateTime = workout.getWorkoutStart().truncatedTo(ChronoUnit.DAYS);
                    break;
                case "half-year":
                case "year":
                    intervalDateTime = workout.getWorkoutStart().withDayOfMonth(1).truncatedTo(ChronoUnit.MONTHS);
                    break;
            }

            summary.add(intervalDateTime, workout);
        }

        summary.calculateAverage();

        return summary;
    }
}
