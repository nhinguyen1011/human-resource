package com.doan.employeeservice.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "employee_requests")
@NoArgsConstructor
public class RequestOfEmployee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int requestId;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Column(name = "request_type")
    private String requestType;

    @Column(name = "request_date")
    private String requestDate;

    @Column(name = "status")
    private String status;


}
