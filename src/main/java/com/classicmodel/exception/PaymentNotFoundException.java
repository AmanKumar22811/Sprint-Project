package com.classicmodel.exception;

public class PaymentNotFoundException extends EntityNotFoundException {
    public PaymentNotFoundException(Integer customerNumber, String checkNumber) {
        super("Payment not found for customerNumber: " + customerNumber + ", checkNumber: " + checkNumber);
    }
}
