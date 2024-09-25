package com.doan.rewardservice.repositories;

import com.doan.rewardservice.models.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VoucherRepository extends JpaRepository<Voucher, Long> {
    Optional<Voucher> findByCode(String code);
    List<Voucher> findByRewardEmployeeId(Integer reward_employeeId);
}
