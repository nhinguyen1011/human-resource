package com.doan.employeeservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO {
    private int employeeId;
    private String fullName;
    private String position;
    private String taxCode;
    private String address;
    private String phone;
    private String bankAccount;
    private String email;
    private String pasword;
    private String dateOfBirth;
    private String startDate;
    private double salary;
    private String status;
}