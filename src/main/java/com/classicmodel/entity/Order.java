package com.classicmodel.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @Column(name = "orderNumber")
    @NotNull
    private Integer orderNumber;

    @Column(name = "orderDate")
    @NotNull
    private LocalDate orderDate;

    @Column(name = "requiredDate")
    @NotNull
    private LocalDate requiredDate;

    @Column(name = "shippedDate")
    private LocalDate shippedDate;

    @Column(name = "status")
    @NotBlank
    private String status;

    @Column(name = "comments", length = 2000)
    private String comments;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customerNumber")
    @NotNull
    private Customer customer;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<OrderDetail> orderDetails = new ArrayList<>();

    public Order() {}

    public Integer getOrderNumber() { return orderNumber; }
    public void setOrderNumber(Integer orderNumber) { this.orderNumber = orderNumber; }

    public LocalDate getOrderDate() { return orderDate; }
    public void setOrderDate(LocalDate orderDate) { this.orderDate = orderDate; }

    public LocalDate getRequiredDate() { return requiredDate; }
    public void setRequiredDate(LocalDate requiredDate) { this.requiredDate = requiredDate; }

    public LocalDate getShippedDate() { return shippedDate; }
    public void setShippedDate(LocalDate shippedDate) { this.shippedDate = shippedDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getComments() { return comments; }
    public void setComments(String comments) { this.comments = comments; }

    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }

    public List<OrderDetail> getOrderDetails() { return orderDetails; }
    public void setOrderDetails(List<OrderDetail> orderDetails) { this.orderDetails = orderDetails; }

    @Override
    public String toString() {
        return "Order{orderNumber=" + orderNumber + ", status='" + status + "'}";
    }
}
