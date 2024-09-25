package com.doan.employeeservice.services;

import com.doan.employeeservice.models.Employee;
import com.doan.employeeservice.models.RequestOfEmployee;
import com.doan.employeeservice.repositories.EmployeeRepository;
import com.doan.employeeservice.repositories.RequestOfEmployeeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class RequestOfEmployeeService {

    private final RequestOfEmployeeRepository requestOfEmployeeRepository;
    private final EmployeeRepository employeeRepository;



    // Format the current date and time as a String
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    String formattedDate = LocalDateTime.now().format(formatter);

    public RequestOfEmployeeService(RequestOfEmployeeRepository requestOfEmployeeRepository, EmployeeRepository employeeRepository) {
        this.requestOfEmployeeRepository = requestOfEmployeeRepository;
        this.employeeRepository = employeeRepository;
    }


    public List<RequestOfEmployee> getAllEmployees() {
        return requestOfEmployeeRepository.findAll();
    }

    public void requestLeave(Long employeeId, RequestOfEmployee request) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        request.setEmployee(employee);
        request.setRequestType(String.valueOf(RequestType.LEAVE));
        request.setStatus(String.valueOf(RequestStatus.PENDING));
        request.setRequestDate(formattedDate);
        requestOfEmployeeRepository.save(request);
    }

    public void updateTimesheet(Long employeeId, RequestOfEmployee request) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        request.setEmployee(employee);
        request.setRequestType(String.valueOf(RequestType.TIMESHEET_UPDATE));
        request.setStatus(String.valueOf(RequestStatus.PENDING));
        request.setRequestDate(formattedDate);
        requestOfEmployeeRepository.save(request);
    }

    public void checkIn(Long employeeId, RequestOfEmployee request) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        request.setEmployee(employee);
        request.setRequestType(String.valueOf(RequestType.CHECK_IN));
        request.setStatus(String.valueOf(RequestStatus.PENDING));
        request.setRequestDate(formattedDate);
        requestOfEmployeeRepository.save(request);
    }

    public void checkOut(Long employeeId, RequestOfEmployee request) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        request.setEmployee(employee);
        request.setRequestType(String.valueOf(RequestType.CHECK_OUT));
        request.setStatus(String.valueOf(RequestStatus.PENDING));
        request.setRequestDate(formattedDate);
        requestOfEmployeeRepository.save(request);
    }

    public void requestWFH(Long employeeId, RequestOfEmployee request) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        request.setEmployee(employee);
        request.setRequestType(String.valueOf(RequestType.WORK_FROM_HOME));
        request.setStatus(String.valueOf(RequestStatus.PENDING));
        request.setRequestDate(formattedDate);
        requestOfEmployeeRepository.save(request);
    }

    public boolean approveRequest(Long managerId, Long requestId) {
        Employee manager = employeeRepository.findById(managerId)
                .orElseThrow(() -> new RuntimeException("Manager not found"));
        if (manager.getPosition().equalsIgnoreCase("manager")) {
            RequestOfEmployee request = requestOfEmployeeRepository.findById(requestId)
                    .orElseThrow(() -> new RuntimeException("Request not found"));
            request.setStatus(String.valueOf(RequestStatus.APPROVED));
            request.setRequestDate(formattedDate);
            requestOfEmployeeRepository.save(request);

            return true;
        }

        return false;

    }

    public boolean rejectRequest(Long managerId, Long requestId) {
        Employee manager = employeeRepository.findById(managerId)
                .orElseThrow(() -> new RuntimeException("Manager not found"));
        if (manager.getPosition().equalsIgnoreCase("manager")) {
            RequestOfEmployee request = requestOfEmployeeRepository.findById(requestId)
                    .orElseThrow(() -> new RuntimeException("Request not found"));
            request.setStatus(String.valueOf(RequestStatus.REJECTED));
            request.setRequestDate(formattedDate);
            requestOfEmployeeRepository.save(request);

            return true;
        }

        return false;
    }

    // Method to get the total number of employees
    public int getEmployeeRequestCount() {
        return (int) requestOfEmployeeRepository.count(); // Assuming count() returns the total number of employees
    }

    public int countUnprocessedRequests() {
        return (int) requestOfEmployeeRepository.countByStatus("PENDING");
    }

    public int countProcessedRequests() {
        return (int) requestOfEmployeeRepository.countByStatusNotLike("PENDING");
    }


    public Map<String, Object> getMonthlyRequests() {
        List<String> labels = new ArrayList<>();
        List<Integer> data = new ArrayList<>();

        // Lấy dữ liệu cho 6 tháng gần nhất
        LocalDate now = LocalDate.now();
        DateTimeFormatter monthYearFormatter = DateTimeFormatter.ofPattern("MM/yyyy");
        DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern("MM");
        DateTimeFormatter yearFormatter = DateTimeFormatter.ofPattern("yyyy");

        for (int i = 5; i >= 0; i--) {
            LocalDate date = now.minusMonths(i);

            String label = "Tháng " + date.format(monthYearFormatter);
            labels.add(label);

            String monthStr = date.format(monthFormatter);
            String yearStr = date.format(yearFormatter);

            // Sử dụng phương thức đã điều chỉnh trong repository
            int count = requestOfEmployeeRepository.getRequestCountForMonth(monthStr, yearStr);
            data.add(count);
        }

        Map<String, Object> result = new HashMap<>();
        result.put("labels", labels);
        result.put("data", data);

        return result;
    }

}

