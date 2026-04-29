package com.classicmodel.repository;

import com.classicmodel.entity.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepo;

    @Autowired
    private OfficeRepository officeRepo;

    @Test
    void testGetEmployeeById_whenExists_returnsEmployee() {
        Optional<Employee> result = employeeRepo.findById(1002);
        assertTrue(result.isPresent());
        assertEquals(1002, result.get().getEmployeeNumber());
        assertEquals("Murphy", result.get().getLastName());
    }

    @Test
    void testGetEmployeeById_whenNotExists_returnsEmpty() {
        Optional<Employee> result = employeeRepo.findById(99999);
        assertFalse(result.isPresent());
    }

    @Test
    void testGetEmployeesByLastName_returnsResults() {
        List<Employee> result = employeeRepo.findByLastName("Murphy");
        assertFalse(result.isEmpty());
        assertTrue(result.stream().allMatch(e -> "Murphy".equals(e.getLastName())));
    }

    @Test
    void testGetEmployeesByJobTitle_returnsResults() {
        List<Employee> result = employeeRepo.findByJobTitle("Sales Rep");
        assertFalse(result.isEmpty());
        assertTrue(result.stream().allMatch(e -> "Sales Rep".equals(e.getJobTitle())));
    }

    @Test
    void testGetEmployeesByOfficeCode_returnsResults() {
        List<Employee> result = employeeRepo.findByOffice_OfficeCode("1");
        assertFalse(result.isEmpty());
        assertTrue(result.stream().allMatch(e -> "1".equals(e.getOffice().getOfficeCode())));
    }

    @Test
    void testGetEmployeesByOfficeCity_returnsResults() {
        List<Employee> result = employeeRepo.findByOffice_City("San Francisco");
        assertFalse(result.isEmpty());
    }

    @Test
    void testGetEmployeeByEmail_returnsEmployee() {
        Optional<Employee> result = employeeRepo.findByEmail("dmurphy@classicmodelcars.com");
        assertTrue(result.isPresent());
    }

    @Test
    void testGetEmployeesByManagerNumber_returnsResults() {
        List<Employee> result = employeeRepo.findByManager_EmployeeNumber(1002);
        assertNotNull(result);
    }

    @Test
    void testGetAllEmployees_returnsNonEmpty() {
        Iterable<Employee> all = employeeRepo.findAll();
        assertNotNull(all);
        assertTrue(all.iterator().hasNext());
    }

    @Test
    void testCountEmployees_greaterThanZero() {
        long count = employeeRepo.count();
        assertTrue(count > 0);
    }

}
