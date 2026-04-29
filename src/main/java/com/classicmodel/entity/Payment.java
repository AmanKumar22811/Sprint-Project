package com.classicmodel.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "payments")
public class Payment {

    @EmbeddedId
    private PaymentId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("customerNumber")
    @JoinColumn(name = "customerNumber")
    private Customer customer;

    @Column(name = "paymentDate")
    private LocalDate paymentDate;

    @Column(name = "amount")
    private BigDecimal amount;

    public Payment() {}

    public PaymentId getId() { return id; }
    public void setId(PaymentId id) { this.id = id; }

    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }

    public LocalDate getPaymentDate() { return paymentDate; }
    public void setPaymentDate(LocalDate paymentDate) { this.paymentDate = paymentDate; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Payment)) return false;
        Payment that = (Payment) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Payment{id=" + id + ", amount=" + amount + "}";
    }
}
