package com.classicmodel.exception;

public class EmployeeNotFoundException extends EntityNotFoundException {
    public EmployeeNotFoundException(Integer employeeNumber) {
        super("Employee not found with employeeNumber: " + employeeNumber);
    }
}
