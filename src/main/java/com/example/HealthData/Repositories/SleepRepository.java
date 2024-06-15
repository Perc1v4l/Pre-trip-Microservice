package com.example.HealthData.Repositories;

import com.example.HealthData.Models.Sleep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface SleepRepository extends JpaRepository<Sleep, Long> {
    @Query("SELECT h FROM HealthData h WHERE h.userIdfv = :idfv AND h.createdAt BETWEEN :startDate AND :endDate")
    List<Sleep> findAllByUserIdfvAndSleepDateBetween(@Param("idfv") String idfv, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
