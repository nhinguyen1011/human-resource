package com.doan.employeeservice.controllers;

import com.doan.employeeservice.dto.EmployeeDTO;
import com.doan.employeeservice.dto.SignInDTO;
import com.doan.employeeservice.dto.mappers.EmployeeMapper;
import com.doan.employeeservice.models.Employee;
import com.doan.employeeservice.services.EmployeeService;
import com.doan.employeeservice.services.RequestOfEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final EmployeeMapper employeeMapper;

    @Autowired
    public EmployeeController(EmployeeService employeeService, EmployeeMapper employeeMapper, RequestOfEmployeeService requestOfEmployeeService) {
        this.employeeService = employeeService;
        this.employeeMapper = employeeMapper;
    }

    @GetMapping("/all")
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();
        List<EmployeeDTO> employeeDTOs = employees.stream()
                .map(employeeMapper::toEmployeeDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(employeeDTOs);
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<EmployeeDTO> getEmployeeProfile(@PathVariable Long employeeId) {
        Optional<Employee> employee = employeeService.getEmployeeById(employeeId);
        return employee.map(emp -> ResponseEntity.ok(employeeMapper.toEmployeeDTO(emp)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/sign-in")
    public ResponseEntity<EmployeeDTO> SignIn(@RequestBody SignInDTO signInData) {
        Employee employee = employeeService.signIn(signInData.email, signInData.password);
        if (employee != null) {
            EmployeeDTO empDTO = employeeMapper.toEmployeeDTO(employee);

            return ResponseEntity.status(HttpStatus.OK).body(empDTO);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

    }

    @GetMapping("/count")
    public ResponseEntity<Map<String, Integer>> getEmployeeCount() {
        int totalEmployees = employeeService.getEmployeeCount();
        // Add other necessary data like totalRevenue, etc.
        Map<String, Integer> response = new HashMap<>();
        response.put("totalEmployees", totalEmployees);
        // Add other keys as needed

        return ResponseEntity.ok(response);
    }


    @PostMapping("/create")
    public ResponseEntity<EmployeeDTO> createEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee employee = employeeMapper.toEmployee(employeeDTO);
        Employee createdEmployee = employeeService.saveOrUpdateEmployee(employee);
        EmployeeDTO createdEmployeeDTO = employeeMapper.toEmployeeDTO(createdEmployee);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEmployeeDTO);
    }

    @PutMapping("/update")
    public ResponseEntity<EmployeeDTO> updateEmployeeProfile(@RequestBody EmployeeDTO employeeDTO) {
        Employee employee = employeeMapper.toEmployee(employeeDTO);
        Employee updatedEmployee = employeeService.saveOrUpdateEmployee(employee);
        EmployeeDTO updatedEmployeeDTO = employeeMapper.toEmployeeDTO(updatedEmployee);
        return ResponseEntity.ok(updatedEmployeeDTO);
    }

    @DeleteMapping("/{employeeId}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable int employeeId) {
        boolean isRemoved = employeeService.deleteEmployee((long) employeeId);
        if (!isRemoved) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
