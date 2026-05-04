package com.classicmodel.event;

import com.classicmodel.entity.Order;
import com.classicmodel.exception.BadRequestException;
import com.classicmodel.exception.CustomerNotFoundException;
import com.classicmodel.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RepositoryEventHandler
public class OrderEventHandler {

    private static final List<String> VALID_STATUSES =
        List.of("In Process", "On Hold", "Resolved", "Cancelled", "Shipped", "Disputed");

    @Autowired private CustomerRepository customerRepository;

    @HandleBeforeCreate
    @HandleBeforeSave
    public void handleBeforeSave(Order order) {
        if (order.getCustomer() == null || order.getCustomer().getCustomerNumber() == null) {
            throw new BadRequestException("Order must be associated with a valid customer.");
        }
        customerRepository.findById(order.getCustomer().getCustomerNumber())
            .orElseThrow(() -> new CustomerNotFoundException(order.getCustomer().getCustomerNumber()));

        if (order.getStatus() == null || !VALID_STATUSES.contains(order.getStatus())) {
            throw new BadRequestException(
                "Order status must be one of: " + String.join(", ", VALID_STATUSES));
        }
        if (order.getOrderDate() == null) {
            throw new BadRequestException("Order date is required.");
        }
        if (order.getRequiredDate() == null) {
            throw new BadRequestException("Required date is required.");
        }
        if (order.getRequiredDate().isBefore(order.getOrderDate())) {
            throw new BadRequestException("Required date cannot be before the order date.");
        }
    }
}
