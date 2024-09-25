package com.doan.employeeservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeRequestCreateDTO {
    private int employeeId; // Foreign key reference to Employee
    private String requestType;
    private String requestDate;
    private String pasword;
    private String status;
}
