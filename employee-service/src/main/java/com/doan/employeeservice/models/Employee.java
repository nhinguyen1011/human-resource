package com.doan.employeeservice.models;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@Entity
@Table(name = "employees")
@NoArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int employeeId;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "position")
    private String position;

    @Column(name = "tax_code")
    private String taxCode;

    @Column(name = "address")
    private String address;

    @Column(name = "phone")
    private String phone;

    @Column(name = "bank_account")
    private String bankAccount;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "date_of_birth")
    private String dateOfBirth;

    @Column(name = "start_date")
    private String startDate;

    @Column(name = "salary")
    private double salary;

    @Column(name = "status")
    private String status;

    @Column(name = "strava_access_token")
    private String accessToken;
}
