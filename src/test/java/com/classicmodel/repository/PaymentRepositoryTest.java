package com.classicmodel.repository;

import com.classicmodel.entity.Payment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PaymentRepositoryTest {

    @Autowired
    private PaymentRepository paymentRepo;

    
    @Test
    void testGetPaymentsByCustomerNumber_returnsResults() {
        List<Payment> result = paymentRepo.findByCustomer_CustomerNumber(103);
        assertFalse(result.isEmpty());
        assertTrue(result.stream().allMatch(p -> 103 == p.getCustomer().getCustomerNumber()));
    }

    @Test
    void testGetPaymentsByCheckNumber_returnsResults() {
        List<Payment> result = paymentRepo.findById_CheckNumber("HQ336336");
        assertFalse(result.isEmpty());
        assertTrue(result.stream().allMatch(p -> "HQ336336".equals(p.getId().getCheckNumber())));
    }

    @Test
    void testGetPaymentsByDate_returnsListNotNull() {
       
        List<Payment> result = paymentRepo.findByPaymentDate(LocalDate.of(2004, 10, 19));
        assertNotNull(result);
        
    }

    @Test
    void testGetAllPayments_returnsNonEmpty() {
        Iterable<Payment> all = paymentRepo.findAll();
        assertNotNull(all);
        assertTrue(all.iterator().hasNext());
    }

    @Test
    void testCountPayments_greaterThanZero() {
        long count = paymentRepo.count();
        assertTrue(count > 0);
    }
}
