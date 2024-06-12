package com.example.HealthData.Repositories;

import com.example.HealthData.Models.HealthData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface HealthDataRepository extends JpaRepository<HealthData, Long> {
    @Query("SELECT h FROM HealthData h WHERE h.userIdfv = :idfv AND h.createdAt BETWEEN :startDate AND :endDate")
    List<HealthData> findByUserIdfvAndDateRange(@Param("idfv") String idfv, @Param("startDate") Date startDate, @Param("endDate") Date endDate);
}
