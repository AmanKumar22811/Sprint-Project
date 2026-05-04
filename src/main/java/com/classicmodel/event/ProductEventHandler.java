package com.classicmodel.event;

import com.classicmodel.entity.Product;
import com.classicmodel.exception.BadRequestException;
import com.classicmodel.exception.ProductLineNotFoundException;
import com.classicmodel.repository.ProductLineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

@Component
@RepositoryEventHandler
public class ProductEventHandler {

    @Autowired private ProductLineRepository productLineRepository;

    @HandleBeforeCreate
    @HandleBeforeSave
    public void handleBeforeSave(Product product) {
        if (product.getProductName() == null || product.getProductName().isBlank()) {
            throw new BadRequestException("Product name is required.");
        }
        if (product.getProductCode() == null || product.getProductCode().isBlank()) {
            throw new BadRequestException("Product code is required.");
        }
        if (product.getQuantityInStock() != null && product.getQuantityInStock() < 0) {
            throw new BadRequestException("Quantity in stock cannot be negative.");
        }
        if (product.getProductLineEntity() != null && product.getProductLineEntity().getProductLine() != null) {
            productLineRepository.findById(product.getProductLineEntity().getProductLine())
                .orElseThrow(() -> new ProductLineNotFoundException(product.getProductLineEntity().getProductLine()));
        }
    }
}
