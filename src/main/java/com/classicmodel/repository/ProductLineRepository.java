package com.classicmodel.repository;

import com.classicmodel.entity.ProductLine;
import com.classicmodel.projection.ProductLineProjection;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource(path = "productlines", excerptProjection = ProductLineProjection.class)
public interface ProductLineRepository extends
        PagingAndSortingRepository<ProductLine, String>,
        CrudRepository<ProductLine, String> {

    Optional<ProductLine> findByProductLine(String productLine);

    long count();
}
