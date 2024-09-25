package com.doan.rewardservice.services;

import com.doan.rewardservice.dto.VoucherDTO;
import com.doan.rewardservice.models.Voucher;
import com.doan.rewardservice.repositories.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VoucherService {

    @Autowired
    private VoucherRepository voucherRepository;



    public List<VoucherDTO> getVouchersByEmployeeId(Long employeeId) {
        List<Voucher> vouchers = voucherRepository.findByRewardEmployeeId(Math.toIntExact(employeeId));
        return vouchers.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
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
