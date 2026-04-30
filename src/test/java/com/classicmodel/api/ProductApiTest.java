package com.classicmodel.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
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
class ProductApiTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAllProducts_returns200() throws Exception {
        mockMvc.perform(get("/api/products"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$._embedded.products").isArray())
            .andExpect(jsonPath("$._embedded.products.length()").value(greaterThan(0)));
    }

    @Test
    void testGetProductById_whenExists_returns200() throws Exception {
        mockMvc.perform(get("/api/products/S18_1749"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.productCode").value("S18_1749"));
    }

    @Test
    void testGetProductById_whenNotExists_returns404() throws Exception {
        mockMvc.perform(get("/api/products/INVALID_CODE"))
            .andExpect(status().isNotFound());
    }

    @Test
    void testSearchByProductLine_returnsResults() throws Exception {

        mockMvc.perform(
                get("/api/products/search/findByProductLineEntity_ProductLine")
                    .param("productLine","Classic Cars")
        )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$._embedded.products").isArray())
            .andExpect(jsonPath("$._embedded.products.length()").value(greaterThan(0)));
    }

    @Test
    void testSearchByVendor_returnsResults() throws Exception {

        mockMvc.perform(
                get("/api/products/search/findByProductVendorIgnoreCase")
                    .param("vendor","Min Lin Diecast")
        )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$._embedded.products").isArray());
    }

    @Test
    void testSearchByScale_returnsResults() throws Exception {
        mockMvc.perform(
                get("/api/products/search/findByProductScale")
                    .param("scale","1:18")
        )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$._embedded.products").isArray());
    }

    @Test
    void testSearchByLowStock_returnsResults() throws Exception {
        mockMvc.perform(
                get("/api/products/search/findByQuantityInStockLessThan")
                    .param("qty","500")
        )
            .andExpect(status().isOk());
    }

    @Test
    void testPatchProduct_MSRP_returns204() throws Exception {

        Map<String,String> patch = Map.of(
            "MSRP","200.00"
        );

        mockMvc.perform(
                patch("/api/products/S18_1749")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(patch))
        )
            .andExpect(status().isNoContent());
    }
}