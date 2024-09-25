package com.doan.employeeservice.dto.mappers;

import com.doan.employeeservice.dto.EmployeeDTO;
import com.doan.employeeservice.models.Employee;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-09-04T14:14:42+0700",
    comments = "version: 1.6.0, compiler: javac, environment: Java 18 (Oracle Corporation)"
)
@Component
public class EmployeeMapperImpl implements EmployeeMapper {

    @Override
    public EmployeeDTO toEmployeeDTO(Employee employee) {
        if ( employee == null ) {
            return null;
        }

        EmployeeDTO employeeDTO = new EmployeeDTO();

        employeeDTO.setEmployeeId( employee.getEmployeeId() );
        employeeDTO.setFullName( employee.getFullName() );
        employeeDTO.setPosition( employee.getPosition() );
        employeeDTO.setTaxCode( employee.getTaxCode() );
        employeeDTO.setAddress( employee.getAddress() );
        employeeDTO.setPhone( employee.getPhone() );
        employeeDTO.setBankAccount( employee.getBankAccount() );
        employeeDTO.setEmail( employee.getEmail() );
        employeeDTO.setDateOfBirth( employee.getDateOfBirth() );
        employeeDTO.setStartDate( employee.getStartDate() );
        employeeDTO.setSalary( employee.getSalary() );
        employeeDTO.setStatus( employee.getStatus() );

        return employeeDTO;
    }

    @Override
    public Employee toEmployee(EmployeeDTO employeeDTO) {
        if ( employeeDTO == null ) {
            return null;
        }

        Employee employee = new Employee();

        employee.setEmployeeId( employeeDTO.getEmployeeId() );
        employee.setFullName( employeeDTO.getFullName() );
        employee.setPosition( employeeDTO.getPosition() );
        employee.setTaxCode( employeeDTO.getTaxCode() );
        employee.setAddress( employeeDTO.getAddress() );
        employee.setPhone( employeeDTO.getPhone() );
        employee.setBankAccount( employeeDTO.getBankAccount() );
        employee.setEmail( employeeDTO.getEmail() );
        employee.setDateOfBirth( employeeDTO.getDateOfBirth() );
        employee.setStartDate( employeeDTO.getStartDate() );
        employee.setSalary( employeeDTO.getSalary() );
        employee.setStatus( employeeDTO.getStatus() );

        return employee;
    }
}
