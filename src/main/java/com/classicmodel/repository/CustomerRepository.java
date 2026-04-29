package com.classicmodel.repository;

import com.classicmodel.entity.Customer;
import com.classicmodel.projection.CustomerProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RepositoryRestResource(path = "customers", excerptProjection = CustomerProjection.class)
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    Optional<Customer> findByCustomerName(String customerName);

    List<Customer> findByCustomerNameContaining(String name);

    List<Customer> findByCountry(String country);

    List<Customer> findByCountryIgnoreCase(String country);

    List<Customer> findByCity(String city);

    List<Customer> findByCityIgnoreCase(String city);

    List<Customer> findBySalesRepEmployee_EmployeeNumber(Integer empNo);

    List<Customer> findBySalesRepEmployee_Office_OfficeCode(String code);

    List<Customer> findByCreditLimitBetween(BigDecimal min, BigDecimal max);

    List<Customer> findByCreditLimitGreaterThan(BigDecimal limit);

    List<Customer> findByCreditLimitLessThan(BigDecimal limit);

    long count();
}