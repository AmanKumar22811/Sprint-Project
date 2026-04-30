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
import static org.hamcrest.Matchers.hasItems;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductLineApiTest {

    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;

    private static final String TEST_LINE = "Test Vehicles";

    // =========================
    // GET
    // =========================

    @Test
    @Order(1)
    void getAllProductLines_returns200WithEmbedded() throws Exception {

        mockMvc.perform(get("/api/productlines"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$._embedded.productLines").isArray())
            .andExpect(
                jsonPath("$._embedded.productLines.length()")
                    .value(greaterThan(0))
            );
    }

    @Test
    @Order(2)
    void getProductLineById_classicCars_returns200() throws Exception {

        mockMvc.perform(get("/api/productlines/Classic Cars"))
            .andExpect(status().isOk())
            .andExpect(
                jsonPath("$.productLine")
                    .value("Classic Cars")
            );
    }

    @Test
    @Order(3)
    void getProductLineById_nonExistent_returns404() throws Exception {

        mockMvc.perform(
            get("/api/productlines/NoSuchLine")
        )
        .andExpect(status().isNotFound());
    }

    @Test
    @Order(4)
    void getProductLines_verifyAllSixDefaultLines() throws Exception {

        mockMvc.perform(get("/api/productlines"))
            .andExpect(status().isOk())
            .andExpect(
                jsonPath(
                    "$._embedded.productLines[*].productLine",
                    hasItems(
                        "Classic Cars",
                        "Motorcycles",
                        "Planes",
                        "Ships",
                        "Trains",
                        "Trucks and Buses"
                    )
                )
            );
    }

    // =========================
    // POST
    // =========================

    @Test
    @Order(10)
    void createProductLine_withValidBody_returns201() throws Exception {

        Map<String,String> body = Map.of(
            "productLine", TEST_LINE,
            "textDescription",
            "Test vehicles for automated testing"
        );

        mockMvc.perform(
                post("/api/productlines")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(
                        objectMapper.writeValueAsString(body)
                    )
        )
        .andExpect(status().isCreated());
    }

    @Test
    @Order(11)
    void createProductLine_missingProductLine_returns400() throws Exception {

        Map<String,String> body = Map.of(
            "textDescription","Missing name"
        );

        mockMvc.perform(
                post("/api/productlines")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(
                        objectMapper.writeValueAsString(body)
                    )
        )
        .andExpect(status().isBadRequest());
    }

    // current behavior acts like upsert
    @Test
    @Order(12)
    void createProductLine_duplicate_returns201_currentBehavior() throws Exception {

        Map<String,String> body = Map.of(
            "productLine", TEST_LINE,
            "textDescription","Duplicate line"
        );

        mockMvc.perform(
                post("/api/productlines")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(
                        objectMapper.writeValueAsString(body)
                    )
        )
        .andExpect(status().isCreated());
    }

    // =========================
    // PATCH
    // =========================

    @Test
    @Order(20)
    void patchProductLine_textDescription_returns204() throws Exception {

        Map<String,String> patch = Map.of(
            "textDescription",
            "Updated test description"
        );

        mockMvc.perform(
                patch("/api/productlines/" + TEST_LINE)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(
                        objectMapper.writeValueAsString(patch)
                    )
        )
        .andExpect(status().isNoContent());
    }

    @Test
    @Order(21)
    void patchProductLine_htmlDescription_returns204() throws Exception {

        Map<String,String> patch = Map.of(
            "htmlDescription",
            "<p>HTML description update</p>"
        );

        mockMvc.perform(
                patch("/api/productlines/" + TEST_LINE)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(
                        objectMapper.writeValueAsString(patch)
                    )
        )
        .andExpect(status().isNoContent());
    }

    @Test
    @Order(22)
    void patchProductLine_nonExistent_returns404() throws Exception {

        Map<String,String> patch = Map.of(
            "textDescription","Ghost"
        );

        mockMvc.perform(
                patch("/api/productlines/NoSuchLine")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(
                        objectMapper.writeValueAsString(patch)
                    )
        )
        .andExpect(status().isNotFound());
    }

   
}