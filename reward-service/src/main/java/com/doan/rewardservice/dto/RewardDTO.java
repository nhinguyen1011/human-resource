package com.doan.rewardservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RewardDTO {
    private int rewardId;
    private int employeeId;
    private String rewardDescription;
    private int pointsAwarded;
    private String rewardDate;
}
