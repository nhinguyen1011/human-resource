package com.doan.activityservice.controllers;

import com.doan.activityservice.dto.EmployeeDTO;
import com.doan.activityservice.models.ActivitySummary;

import com.doan.activityservice.services.StravaService;
import com.doan.activityservice.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.doan.activityservice.dto.ActivityUpdateDTO;
import com.doan.activityservice.models.Activity;
import com.doan.activityservice.services.ActivityService;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/activities")
public class ActivityController {

    @Autowired
    private ActivityService activityService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private StravaService stravaService;
//    @PutMapping("/update")
//    public Activity updateActivity(@RequestBody ActivityUpdateDTO dto) {
//        return activityService.updateActivity(dto);
//    }
//
//    @PostMapping("/create")
//    public Activity createActivity(@RequestBody ActivityUpdateDTO dto) {
//        return activityService.createActivity(dto);
//    }

    @GetMapping("/sync")
    public ResponseEntity<?> syncActivities(@RequestParam Long employeeId,
                                            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
                                            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        try {
            List<?> activities = stravaService.fetchActivities(String.valueOf(employeeId), start, end);
            return ResponseEntity.ok(activities);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        } catch (Exception e) {
            System.out.printf("Error syncing activities: " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while syncing activities");
        }
    }


    @GetMapping("/summary")
    public ResponseEntity<ActivitySummary> getSummary(
            @RequestParam String employeeId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        ActivitySummary summary = activityService.getSummary(employeeId, start, end);
        return ResponseEntity.ok(summary);
    }





}