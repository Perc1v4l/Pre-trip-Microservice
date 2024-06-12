package com.example.HealthData.Models;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import java.util.Date;

@Data
@Entity
@Table(name = "health_data")
public class HealthData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_idfv", nullable = false)
    private String userIdfv;

    @Column(name = "blood_pressure_systolic")
    private int bloodPressureSystolic;

    @Column(name = "blood_pressure_diastolic")
    private int bloodPressureDiastolic;

    @Column(name = "pulse")
    private int pulse;

    @Column(name = "blood_alcohol_level")
    private double bloodAlcoholLevel;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
}