package com.example.HealthData.Repositories;

import com.example.HealthData.Models.Workout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface WorkoutRepository extends JpaRepository<Workout, Long> {
    @Query("SELECT h FROM HealthData h WHERE h.userIdfv = :idfv AND h.createdAt BETWEEN :startDate AND :endDate")
    List<Workout> findAllByUserIdfvAndStartTimeBetween(@Param("idfv") String idfv, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
}
