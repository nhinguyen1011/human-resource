package com.doan.rewardservice.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reward {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer employeeId; // Foreign key to Employee

    private Integer points; // Points the employee has

    @OneToMany(mappedBy = "reward", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("reward")
    private List<Voucher> vouchers;

    public Reward(Long employeeId, int i) {
        this.employeeId = Math.toIntExact(employeeId);
        this.points = i;
    }
}