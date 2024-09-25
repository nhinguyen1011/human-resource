package com.doan.rewardservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RewardUpdateDTO {
    private int rewardId;
    private int employeeId; // Foreign key reference to Employee
    private String rewardDescription;
    private int pointsAwarded;
    private String rewardDate;
}
