package com.doan.rewardservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoucherDTO {
    private Long id;
    private String code;
    private Integer value;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
    private Boolean isRedeemed;

}
