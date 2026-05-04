package com.classicmodel.exception;

public class OrderDetailNotFoundException extends EntityNotFoundException {
    public OrderDetailNotFoundException(Integer orderNumber, String productCode) {
        super("OrderDetail not found for orderNumber: " + orderNumber + ", productCode: " + productCode);
    }
}
