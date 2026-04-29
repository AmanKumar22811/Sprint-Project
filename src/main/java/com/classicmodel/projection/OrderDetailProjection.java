package com.classicmodel.projection;

import com.classicmodel.entity.OrderDetail;
import com.classicmodel.entity.OrderDetailId;
import org.springframework.data.rest.core.config.Projection;
import java.math.BigDecimal;

@Projection(name = "orderDetailExcerpt", types = {OrderDetail.class})
public interface OrderDetailProjection {
    OrderDetailId getId();
    Integer getQuantityOrdered();
    BigDecimal getPriceEach();
    Integer getOrderLineNumber();
    OrderProjection getOrder();
    ProductProjection getProduct();
}
