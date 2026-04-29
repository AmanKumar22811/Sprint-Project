package com.classicmodel.projection;

import com.classicmodel.entity.Employee;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "employeeExcerpt", types = {Employee.class})
public interface EmployeeProjection {
    Integer getEmployeeNumber();
    String getFirstName();
    String getLastName();
    String getJobTitle();
    String getEmail();
    String getExtension();
    OfficeProjection getOffice();
}
