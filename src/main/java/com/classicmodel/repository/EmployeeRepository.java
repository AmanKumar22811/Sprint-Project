package com.classicmodel.repository;

import com.classicmodel.entity.Employee;
import com.classicmodel.projection.EmployeeProjection;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(path = "employees", excerptProjection = EmployeeProjection.class)
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    List<Employee> findByLastName(String lastName);

    List<Employee> findByJobTitle(String jobTitle);

    List<Employee> findByOffice_OfficeCode(String officeCode);

    List<Employee> findByOffice_City(String city);

    Optional<Employee> findByEmail(String email);

    List<Employee> findByManager_EmployeeNumber(Integer managerNumber);
}