package com.classicmodel.repository;

import com.classicmodel.entity.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepo;

    @Test
    void testGetOrderById_whenExists_returnsOrder() {
        Optional<Order> result = orderRepo.findById(10100);
        assertTrue(result.isPresent());
        assertEquals(10100, result.get().getOrderNumber());
    }

    @Test
    void testGetOrderById_whenNotExists_returnsEmpty() {
        Optional<Order> result = orderRepo.findById(9999999);
        assertFalse(result.isPresent());
    }

    @Test
    void testGetOrdersByStatus_returnsShippedOrders() {
        List<Order> result = orderRepo.findByStatus("Shipped");
        assertFalse(result.isEmpty());
        assertTrue(result.stream().allMatch(o -> "Shipped".equals(o.getStatus())));
    }

    @Test
    void testGetOrdersByCustomerNumber_returnsResults() {
        List<Order> result = orderRepo.findByCustomer_CustomerNumber(103);
        assertFalse(result.isEmpty());
        assertTrue(result.stream().allMatch(o -> 103 == o.getCustomer().getCustomerNumber()));
    }

    @Test
    void testGetOrdersByCustomerAndStatus_returnsResults() {
        List<Order> result = orderRepo.findByCustomer_CustomerNumberAndStatus(103, "Shipped");
        assertNotNull(result);
    }

    @Test
    void testGetAllOrders_returnsNonEmpty() {
        Iterable<Order> all = orderRepo.findAll();
        assertNotNull(all);
        assertTrue(all.iterator().hasNext());
    }

    @Test
    void testCountOrders_greaterThanZero() {
        long count = orderRepo.count();
        assertTrue(count > 0);
    }

    @Test
    void testOrderHasCustomer_customerIsNotNull() {
        Optional<Order> result = orderRepo.findById(10100);
        assertTrue(result.isPresent());
        assertNotNull(result.get().getCustomer());
    }
}
