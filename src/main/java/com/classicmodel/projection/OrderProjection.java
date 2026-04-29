package com.classicmodel.projection;

import com.classicmodel.entity.Order;
import org.springframework.data.rest.core.config.Projection;
import java.time.LocalDate;

@Projection(name = "orderExcerpt", types = {Order.class})
public interface OrderProjection {
    Integer getOrderNumber();
    LocalDate getOrderDate();
    LocalDate getRequiredDate();
    LocalDate getShippedDate();
    String getStatus();
    String getComments();
    CustomerProjection getCustomer();
}
