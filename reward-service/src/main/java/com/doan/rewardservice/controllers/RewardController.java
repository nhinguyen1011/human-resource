package com.doan.rewardservice.controllers;

import com.doan.rewardservice.dto.VoucherDTO;
import com.doan.rewardservice.models.Reward;
import com.doan.rewardservice.models.Voucher;
import com.doan.rewardservice.services.RewardService;

import java.util.List;

import org.hibernate.sql.exec.ExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rewards")
public class RewardController {

    @Autowired
    private RewardService rewardService;

    // Endpoint to assign points to an employee
    @PostMapping("/assign")
    public String assignPoints(@RequestParam Long employeeId, @RequestParam int points) {
        rewardService.assignMonthlyPoints(employeeId, points);
        return "Points assigned successfully";
    }

    // Endpoint to transfer points between employees
    @PostMapping("/transfer")
    public String transferPoints(@RequestParam Long fromEmployeeId, @RequestParam Long toEmployeeId, @RequestParam int points) {
        rewardService.transferPoints(fromEmployeeId, toEmployeeId, points);
        return "Points transferred successfully";
    }


    // Endpoint to get total points for an employee
    @GetMapping("/points/{employeeId}")
    public ResponseEntity<Integer> getTotalPoints(@PathVariable Long employeeId) {
        int point =  rewardService.getTotalPoints(employeeId);
        return ResponseEntity.status(HttpStatus.OK).body(point);
    }

    @PostMapping("/create")
    public String createReward(@RequestParam Long employeeId, @RequestParam int points) {
        rewardService.createReward(employeeId, points);
        return "Reward added successfully";
    }

    @PostMapping("/{employeeId}/convert")
    public ResponseEntity<?> convertPointsToVoucher(
            @PathVariable Long employeeId,
            @RequestParam int points) {
        try {
            VoucherDTO voucher = rewardService.convertPointsToVoucher(employeeId, points);
            return ResponseEntity.ok(voucher);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        catch (ExecutionException ec) {
            return ResponseEntity.internalServerError().body(ec.getMessage());
        }
    }
}
