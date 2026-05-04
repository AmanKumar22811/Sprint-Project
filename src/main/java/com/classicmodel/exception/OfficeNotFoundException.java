package com.classicmodel.exception;

public class OfficeNotFoundException extends EntityNotFoundException {
    public OfficeNotFoundException(String officeCode) {
        super("Office not found with officeCode: " + officeCode);
    }
}
