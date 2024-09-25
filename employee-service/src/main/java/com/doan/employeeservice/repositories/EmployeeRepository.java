package com.doan.employeeservice.repositories;
import com.doan.employeeservice.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    // Custom query methods can be added here if needed
    public Employee findByEmailAndPassword(String email, String password);
}
