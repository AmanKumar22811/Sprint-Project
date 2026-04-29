package com.classicmodel.repository;

import com.classicmodel.entity.OrderDetail;
import com.classicmodel.entity.OrderDetailId;
import com.classicmodel.projection.OrderDetailProjection;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
 * COMPOSITE KEY RULE:
 * - orderNumber is in @EmbeddedId BUT reachable via @ManyToOne Order — use: findByOrder_OrderNumber()
 * - productCode is in @EmbeddedId BUT reachable via @ManyToOne Product — use: findByProduct_ProductCode()
 * - NEVER write findByOrderNumber() or findByProductCode() — causes startup crash.
 */
@RepositoryRestResource(path = "orderdetails", excerptProjection = OrderDetailProjection.class)
public interface OrderDetailRepository extends
        PagingAndSortingRepository<OrderDetail, OrderDetailId>,
        CrudRepository<OrderDetail, OrderDetailId> {

    // Traverse via @ManyToOne Order — resolves orderNumber through Order entity
    List<OrderDetail> findByOrder_OrderNumber(Integer orderNumber);

    // Traverse via @ManyToOne Product — resolves productCode through Product entity
    List<OrderDetail> findByProduct_ProductCode(String productCode);

    // Traverse Order relationship then Order.status
    List<OrderDetail> findByOrder_Status(String status);

    long count();
}
