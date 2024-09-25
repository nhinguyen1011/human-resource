package com.doan.activityservice.repositories;

import com.doan.activityservice.models.StravaToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StravaTokenRepository extends JpaRepository<StravaToken, Long> {
    StravaToken findByEmployeeId(String employeeId);
}
