package com.classicmodel.exception;

public class ProductNotFoundException extends EntityNotFoundException {
    public ProductNotFoundException(String productCode) {
        super("Product not found with productCode: " + productCode);
    }
}
