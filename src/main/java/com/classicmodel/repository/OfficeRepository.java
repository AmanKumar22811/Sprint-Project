package com.classicmodel.repository;

import com.classicmodel.entity.Office;
import com.classicmodel.projection.OfficeProjection;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(path = "offices", excerptProjection = OfficeProjection.class)
public interface OfficeRepository extends
        PagingAndSortingRepository<Office, String>,
        CrudRepository<Office, String> {

    List<Office> findByCity(String city);

    List<Office> findByCountry(String country);

    List<Office> findByCityIn(List<String> cities);

    List<Office> findByTerritory(String territory);

    long count();
}