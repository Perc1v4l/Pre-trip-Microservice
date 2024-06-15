package com.example.HealthData.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "physical_activity")
@NoArgsConstructor
@AllArgsConstructor
public class PhysicalActivity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_idfv", nullable = false)
    private String userIdfv;

    @Column(name = "steps")
    private int steps;

    @Column(name = "distance")
    private double distance;

    @Column(name = "calories")
    private double calories;

    @Column(name = "activity_date")
    private LocalDate activityDate;
}
