package com.classicmodel.event;

import com.classicmodel.entity.Customer;
import com.classicmodel.exception.BadRequestException;
import com.classicmodel.exception.EmployeeNotFoundException;
import com.classicmodel.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RepositoryEventHandler
public class CustomerEventHandler {

    @Autowired private EmployeeRepository employeeRepository;

    @HandleBeforeCreate
    @HandleBeforeSave
    public void handleBeforeSave(Customer customer) {
        if (customer.getCustomerName() == null || customer.getCustomerName().isBlank()) {
            throw new BadRequestException("Customer name is required.");
        }
        if (customer.getPhone() == null || customer.getPhone().isBlank()) {
            throw new BadRequestException("Customer phone is required.");
        }
        if (customer.getCreditLimit() != null && customer.getCreditLimit().compareTo(BigDecimal.ZERO) < 0) {
            throw new BadRequestException("Credit limit cannot be negative.");
        }
        if (customer.getSalesRepEmployee() != null && customer.getSalesRepEmployee().getEmployeeNumber() != null) {
            employeeRepository.findById(customer.getSalesRepEmployee().getEmployeeNumber())
                .orElseThrow(() -> new EmployeeNotFoundException(customer.getSalesRepEmployee().getEmployeeNumber()));
        }
    }
}
