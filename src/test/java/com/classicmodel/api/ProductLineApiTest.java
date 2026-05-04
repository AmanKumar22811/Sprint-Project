package com.classicmodel.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
<<<<<<< Updated upstream
=======
<<<<<<< HEAD
 * ProductLine API integration tests (Spring Data REST - HAL format)
=======
>>>>>>> Stashed changes
 * ProductLine API integration tests using real MySQL (classicmodels DB).
 *
 * image column was changed from MEDIUMBLOB to TEXT (URL storage).
 * Entity uses @Column(name="image", columnDefinition="TEXT") String imageUrl.
<<<<<<< Updated upstream
=======
>>>>>>> e569a9f8118e5020892d9cb259b7a0082a1e2a83
>>>>>>> Stashed changes
 */
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductLineApiTest {

    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;

    private static final String TEST_LINE = "Test Vehicles";

    // ── GET (Read) ──────────────────────────────────────────────────────────

    @Test @Order(1)
    void getAllProductLines_returns200WithEmbedded() throws Exception {
        mockMvc.perform(get("/api/productlines"))
            .andExpect(status().isOk())
<<<<<<< Updated upstream
            .andExpect(jsonPath("$._embedded.productlines").isArray())
            .andExpect(jsonPath("$._embedded.productlines.length()").value(greaterThan(0)));
=======
<<<<<<< HEAD
            .andExpect(jsonPath("$._embedded.productLines").isArray())  // ✅ FIXED
            .andExpect(jsonPath("$._embedded.productLines.length()")
                .value(greaterThan(0)));
=======
            .andExpect(jsonPath("$._embedded.productlines").isArray())
            .andExpect(jsonPath("$._embedded.productlines.length()").value(greaterThan(0)));
>>>>>>> e569a9f8118e5020892d9cb259b7a0082a1e2a83
>>>>>>> Stashed changes
    }

    @Test @Order(2)
    void getProductLineById_ClassicCars_returns200() throws Exception {
        mockMvc.perform(get("/api/productlines/Classic Cars"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.productLine").value("Classic Cars"));
    }

    @Test @Order(3)
    void getProductLineById_nonExistent_returns404() throws Exception {
        mockMvc.perform(get("/api/productlines/NoSuchLine"))
            .andExpect(status().isNotFound());
    }

    @Test @Order(4)
    void getAllProductLines_containsAllSixDefaultLines() throws Exception {
        mockMvc.perform(get("/api/productlines"))
            .andExpect(status().isOk())
<<<<<<< Updated upstream
            .andExpect(jsonPath("$._embedded.productlines[*].productLine",
=======
<<<<<<< HEAD
            .andExpect(jsonPath("$._embedded.productLines[*].productLine", // ✅ FIXED
=======
            .andExpect(jsonPath("$._embedded.productlines[*].productLine",
>>>>>>> e569a9f8118e5020892d9cb259b7a0082a1e2a83
>>>>>>> Stashed changes
                hasItems("Classic Cars", "Motorcycles", "Planes",
                         "Ships", "Trains", "Trucks and Buses")));
    }

    @Test @Order(5)
    void getProductLine_ClassicCars_imageUrlIsTextNotBlob() throws Exception {
        mockMvc.perform(get("/api/productlines/Classic Cars"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.productLine").value("Classic Cars"));
    }

    // ── POST (Create) ───────────────────────────────────────────────────────

    @Test @Order(10)
    void createProductLine_withValidBody_returns201WithBody() throws Exception {
        Map<String, String> body = Map.of(
<<<<<<< Updated upstream
            "productLine",    TEST_LINE,
            "textDescription","Test vehicles for automated testing"
        );
=======
<<<<<<< HEAD
            "productLine", TEST_LINE,
            "textDescription", "Test vehicles for automated testing"
        );

=======
            "productLine",    TEST_LINE,
            "textDescription","Test vehicles for automated testing"
        );
>>>>>>> e569a9f8118e5020892d9cb259b7a0082a1e2a83
>>>>>>> Stashed changes
        mockMvc.perform(post("/api/productlines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(body)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.productLine").value(TEST_LINE))
<<<<<<< Updated upstream
            .andExpect(jsonPath("$.textDescription").value("Test vehicles for automated testing"));
=======
<<<<<<< HEAD
            .andExpect(jsonPath("$.textDescription")
                .value("Test vehicles for automated testing"));
=======
            .andExpect(jsonPath("$.textDescription").value("Test vehicles for automated testing"));
>>>>>>> e569a9f8118e5020892d9cb259b7a0082a1e2a83
>>>>>>> Stashed changes
    }

    @Test @Order(11)
    void createProductLine_emptyProductLine_returns400() throws Exception {
        Map<String, String> body = Map.of(
<<<<<<< Updated upstream
            "productLine",    "",
            "textDescription","Missing name"
        );
=======
<<<<<<< HEAD
            "productLine", "",
            "textDescription", "Missing name"
        );

=======
            "productLine",    "",
            "textDescription","Missing name"
        );
>>>>>>> e569a9f8118e5020892d9cb259b7a0082a1e2a83
>>>>>>> Stashed changes
        mockMvc.perform(post("/api/productlines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(body)))
            .andExpect(status().isBadRequest());
    }

<<<<<<< Updated upstream
    /**
     * Duplicate product line → Spring Data REST UPSERT → 2xx.
     */
=======
<<<<<<< HEAD
>>>>>>> Stashed changes
    @Test @Order(12)
    void createProductLine_duplicate_springDataRestUpserts_returns2xx() throws Exception {
        Map<String, String> body = Map.of(
            "productLine",    TEST_LINE,
            "textDescription","Duplicate line"
        );
<<<<<<< Updated upstream
=======

=======
    /**
     * Duplicate product line → Spring Data REST UPSERT → 2xx.
     */
    @Test @Order(12)
    void createProductLine_duplicate_springDataRestUpserts_returns2xx() throws Exception {
        Map<String, String> body = Map.of(
            "productLine",    TEST_LINE,
            "textDescription","Duplicate line"
        );
>>>>>>> e569a9f8118e5020892d9cb259b7a0082a1e2a83
>>>>>>> Stashed changes
        mockMvc.perform(post("/api/productlines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(body)))
            .andExpect(status().is2xxSuccessful());
    }

    // ── PATCH (Update) ──────────────────────────────────────────────────────

    @Test @Order(20)
    void patchProductLine_textDescription_returns200WithBody() throws Exception {
<<<<<<< Updated upstream
        Map<String, String> patch = Map.of("textDescription", "Updated test description");
=======
<<<<<<< HEAD
        Map<String, String> patch = Map.of(
            "textDescription", "Updated test description"
        );

=======
        Map<String, String> patch = Map.of("textDescription", "Updated test description");
>>>>>>> e569a9f8118e5020892d9cb259b7a0082a1e2a83
>>>>>>> Stashed changes
        mockMvc.perform(patch("/api/productlines/" + TEST_LINE)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(patch)))
            .andExpect(status().isOk())
<<<<<<< Updated upstream
            .andExpect(jsonPath("$.textDescription").value("Updated test description"));
=======
<<<<<<< HEAD
            .andExpect(jsonPath("$.textDescription")
                .value("Updated test description"));
=======
            .andExpect(jsonPath("$.textDescription").value("Updated test description"));
>>>>>>> e569a9f8118e5020892d9cb259b7a0082a1e2a83
>>>>>>> Stashed changes
    }

    @Test @Order(21)
    void patchProductLine_imageUrl_returns200WithBody() throws Exception {
<<<<<<< Updated upstream
        Map<String, String> patch = Map.of("imageUrl", "https://example.com/test-vehicles.jpg");
=======
<<<<<<< HEAD
        Map<String, String> patch = Map.of(
            "imageUrl", "https://example.com/test-vehicles.jpg"
        );

=======
        Map<String, String> patch = Map.of("imageUrl", "https://example.com/test-vehicles.jpg");
>>>>>>> e569a9f8118e5020892d9cb259b7a0082a1e2a83
>>>>>>> Stashed changes
        mockMvc.perform(patch("/api/productlines/" + TEST_LINE)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(patch)))
            .andExpect(status().isOk())
<<<<<<< Updated upstream
            .andExpect(jsonPath("$.imageUrl").value("https://example.com/test-vehicles.jpg"));
=======
<<<<<<< HEAD
            .andExpect(jsonPath("$.imageUrl")
                .value("https://example.com/test-vehicles.jpg"));
=======
            .andExpect(jsonPath("$.imageUrl").value("https://example.com/test-vehicles.jpg"));
>>>>>>> e569a9f8118e5020892d9cb259b7a0082a1e2a83
>>>>>>> Stashed changes
    }

    @Test @Order(22)
    void patchProductLine_nonExistent_returns404() throws Exception {
        Map<String, String> patch = Map.of("textDescription", "Ghost");
<<<<<<< Updated upstream
=======
<<<<<<< HEAD

=======
>>>>>>> e569a9f8118e5020892d9cb259b7a0082a1e2a83
>>>>>>> Stashed changes
        mockMvc.perform(patch("/api/productlines/NoSuchLine")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(patch)))
            .andExpect(status().isNotFound());
    }

<<<<<<< Updated upstream
=======
<<<<<<< HEAD
    
}
=======
>>>>>>> Stashed changes
    // ── Cleanup test data ───────────────────────────────────────────────────


    @Test @Order(100)
    void cleanup_verifyTestProductLineGone() throws Exception {
        mockMvc.perform(get("/api/productlines/" + TEST_LINE))
            .andExpect(status().isNotFound());
    }
}
<<<<<<< Updated upstream
=======
>>>>>>> e569a9f8118e5020892d9cb259b7a0082a1e2a83
>>>>>>> Stashed changes
