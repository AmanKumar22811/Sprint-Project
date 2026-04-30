package com.classicmodel.repository;

import com.classicmodel.entity.ProductLine;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductLineRepositoryTest {

    @Autowired
    private ProductLineRepository productLineRepo;

    @Test
    void testGetProductLineById_whenExists_returnsProductLine() {
        Optional<ProductLine> result = productLineRepo.findById("Classic Cars");
        assertTrue(result.isPresent());
        assertEquals("Classic Cars", result.get().getProductLine());
    }

    @Test
    void testGetProductLineById_whenNotExists_returnsEmpty() {
        Optional<ProductLine> result = productLineRepo.findById("Nonexistent Line XYZ");
        assertFalse(result.isPresent());
    }

    @Test
    void testGetProductLineByProductLine_returnsResult() {
        Optional<ProductLine> result = productLineRepo.findByProductLine("Classic Cars");
        assertTrue(result.isPresent());
    }

    @Test
    void testGetAllProductLines_returnsNonEmpty() {
        Iterable<ProductLine> all = productLineRepo.findAll();
        assertNotNull(all);
        assertTrue(all.iterator().hasNext());
    }

    @Test
    void testCountProductLines_greaterThanZero() {
        long count = productLineRepo.count();
        assertTrue(count > 0);
    }

    @Test
    void testAddProductLine_succeeds() {
        ProductLine pl = new ProductLine();
        pl.setProductLine("Test Line");
        pl.setTextDescription("Test description for test line");

        productLineRepo.save(pl);

        assertTrue(productLineRepo.findById("Test Line").isPresent());
    }

    @Test
    void testUpdateProductLine_succeeds() {
        Optional<ProductLine> opt = productLineRepo.findById("Classic Cars");
        assertTrue(opt.isPresent());
        ProductLine pl = opt.get();
        String original = pl.getTextDescription();
        pl.setTextDescription("Updated description");
        productLineRepo.save(pl);
        assertEquals("Updated description", productLineRepo.findById("Classic Cars").get().getTextDescription());
        pl.setTextDescription(original);
        productLineRepo.save(pl);
    }
}
