package com.classicmodel.event;

import com.classicmodel.entity.Office;
import com.classicmodel.exception.BadRequestException;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

@Component
@RepositoryEventHandler
public class OfficeEventHandler {

    @HandleBeforeCreate
    @HandleBeforeSave
    public void handleBeforeSave(Office office) {
        if (office.getOfficeCode() == null || office.getOfficeCode().isBlank()) {
            throw new BadRequestException("Office must have a non-blank officeCode.");
        }
        if (office.getCity() == null || office.getCity().isBlank()) {
            throw new BadRequestException("Office city is required.");
        }
        if (office.getCountry() == null || office.getCountry().isBlank()) {
            throw new BadRequestException("Office country is required.");
        }
        if (office.getPhone() == null || office.getPhone().isBlank()) {
            throw new BadRequestException("Office phone is required.");
        }
        if (office.getTerritory() == null || office.getTerritory().isBlank()) {
            throw new BadRequestException("Office territory is required.");
        }
    }
}
