package com.classicmodel.projection;

import com.classicmodel.entity.Payment;
import com.classicmodel.entity.PaymentId;
import org.springframework.data.rest.core.config.Projection;
import java.math.BigDecimal;
import java.time.LocalDate;

@Projection(name = "paymentExcerpt", types = {Payment.class})
public interface PaymentProjection {
    PaymentId getId();
    LocalDate getPaymentDate();
    BigDecimal getAmount();
    CustomerProjection getCustomer();
}
