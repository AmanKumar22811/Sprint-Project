package com.classicmodel.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.greaterThan;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class PaymentApiTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetAllPayments_returns200() throws Exception {

        mockMvc.perform(get("/api/payments"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$._embedded.payments").isArray())
            .andExpect(jsonPath("$._embedded.payments.length()")
                .value(greaterThan(0)));
    }

    @Test
    void testSearchByCustomerNumber_viaAssociation_returns200() throws Exception {

        mockMvc.perform(
                get("/api/payments/search/findByCustomer_CustomerNumber")
                    .param("customerNumber","103")
        )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$._embedded.payments").isArray())
            .andExpect(jsonPath("$._embedded.payments.length()")
                .value(greaterThan(0)));
    }

    @Test
    void testSearchByCheckNumber_viaIdPrefix_returns200() throws Exception {

        mockMvc.perform(
                get("/api/payments/search/findById_CheckNumber")
                    .param("checkNumber","HQ336336")
        )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$._embedded.payments").isArray());
    }

    @Test
    void testSearchByPaymentDate_returns200() throws Exception {

        mockMvc.perform(
                get("/api/payments/search/findByPaymentDate")
                    .param("paymentDate","2004-10-19")
        )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$._embedded.payments").isArray());
    }
}