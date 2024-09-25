package com.doan.employeeservice.controllers;

import com.doan.employeeservice.dto.EmployeeDTO;
import com.doan.employeeservice.models.Employee;
import com.doan.employeeservice.models.RequestOfEmployee;
import com.doan.employeeservice.services.RequestOfEmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/employees")
public class RequestOfEmployeeController {

    private final RequestOfEmployeeService requestOfEmployeeService;

    public RequestOfEmployeeController(RequestOfEmployeeService requestOfEmployeeService) {
        this.requestOfEmployeeService = requestOfEmployeeService;
    }

    @GetMapping("/monthly-requests")
    public ResponseEntity<Map<String, Object>> getMonthlyRequests() {
        Map<String, Object> monthlyData = requestOfEmployeeService.getMonthlyRequests();
        if (monthlyData != null && !monthlyData.isEmpty()) {
            return ResponseEntity.ok(monthlyData);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/all-request")
    public ResponseEntity<List<RequestOfEmployee>> getAllEmployees() {
        List<RequestOfEmployee> employees = requestOfEmployeeService.getAllEmployees();

        return ResponseEntity.ok(employees);
    }

    @PostMapping("/{employeeId}/leave-request")
    public ResponseEntity<String> requestLeave(@PathVariable Long employeeId, @RequestBody RequestOfEmployee request) {
        requestOfEmployeeService.requestLeave(employeeId, request);
        return ResponseEntity.ok("Leave request submitted successfully.");
    }

    @PostMapping("/{employeeId}/update-timesheet")
    public ResponseEntity<String> updateTimesheet(@PathVariable Long employeeId, @RequestBody RequestOfEmployee request) {
        requestOfEmployeeService.updateTimesheet(employeeId, request);
        return ResponseEntity.ok("Timesheet updated successfully.");
    }

    @PostMapping("/{employeeId}/check-in")
    public ResponseEntity<String> checkIn(@PathVariable Long employeeId, @RequestBody RequestOfEmployee request) {
        requestOfEmployeeService.checkIn(employeeId, request);
        return ResponseEntity.ok("Check-in successful.");
    }

    @PostMapping("/{employeeId}/check-out")
    public ResponseEntity<String> checkOut(@PathVariable Long employeeId, @RequestBody RequestOfEmployee request) {
        requestOfEmployeeService.checkOut(employeeId, request);
        return ResponseEntity.ok("Check-out successful.");
    }

    @PostMapping("/{employeeId}/wfh-request")
    public ResponseEntity<String> requestWFH(@PathVariable Long employeeId, @RequestBody RequestOfEmployee request) {
        requestOfEmployeeService.requestWFH(employeeId, request);
        return ResponseEntity.ok("WFH request submitted successfully.");
    }

    @PostMapping("/{employeeId}/approve-request/{requestId}")
    public ResponseEntity<String> approveRequest(@PathVariable Long employeeId, @PathVariable Long requestId) {
        boolean result = requestOfEmployeeService.approveRequest(employeeId, requestId);
        return result ? ResponseEntity.ok("Request approved successfully.") : ResponseEntity.badRequest().body("Can't approve request.");
    }

    @PostMapping("/{employeeId}/reject-request/{requestId}")
    public ResponseEntity<String> rejectRequest(@PathVariable Long employeeId, @PathVariable Long requestId) {
        boolean result = requestOfEmployeeService.rejectRequest(employeeId, requestId);
        return result ? ResponseEntity.ok("Request rejected successfully.") : ResponseEntity.badRequest().body("Can't reject request.");
    }

    @GetMapping("/count-request")
    public ResponseEntity<Map<String, Integer>> getEmployeeCount() {
        int totalReqs = requestOfEmployeeService.getEmployeeRequestCount();
        int totalPending = requestOfEmployeeService.countUnprocessedRequests();
        int totalProcessedReq = requestOfEmployeeService.countProcessedRequests();
        // Add other necessary data like totalRevenue, etc.
        Map<String, Integer> response = new HashMap<>();
        response.put("totalRequests", totalReqs);
        response.put("totalPendingRequests", totalPending);
        response.put("totalProcessedRequests", totalProcessedReq);
        // Add other keys as needed

        return ResponseEntity.ok(response);
    }

//    @GetMapping("/count-request-pending")
//    public ResponseEntity<Map<String, Integer>> getPendingRequestCount() {
//        int totalReq = requestOfEmployeeService.getEmployeeRequestCount();
//        // Add other necessary data like totalRevenue, etc.
//        Map<String, Integer> response = new HashMap<>();
//        response.put("totalRequests", totalReq);
//        response.put("totalPendingRequests", totalReq);
//        // Add other keys as needed
//
//        return ResponseEntity.ok(response);
//    }

}
