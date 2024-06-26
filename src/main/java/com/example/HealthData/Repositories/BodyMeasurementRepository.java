package com.example.HealthData.Repositories;

import com.example.HealthData.Models.BodyMeasurement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface BodyMeasurementRepository extends JpaRepository<BodyMeasurement, Long> {
    @Query("SELECT h FROM BodyMeasurement h WHERE h.userIdfv = :idfv AND h.measurementDate BETWEEN :startDate AND :endDate")
    List<BodyMeasurement> findAllByUserIdfvAndMeasurementDateBetween(@Param("idfv") String idfv, @Param("startDate") Date startDate, @Param("endDate") Date endDate);
}
