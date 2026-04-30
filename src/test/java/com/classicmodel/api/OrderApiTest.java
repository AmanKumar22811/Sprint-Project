package com.classicmodel.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.hamcrest.Matchers.greaterThan;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class OrderApiTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    private static final int TEST_ORDER_NUM = 99901;

    // =========================
    // GET
    // =========================

    @Test
    @Order(1)
    void getAllOrders_returns200WithEmbedded() throws Exception {

        mockMvc.perform(get("/api/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.orders").isArray())
                .andExpect(jsonPath("$._embedded.orders.length()")
                        .value(greaterThan(0)));
    }

    @Test
    @Order(2)
    void getOrderById_whenExists_returns200() throws Exception {

        mockMvc.perform(get("/api/orders/10100"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderNumber").value(10100));
    }

    @Test
    @Order(3)
    void getOrderById_whenNotExists_returns404() throws Exception {

        mockMvc.perform(get("/api/orders/999999"))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(4)
    void searchByStatus_shipped_returns200() throws Exception {

        mockMvc.perform(
                get("/api/orders/search/findByStatus")
                    .param("status","Shipped")
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$._embedded.orders").isArray());
    }

    @Test
    @Order(5)
    void searchByCustomerNumber_returns200() throws Exception {

        mockMvc.perform(
                get("/api/orders/search/findByCustomer_CustomerNumber")
                    .param("customerNumber","103")
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$._embedded.orders").isArray());
    }

    @Test
    @Order(6)
    void searchByStatus_inProcess_returns200() throws Exception {

        mockMvc.perform(
                get("/api/orders/search/findByStatus")
                    .param("status","In Process")
        )
        .andExpect(status().isOk());
    }

    // =========================
    // POST
    // =========================

    @Test
    @Order(10)
    void createOrder_withValidBody_returns201() throws Exception {

        Map<String,Object> body = Map.of(
                "orderNumber", TEST_ORDER_NUM,
                "orderDate","2026-01-15",
                "requiredDate","2026-01-30",
                "status","In Process",
                "customer","/api/customers/103"
        );

        mockMvc.perform(
                post("/api/orders")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(body))
        )
        .andExpect(status().isCreated());
    }

    @Test
    @Order(11)
    void createOrder_missingStatus_returns400() throws Exception {

        Map<String,Object> body = Map.of(
                "orderNumber",99902,
                "orderDate","2026-01-16",
                "requiredDate","2026-02-01",
                "customer","/api/customers/103"
        );

        mockMvc.perform(
                post("/api/orders")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(body))
        )
        .andExpect(status().isBadRequest());
    }

    // duplicate behaves like upsert
    @Test
    @Order(12)
    void createOrder_duplicateOrderNumber_returns201_currentBehavior() throws Exception {

        Map<String,Object> body = Map.of(
                "orderNumber", TEST_ORDER_NUM,
                "orderDate","2026-01-17",
                "requiredDate","2026-02-02",
                "status","Shipped",
                "customer","/api/customers/103"
        );

        mockMvc.perform(
                post("/api/orders")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(body))
        )
        .andExpect(status().isCreated());
    }

    // =========================
    // PATCH
    // =========================

    @Test
    @Order(20)
    void patchOrder_status_returns204() throws Exception {

        Map<String,String> patch = Map.of(
                "status","On Hold"
        );

        mockMvc.perform(
                patch("/api/orders/" + TEST_ORDER_NUM)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(patch))
        )
        .andExpect(status().isNoContent());
    }

    @Test
    @Order(21)
    void patchOrder_shippedDateAndComments_returns204() throws Exception {

        Map<String,String> patch = Map.of(
                "shippedDate","2026-01-20",
                "status","Shipped",
                "comments","Updated via test"
        );

        mockMvc.perform(
                patch("/api/orders/" + TEST_ORDER_NUM)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(patch))
        )
        .andExpect(status().isNoContent());
    }

    @Test
    @Order(22)
    void patchOrder_nonExistent_returns404() throws Exception {

        Map<String,String> patch = Map.of(
                "status","Cancelled"
        );

        mockMvc.perform(
                patch("/api/orders/999999")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(patch))
        )
        .andExpect(status().isNotFound());
    }

 
}