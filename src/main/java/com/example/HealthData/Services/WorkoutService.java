package com.example.HealthData.Services;

import com.example.HealthData.Models.User;
import com.example.HealthData.Models.Workout;
import com.example.HealthData.Repositories.UserRepository;
import com.example.HealthData.Repositories.WorkoutRepository;
import com.example.HealthData.SummaryClasses.WorkoutSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
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

    public WorkoutSummary getWorkoutSummary(String idfv, Date startDate, Date endDate) {
        List<Workout> workoutList = repository.findAllByUserIdfvAndStartTimeBetween(idfv, startDate, endDate);

        if (workoutList.isEmpty()) {
            return null;
        }

        Workout latestWorkout = workoutList.get(workoutList.size() - 1);

        double totalCalories = workoutList.stream().mapToDouble(Workout::getCalories).sum();
        double averageCalories = workoutList.stream().mapToDouble(Workout::getCalories).average().orElse(0);

        WorkoutSummary summary = new WorkoutSummary();
        summary.setLatestWorkout(latestWorkout);
        summary.setTotalCalories(totalCalories);
        summary.setAverageCalories(averageCalories);

        return summary;
    }
}
