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
class OrderDetailApiTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetAllOrderDetails_returns200() throws Exception {
        mockMvc.perform(get("/api/orderdetails"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$._embedded.orderDetails").isArray())
            .andExpect(jsonPath("$._embedded.orderDetails.length()").value(greaterThan(0)));
    }

    @Test
    void testSearchByOrderNumber_viaAssociation_returns200() throws Exception {
        mockMvc.perform(get("/api/orderdetails/search/findByOrder_OrderNumber")
                .param("orderNumber","10100"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$._embedded.orderDetails").isArray())
            .andExpect(jsonPath("$._embedded.orderDetails.length()").value(greaterThan(0)));
    }

    @Test
    void testSearchByProductCode_viaAssociation_returns200() throws Exception {
        mockMvc.perform(get("/api/orderdetails/search/findByProduct_ProductCode")
                .param("productCode","S18_1749"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$._embedded.orderDetails").isArray());
    }

    @Test
    void testSearchByOrderStatus_returns200() throws Exception {
        mockMvc.perform(get("/api/orderdetails/search/findByOrder_Status")
                .param("status","Shipped"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$._embedded.orderDetails").isArray());
    }

    @Test
    void testCountEndpoint_returnsCount() throws Exception {
        mockMvc.perform(get("/api/orderdetails"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.page.totalElements").value(greaterThan(0)));
    }
}