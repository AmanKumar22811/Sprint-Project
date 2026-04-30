package com.classicmodel.projection;

import com.classicmodel.entity.Customer;
import org.springframework.data.rest.core.config.Projection;
import java.math.BigDecimal;

@Projection(name = "customerExcerpt", types = {Customer.class})
public interface CustomerProjection {
    Integer getCustomerNumber();
    String getCustomerName();
    String getContactLastName();
    String getContactFirstName();
    String getPhone();
    String getCity();
    String getCountry();
    BigDecimal getCreditLimit();
    EmployeeProjection getSalesRepEmployee();
}
