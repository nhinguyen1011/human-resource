package com.doan.activityservice.models;

import java.time.LocalDateTime;

import jakarta.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "employee_activities")
@NoArgsConstructor
public class Activity  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int activityId;

    @Column(name= "employee_id")
    private int employeeId;

    @Column(name = "campaign_name")
    private String campaignName;

    @Column(name = "kilometers_run")
    private double kilometersRun;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Column(name = "type")
    private String type;
    @Column(name = "distance")
    private double distance;
    @Column(name = "moving_time")
    private long movingTime;
}
