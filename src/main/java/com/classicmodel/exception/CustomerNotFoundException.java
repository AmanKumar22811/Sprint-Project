package com.classicmodel.exception;

public class CustomerNotFoundException extends EntityNotFoundException {
    public CustomerNotFoundException(Integer customerNumber) {
        super("Customer not found with customerNumber: " + customerNumber);
    }
}
