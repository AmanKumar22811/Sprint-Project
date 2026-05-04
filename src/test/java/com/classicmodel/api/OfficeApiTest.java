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
class OfficeApiTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    private static final String TEST_CODE = "99";

    // =========================
    // GET
    // =========================

    @Test
    @Order(1)
    void getAllOffices_returns200WithEmbedded() throws Exception {

        mockMvc.perform(get("/api/offices"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.offices").isArray())
                .andExpect(jsonPath("$._embedded.offices.length()")
                        .value(greaterThan(0)));
    }

    @Test
    @Order(2)
    void getOfficeById_whenExists_returns200() throws Exception {

        mockMvc.perform(get("/api/offices/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.officeCode").value("1"));
    }

    @Test
    @Order(3)
    void getOfficeById_whenNotExists_returns404() throws Exception {

        mockMvc.perform(get("/api/offices/ZZZZ"))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(4)
    void searchByCountry_returns200() throws Exception {

        mockMvc.perform(
                get("/api/offices/search/findByCountry")
                    .param("country","USA")
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$._embedded.offices").isArray());
    }

    @Test
    @Order(5)
    void searchByCity_returnsCorrectOffice() throws Exception {

        mockMvc.perform(
                get("/api/offices/search/findByCity")
                    .param("city","Tokyo")
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$._embedded.offices").isArray())
        .andExpect(jsonPath("$._embedded.offices[0].city").value("Tokyo"));
    }
    // =========================
    // POST
    // =========================

    @Test
    @Order(10)
    void createOffice_withValidBody_returns201() throws Exception {

        Map<String,String> body = Map.of(
                "officeCode", TEST_CODE,
                "city", "Test City",
                "phone", "+1 000 000 0000",
                "addressLine1","123 Test St",
                "country","Testland",
                "postalCode","00000",
                "territory","TEST"
        );

        mockMvc.perform(
                post("/api/offices")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(body))
        )
        .andExpect(status().isCreated());
    }
    @Test
    @Order(11)
    void createOffice_missingCity_returns400() throws Exception {

        Map<String,String> body = Map.of(
                "officeCode","98",
                "phone","+1 000 000 0001",
                "addressLine1","456 Test Ave",
                "country","Testland",
                "postalCode","00001",
                "territory","TEST"
        );

        mockMvc.perform(
                post("/api/offices")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(body))
        )
        .andExpect(status().isBadRequest());
    }

    // duplicate behaves like upsert currently
    @Test
    @Order(12)
    void createOffice_duplicateCode_returns201_currentBehavior() throws Exception {

        Map<String,String> body = Map.of(
                "officeCode", TEST_CODE,
                "city", "Duplicate City",
                "phone", "+1 111 111 1111",
                "addressLine1", "Dup St",
                "country","Testland",
                "postalCode","11111",
                "territory","TEST"
        );

        mockMvc.perform(
                post("/api/offices")
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
    void patchOffice_city_returns204() throws Exception {

        Map<String,String> patch = Map.of(
                "city","Updated City"
        );

        mockMvc.perform(
                patch("/api/offices/" + TEST_CODE)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(patch))
        )
        .andExpect(status().isNoContent());
    }

    @Test
    @Order(21)
    void patchOffice_phoneAndTerritory_returns204() throws Exception {

        Map<String,String> patch = Map.of(
                "phone","+1 999 999 9999",
                "territory","UPDATED"
        );

        mockMvc.perform(
                patch("/api/offices/" + TEST_CODE)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(patch))
        )
        .andExpect(status().isNoContent());
    }

    @Test
    @Order(22)
    void patchOffice_nonExistent_returns404() throws Exception {

        Map<String,String> patch = Map.of(
                "city","Ghost City"
        );

        mockMvc.perform(
                patch("/api/offices/ZZZZ")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(patch))
        )
        .andExpect(status().isNotFound());
    }

}