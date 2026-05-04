package com.classicmodel.event;

import com.classicmodel.entity.Employee;
import com.classicmodel.exception.BadRequestException;
import com.classicmodel.exception.EmployeeNotFoundException;
import com.classicmodel.exception.OfficeNotFoundException;
import com.classicmodel.repository.EmployeeRepository;
import com.classicmodel.repository.OfficeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

@Component
@RepositoryEventHandler
public class EmployeeEventHandler {

    @Autowired private OfficeRepository officeRepository;
    @Autowired private EmployeeRepository employeeRepository;

    @HandleBeforeCreate
    @HandleBeforeSave
    public void handleBeforeSave(Employee employee) {
        if (employee.getOffice() == null || employee.getOffice().getOfficeCode() == null) {
            throw new BadRequestException("Employee must be assigned to a valid office.");
        }
        officeRepository.findById(employee.getOffice().getOfficeCode())
            .orElseThrow(() -> new OfficeNotFoundException(employee.getOffice().getOfficeCode()));

        if (employee.getEmail() == null || !employee.getEmail().contains("@")) {
            throw new BadRequestException("Employee email must be a valid email address.");
        }

        if (employee.getExtension() != null && !employee.getExtension().startsWith("x")) {
            throw new BadRequestException("Employee extension must start with 'x' (e.g. x1234).");
        }

        if (employee.getManager() != null && employee.getManager().getEmployeeNumber() != null) {
            employeeRepository.findById(employee.getManager().getEmployeeNumber())
                .orElseThrow(() -> new EmployeeNotFoundException(employee.getManager().getEmployeeNumber()));
        }
    }
}
