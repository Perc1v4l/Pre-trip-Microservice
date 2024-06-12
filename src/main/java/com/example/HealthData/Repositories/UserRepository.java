package com.example.HealthData.Repositories;

import com.example.HealthData.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByIdfv(String idfv);
}
