package com.example.HealthData.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Entity
@Table(name = "workout")
@NoArgsConstructor
@AllArgsConstructor
public class Workout {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_idfv", nullable = false)
    private String userIdfv;

    @Column(name = "workout_type")
    private String workoutType;

    @Column(name = "calories")
    private double calories;

    @Column(name = "workout_start")
    private LocalDateTime workoutStart;

    @Column(name = "workout_end")
    private LocalDateTime workoutEnd;

    @Column(name = "workout_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date workoutDate;
}
