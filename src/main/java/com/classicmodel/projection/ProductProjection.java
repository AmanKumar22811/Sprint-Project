package com.classicmodel.projection;

import com.classicmodel.entity.Product;
import org.springframework.data.rest.core.config.Projection;
import java.math.BigDecimal;

@Projection(name = "productExcerpt", types = {Product.class})
public interface ProductProjection {
    String getProductCode();
    String getProductName();
    String getProductScale();
    String getProductVendor();
    String getProductDescription();
    Short getQuantityInStock();
    BigDecimal getBuyPrice();
    BigDecimal getMSRP();
    ProductLineProjection getProductLineEntity();
}
