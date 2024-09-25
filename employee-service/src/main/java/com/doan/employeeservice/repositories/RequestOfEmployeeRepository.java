package com.doan.employeeservice.repositories;

import com.doan.employeeservice.models.RequestOfEmployee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RequestOfEmployeeRepository extends JpaRepository<RequestOfEmployee, Long> {
    Integer countByStatus(String status);
    Integer countByStatusNotLike(String status);
    // Custom query methods for specific requests can be added here if needed

    @Query("SELECT COUNT(r) FROM RequestOfEmployee r WHERE DATE_FORMAT(r.requestDate, '%m') = :month AND DATE_FORMAT(r.requestDate, '%Y') = :year")
    int getRequestCountForMonth(@Param("month") String month, @Param("year") String year);
}
