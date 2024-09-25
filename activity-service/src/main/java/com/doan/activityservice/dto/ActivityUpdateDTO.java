package com.doan.activityservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActivityUpdateDTO {
    private int activityId;
    private int employeeId; // Foreign key reference to Employee
    private String campaignName;
    private double kilometersRun;
    private String startDate;
    private String endDate;
}