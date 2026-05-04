package com.classicmodel.exception;

public class OrderNotFoundException extends EntityNotFoundException {
    public OrderNotFoundException(Integer orderNumber) {
        super("Order not found with orderNumber: " + orderNumber);
    }
}
