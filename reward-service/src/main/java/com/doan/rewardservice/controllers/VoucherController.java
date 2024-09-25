package com.doan.rewardservice.controllers;

import com.doan.rewardservice.dto.VoucherDTO;
import com.doan.rewardservice.services.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/vouchers")
public class VoucherController {

    @Autowired
    private VoucherService voucherService;

    @GetMapping("/{employeeId}")
    public ResponseEntity<List<VoucherDTO>> getVouchersByEmployeeId(@PathVariable Long employeeId) {
        List<VoucherDTO> vouchers = voucherService.getVouchersByEmployeeId(employeeId);
        return ResponseEntity.ok(vouchers);
    }
}
