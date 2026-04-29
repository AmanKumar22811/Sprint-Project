package com.classicmodel.projection;

import com.classicmodel.entity.Office;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "officeExcerpt", types = {Office.class})
public interface OfficeProjection {
    String getOfficeCode();
    String getCity();
    String getPhone();
    String getAddressLine1();
    String getAddressLine2();
    String getState();
    String getCountry();
    String getPostalCode();
    String getTerritory();
}
