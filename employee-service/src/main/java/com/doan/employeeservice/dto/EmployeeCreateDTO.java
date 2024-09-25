package com.doan.employeeservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeCreateDTO {
    private String fullName;
    private String position;
    private String taxCode;
    private String address;
    private String phone;
    private String bankAccount;
    private String email;
    private String dateOfBirth;
    private String startDate;
    private double salary;
}
