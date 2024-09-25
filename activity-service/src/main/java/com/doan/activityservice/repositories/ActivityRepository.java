package com.doan.activityservice.repositories;
import com.doan.activityservice.models.Activity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Integer> {

    List<Activity> findByEmployeeIdAndStartDateAfterAndEndDateBefore(int employeeId, LocalDateTime startDate, LocalDateTime endDate);
    // Custom query methods for activities can be added here if needed
}
