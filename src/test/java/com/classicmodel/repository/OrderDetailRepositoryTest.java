package com.classicmodel.repository;

import com.classicmodel.entity.OrderDetail;
import com.classicmodel.entity.OrderDetailId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrderDetailRepositoryTest {

    @Autowired
    private OrderDetailRepository orderDetailRepo;

    @Test
    void testGetOrderDetailByCompositeKey_whenExists_returnsDetail() {
        OrderDetailId key = new OrderDetailId(10100, "S18_1749");
        Optional<OrderDetail> result = orderDetailRepo.findById(key);
        assertTrue(result.isPresent());
        assertEquals(10100, result.get().getId().getOrderNumber());
        assertEquals("S18_1749", result.get().getId().getProductCode());
    }

    @Test
    void testGetOrderDetailByCompositeKey_whenNotExists_returnsEmpty() {
        OrderDetailId key = new OrderDetailId(99999, "INVALID_CODE");
        Optional<OrderDetail> result = orderDetailRepo.findById(key);
        assertFalse(result.isPresent());
    }

    /**
     * CRITICAL: Must use findByOrder_OrderNumber() NOT findByOrderNumber()
     * because orderNumber is inside @EmbeddedId — bare field query crashes at startup.
     */
    @Test
    void testGetOrderDetailsByOrderNumber_returnsResults() {
        List<OrderDetail> result = orderDetailRepo.findByOrder_OrderNumber(10100);
        assertFalse(result.isEmpty());
        assertTrue(result.stream().allMatch(d -> 10100 == d.getOrder().getOrderNumber()));
    }

    /**
     * CRITICAL: Must use findByProduct_ProductCode() NOT findByProductCode()
     * because productCode is inside @EmbeddedId — bare field query crashes at startup.
     */
    @Test
    void testGetOrderDetailsByProductCode_returnsResults() {
        List<OrderDetail> result = orderDetailRepo.findByProduct_ProductCode("S18_1749");
        assertFalse(result.isEmpty());
        assertTrue(result.stream().allMatch(d -> "S18_1749".equals(d.getProduct().getProductCode())));
    }

    @Test
    void testGetOrderDetailsByOrderStatus_returnsResults() {
        List<OrderDetail> result = orderDetailRepo.findByOrder_Status("Shipped");
        assertFalse(result.isEmpty());
    }

    @Test
    void testGetAllOrderDetails_returnsNonEmpty() {
        Iterable<OrderDetail> all = orderDetailRepo.findAll();
        assertNotNull(all);
        assertTrue(all.iterator().hasNext());
    }

    @Test
    void testCountOrderDetails_greaterThanZero() {
        long count = orderDetailRepo.count();
        assertTrue(count > 0);
    }

    @Test
    void testOrderDetailHasOrder_orderIsNotNull() {
        OrderDetailId key = new OrderDetailId(10100, "S18_1749");
        Optional<OrderDetail> result = orderDetailRepo.findById(key);
        assertTrue(result.isPresent());
        assertNotNull(result.get().getOrder());
    }

    @Test
    void testOrderDetailHasProduct_productIsNotNull() {
        OrderDetailId key = new OrderDetailId(10100, "S18_1749");
        Optional<OrderDetail> result = orderDetailRepo.findById(key);
        assertTrue(result.isPresent());
        assertNotNull(result.get().getProduct());
    }
}
