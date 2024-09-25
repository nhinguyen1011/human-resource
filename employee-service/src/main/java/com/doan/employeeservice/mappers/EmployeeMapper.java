package com.doan.employeeservice.dto.mappers;

import com.doan.employeeservice.dto.EmployeeDTO;
import com.doan.employeeservice.models.Employee;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    EmployeeDTO toEmployeeDTO(Employee employee);

    Employee toEmployee(EmployeeDTO employeeDTO);
}
