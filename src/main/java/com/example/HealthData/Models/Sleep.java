package com.example.HealthData.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "sleep")
@NoArgsConstructor
@AllArgsConstructor
public class Sleep {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_idfv", nullable = false)
    private String userIdfv;

    @Column(name = "sleep_duration")
    private double sleepDuration;

    @Column(name = "sleep_start")
    private LocalDateTime sleepStart;

    @Column(name = "sleep_end")
    private LocalDateTime sleepEnd;

    @Column(name = "sleep_date")
    private LocalDate sleepDate;
}
