package com.doan.employeeservice.services;

import com.doan.employeeservice.models.Employee;
import com.doan.employeeservice.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.*;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Optional<Employee> getEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }

    public Employee saveOrUpdateEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public boolean deleteEmployee(Long id) {
       try {

           Optional<Employee> employee = employeeRepository.findById(id);
           if (employee.isPresent()) {
               Employee emp = employee.get();
               employeeRepository.deleteById((long) emp.getEmployeeId());
               return true;
           }
           return false;

       }
       catch(Exception e) {
           System.out.println(e.getMessage());
           return false;
       }

    }

    public Employee signIn(String email, String password) {
        return employeeRepository.findByEmailAndPassword(email, password);
    }

    // Method to get the total number of employees
    public int getEmployeeCount() {
        return (int) employeeRepository.count(); // Assuming count() returns the total number of employees
    }




}
