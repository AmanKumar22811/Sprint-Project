package com.classicmodel.exception;

public class ProductLineNotFoundException extends EntityNotFoundException {
    public ProductLineNotFoundException(String productLine) {
        super("ProductLine not found with productLine: " + productLine);
    }
}
