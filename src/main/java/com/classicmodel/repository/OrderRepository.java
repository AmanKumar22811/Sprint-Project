package com.classicmodel.repository;

import com.classicmodel.entity.Order;
import com.classicmodel.projection.OrderProjection;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.time.LocalDate;
import java.util.List;

@RepositoryRestResource(path = "orders", excerptProjection = OrderProjection.class)
public interface OrderRepository extends
        PagingAndSortingRepository<Order, Integer>,
        CrudRepository<Order, Integer> {

    List<Order> findByStatus(String status);

    List<Order> findByCustomer_CustomerNumber(Integer customerNumber);

    List<Order> findByOrderDate(LocalDate orderDate);

    List<Order> findByShippedDate(LocalDate shippedDate);

    List<Order> findByRequiredDate(LocalDate requiredDate);

    List<Order> findByCustomer_CustomerNumberAndStatus(Integer custNo, String status);

    long count();
}
