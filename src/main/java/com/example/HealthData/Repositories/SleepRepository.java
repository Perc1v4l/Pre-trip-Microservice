package com.example.HealthData.Repositories;

import com.example.HealthData.Models.Sleep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface SleepRepository extends JpaRepository<Sleep, Long> {
    @Query("SELECT h FROM Sleep h WHERE h.userIdfv = :idfv AND h.sleepDate BETWEEN :startDate AND :endDate")
    List<Sleep> findAllByUserIdfvAndSleepDateBetween(@Param("idfv") String idfv, @Param("startDate") Date startDate, @Param("endDate") Date endDate);
}
