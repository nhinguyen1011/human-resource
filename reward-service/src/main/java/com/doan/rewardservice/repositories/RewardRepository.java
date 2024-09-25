package com.doan.rewardservice.repositories;

import com.doan.rewardservice.models.Reward;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RewardRepository extends JpaRepository<Reward, Long> {

    List<Reward> findByEmployeeId(Integer employeeId);
}