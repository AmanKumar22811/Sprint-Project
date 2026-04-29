package com.classicmodel.repository;

import com.classicmodel.entity.Product;
import com.classicmodel.projection.ProductProjection;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.math.BigDecimal;
import java.util.List;

@RepositoryRestResource(path = "products", excerptProjection = ProductProjection.class)
public interface ProductRepository extends
        PagingAndSortingRepository<Product, String>,
        CrudRepository<Product, String> {

    // Traverses Product.productLineEntity.productLine — correct field name is productLineEntity
    List<Product> findByProductLineEntity_ProductLine(String productLine);

    List<Product> findByProductVendor(String vendor);

    List<Product> findByProductVendorContaining(String vendor);

    List<Product> findByProductVendorIgnoreCase(String vendor);

    List<Product> findByProductScale(String scale);

    List<Product> findByProductNameContaining(String name);

    List<Product> findByQuantityInStockLessThan(Short qty);

    List<Product> findByQuantityInStockGreaterThanEqual(Short qty);

    List<Product> findByBuyPriceLessThan(BigDecimal price);

    List<Product> findByBuyPriceGreaterThan(BigDecimal price);

    long count();
}
