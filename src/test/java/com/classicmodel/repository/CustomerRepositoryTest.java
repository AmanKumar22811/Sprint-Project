package com.classicmodel.repository;

import com.classicmodel.entity.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepo;

    @Test
    void testGetCustomerById_whenExists_returnsCustomer() {
        Optional<Customer> result = customerRepo.findById(103);
        assertTrue(result.isPresent());
        assertEquals(103, result.get().getCustomerNumber());
    }

    @Test
    void testGetCustomerById_whenNotExists_returnsEmpty() {
        Optional<Customer> result = customerRepo.findById(9999999);
        assertFalse(result.isPresent());
    }

    @Test
    void testGetCustomerByName_returnsCustomer() {
        Optional<Customer> result = customerRepo.findByCustomerName("Atelier graphique");
        assertTrue(result.isPresent());
        assertEquals("Atelier graphique", result.get().getCustomerName());
    }

    @Test
    void testGetCustomersByCountry_returnsResults() {
        List<Customer> result = customerRepo.findByCountry("France");
        assertFalse(result.isEmpty());
        assertTrue(result.stream().allMatch(c -> "France".equals(c.getCountry())));
    }

    @Test
    void testGetCustomersByCity_returnsResults() {
        List<Customer> result = customerRepo.findByCity("Paris");
        assertFalse(result.isEmpty());
    }

    @Test
    void testGetCustomersBySalesRep_returnsResults() {
        List<Customer> result = customerRepo.findBySalesRepEmployee_EmployeeNumber(1370);
        assertNotNull(result);
    }

    @Test
    void testGetCustomersByNameContaining_returnsResults() {
        List<Customer> result = customerRepo.findByCustomerNameContaining("Auto");
        assertNotNull(result);
    }

    @Test
    void testGetCustomersByCreditLimitGreaterThan_returnsResults() {
        List<Customer> result = customerRepo.findByCreditLimitGreaterThan(BigDecimal.valueOf(50000));
        assertFalse(result.isEmpty());
        assertTrue(result.stream().allMatch(c -> c.getCreditLimit().compareTo(BigDecimal.valueOf(50000)) > 0));
    }

    @Test
    void testGetAllCustomers_returnsNonEmpty() {
        Iterable<Customer> all = customerRepo.findAll();
        assertNotNull(all);
        assertTrue(all.iterator().hasNext());
    }

    @Test
    void testCountCustomers_greaterThanZero() {
        long count = customerRepo.count();
        assertTrue(count > 0);
    }
}
