package com.classicmodel.repository;

import com.classicmodel.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepo;

    @Test
    void testGetProductById_whenExists_returnsProduct() {
        Optional<Product> result = productRepo.findById("S18_1749");
        assertTrue(result.isPresent());
        assertEquals("S18_1749", result.get().getProductCode());
    }

    @Test
    void testGetProductById_whenNotExists_returnsEmpty() {
        Optional<Product> result = productRepo.findById("INVALID_PROD_CODE");
        assertFalse(result.isPresent());
    }

    @Test
    void testGetProductsByProductLine_returnsResults() {
        // Uses findByProductLineEntity_ProductLine — traverses Product.productLineEntity.productLine
        List<Product> result = productRepo.findByProductLineEntity_ProductLine("Classic Cars");
        assertFalse(result.isEmpty());
        assertTrue(result.stream().allMatch(p -> "Classic Cars".equals(p.getProductLineEntity().getProductLine())));
    }

    @Test
    void testGetProductsByVendor_returnsResults() {
        List<Product> result = productRepo.findByProductVendorIgnoreCase("Min Lin Diecast");
        assertFalse(result.isEmpty());
    }

    @Test
    void testGetProductsByScale_returnsResults() {
        List<Product> result = productRepo.findByProductScale("1:18");
        assertFalse(result.isEmpty());
        assertTrue(result.stream().allMatch(p -> "1:18".equals(p.getProductScale())));
    }

    @Test
    void testGetProductsByLowStock_returnsResults() {
        List<Product> result = productRepo.findByQuantityInStockLessThan((short) 500);
        assertNotNull(result);
    }

    @Test
    void testGetProductsByNameContaining_returnsResults() {
        List<Product> result = productRepo.findByProductNameContaining("1969");
        assertNotNull(result);
    }

    @Test
    void testGetProductsByBuyPriceGreaterThan_returnsResults() {
        List<Product> result = productRepo.findByBuyPriceGreaterThan(BigDecimal.valueOf(50));
        assertFalse(result.isEmpty());
    }

    @Test
    void testGetAllProducts_returnsNonEmpty() {
        Iterable<Product> all = productRepo.findAll();
        assertNotNull(all);
        assertTrue(all.iterator().hasNext());
    }

    @Test
    void testCountProducts_greaterThanZero() {
        long count = productRepo.count();
        assertTrue(count > 0);
    }

    @Test
    void testProductHasProductLine_notNull() {
        Optional<Product> result = productRepo.findById("S18_1749");
        assertTrue(result.isPresent());
        assertNotNull(result.get().getProductLineEntity());
    }
}
