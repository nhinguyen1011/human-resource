package com.doan.activityservice.services;

import com.doan.activityservice.models.Activity;
import com.doan.activityservice.models.ActivitySummary;
import com.doan.activityservice.repositories.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ActivityService {

    @Autowired
    private ActivityRepository activityRepository;

    public void saveActivities(List<Activity> activities) {
        activityRepository.saveAll(activities);
    }

    public ActivitySummary getSummary(String employeeId, LocalDateTime start, LocalDateTime end) {
        List<Activity> activities = activityRepository.findByEmployeeIdAndStartDateAfterAndEndDateBefore(Integer.parseInt(employeeId), start, end);

        double totalDistance = activities.stream().mapToDouble(Activity::getDistance).sum();
        long totalMovingTime = activities.stream().mapToLong(Activity::getMovingTime).sum();

        return new ActivitySummary(employeeId, totalDistance, totalMovingTime, activities.size());
    }
}
