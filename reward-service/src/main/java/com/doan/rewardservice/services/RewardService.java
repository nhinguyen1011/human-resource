package com.doan.rewardservice.services;

import com.doan.rewardservice.dto.EmployeeDTO;
import com.doan.rewardservice.dto.VoucherDTO;
import com.doan.rewardservice.models.Reward;
import com.doan.rewardservice.models.Voucher;
import com.doan.rewardservice.repositories.RewardRepository;
import com.doan.rewardservice.repositories.VoucherRepository;
import org.hibernate.sql.exec.ExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.UUID;

@Service
public class RewardService {

    @Autowired
    private RewardRepository rewardRepository;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private VoucherRepository voucherRepository;

    private static final int MINIMUM_POINTS_TO_CONVERT = 100;
    private final String EMPLOYEE_SERVICE_URL = "http://localhost:8081/api/employees/";
    // Method to assign points monthly to all employees
    @Transactional
    public void assignMonthlyPoints(Long employeeId, int points) {
        // Call employee-service to verify the employee exists
        try {
            String url = EMPLOYEE_SERVICE_URL + employeeId;
            ResponseEntity<EmployeeDTO> response = restTemplate.getForEntity(url, EmployeeDTO.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                // If the employee exists, proceed with assigning points
                Reward reward = rewardRepository.findByEmployeeId(Math.toIntExact(employeeId)).stream().findFirst()
                        .orElse(new Reward(employeeId, 0));  // If the reward doesn't exist, create a new one
                reward.setPoints(reward.getPoints() + points);
                rewardRepository.save(reward);
            } else {
                throw new IllegalArgumentException("Employee not found");
            }
        } catch (HttpClientErrorException.NotFound e) {
            throw new IllegalArgumentException("Employee not found");
        }
    }

    // Method to transfer points from one employee to another
    @Transactional
    public void transferPoints(Long fromEmployeeId, Long toEmployeeId, int points) {
        // Find the source employee's reward entry
        Reward fromReward = rewardRepository.findByEmployeeId(Math.toIntExact(fromEmployeeId)).stream().findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Source employee not found"));

        // Find the destination employee's reward entry, or create a new one if it doesn't exist
        Reward toReward = rewardRepository.findByEmployeeId(Math.toIntExact(toEmployeeId)).stream().findFirst()
                .orElseGet(() -> {
                    Reward newReward = new Reward();
                    newReward.setEmployeeId(Math.toIntExact(toEmployeeId));
                    newReward.setPoints(0); // Initialize with 0 points
                    return newReward;
                });

        // Check if the source employee has enough points to transfer
        if (fromReward.getPoints() < points) {
            throw new IllegalArgumentException("Insufficient points");
        }

        // Update the points for both employees
        fromReward.setPoints(fromReward.getPoints() - points);
        toReward.setPoints(toReward.getPoints() + points);

        // Save the updated reward records
        rewardRepository.save(fromReward);
        rewardRepository.save(toReward);
    }




    // Method to get the total points for an employee
    @Transactional(readOnly = true)
    public int getTotalPoints(Long employeeId) {
        List<Reward> rewards = rewardRepository.findByEmployeeId(Math.toIntExact(employeeId));
        if (rewards.isEmpty()) {
            return 0;  // Return 0 if no rewards found for the employee
        }
        return rewards.stream().mapToInt(Reward::getPoints).sum();
    }

    @Transactional
    public void createReward(Long employeeId, int points) {
        Reward reward = rewardRepository.findByEmployeeId(Math.toIntExact(employeeId)).stream().findFirst()
                .orElseGet(() -> {
                    Reward newReward = new Reward();
                    newReward.setEmployeeId(Math.toIntExact(employeeId));
                    newReward.setPoints(0); // Initialize with 0 points
                    return newReward;
                });

        reward.setPoints(reward.getPoints() + points);
        rewardRepository.save(reward);
    }

    public VoucherDTO convertPointsToVoucher(Long employeeId, int pointsToConvert) {
        if (pointsToConvert < MINIMUM_POINTS_TO_CONVERT) {
            throw new IllegalArgumentException("Số điểm đổi phải ít nhất là " + MINIMUM_POINTS_TO_CONVERT);
        }

        Reward reward = rewardRepository.findByEmployeeId(Math.toIntExact(employeeId)).stream().findFirst()
                .orElseThrow(() -> new ExecutionException("Không tìm thấy phần thưởng cho nhân viên này"));

        if (reward.getPoints() < pointsToConvert) {
            throw new IllegalArgumentException("Số điểm đổi không thể lớn hơn số điểm hiện có");
        }

        // Tạo voucher mới
        Voucher voucher = new Voucher();
        voucher.setCode(generateVoucherCode());
        voucher.setValue(pointsToConvert);
        voucher.setCreatedAt(LocalDateTime.now());
        voucher.setExpiresAt(LocalDateTime.now().plusMonths(3)); // Ví dụ: voucher hết hạn sau 3 tháng
        voucher.setIsRedeemed(false);
        voucher.setReward(reward);

        // Cập nhật điểm của nhân viên
        reward.setPoints(reward.getPoints() - pointsToConvert);

        voucherRepository.save(voucher);
        rewardRepository.save(reward);

        return convertToDTO(voucher);
    }

    private String generateVoucherCode() {
        return "VCR-" + UUID.randomUUID().toString().substring(0, 12).toUpperCase();
    }

    private VoucherDTO convertToDTO(Voucher voucher) {
        VoucherDTO dto = new VoucherDTO();
        dto.setId(voucher.getId());
        dto.setCode(voucher.getCode());
        dto.setValue(voucher.getValue());
        dto.setCreatedAt(voucher.getCreatedAt());
        dto.setExpiresAt(voucher.getExpiresAt());
        dto.setIsRedeemed(voucher.getIsRedeemed());
        return dto;
    }


}
