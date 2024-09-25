package com.doan.rewardservice.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Voucher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String code;

    private Integer value;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    @JsonIgnoreProperties("vouchers")
    private Reward reward;

    private LocalDateTime createdAt;

    private LocalDateTime expiresAt;

    private Boolean isRedeemed = false;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        // Set expiration date to 3 months from creation
        expiresAt = createdAt.plusMonths(3);
    }
}
