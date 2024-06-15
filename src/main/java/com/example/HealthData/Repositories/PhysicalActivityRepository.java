package com.example.HealthData.Repositories;

import com.example.HealthData.Models.PhysicalActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface PhysicalActivityRepository extends JpaRepository<PhysicalActivity, Long> {
    @Query("SELECT h FROM HealthData h WHERE h.userIdfv = :idfv AND h.createdAt BETWEEN :startDate AND :endDate")
    List<PhysicalActivity> findByUserIdfvAndDateRange(@Param("idfv") String idfv, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
