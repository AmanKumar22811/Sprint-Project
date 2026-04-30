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
class EmployeeApiTest {

	@Autowired
	MockMvc mockMvc;
	@Autowired
	ObjectMapper objectMapper;

	private static final int TEST_EMP_NUM = 9991;

	@Test
	@Order(1)
	void getAllEmployees_returns200WithEmbedded() throws Exception {
		mockMvc.perform(get("/api/employees")).andExpect(status().isOk())
				.andExpect(jsonPath("$._embedded.employees").isArray())
				.andExpect(jsonPath("$._embedded.employees.length()").value(greaterThan(0)));
	}

	@Test
	@Order(2)
	void getEmployeeById_whenExists_returns200() throws Exception {
		mockMvc.perform(get("/api/employees/1002")).andExpect(status().isOk())
				.andExpect(jsonPath("$.employeeNumber").value(1002));
	}

	@Test
	@Order(3)
	void getEmployeeById_whenNotExists_returns404() throws Exception {
		mockMvc.perform(get("/api/employees/99999")).andExpect(status().isNotFound());
	}

	@Test
	@Order(4)
	void searchByOfficeCode_returns200() throws Exception {
		mockMvc.perform(get("/api/employees/search/findByOffice_OfficeCode").param("officeCode", "1"))
				.andExpect(status().isOk());
	}

	@Test
	@Order(5)
	void searchByJobTitle_returns200() throws Exception {
		mockMvc.perform(get("/api/employees/search/findByJobTitle").param("jobTitle", "Sales Rep"))
				.andExpect(status().isOk());
	}

	@Test
	@Order(6)
	void searchByLastName_returns200() throws Exception {
		mockMvc.perform(get("/api/employees/search/findByLastName").param("lastName", "Murphy"))
				.andExpect(status().isOk());
	}

	@Test
	@Order(10)
	void createEmployee_withValidBody_returns201() throws Exception {

		Map<String, Object> body = Map.of("employeeNumber", TEST_EMP_NUM, "lastName", "TestLast", "firstName",
				"TestFirst", "extension", "x991", "email", "test9991@classicmodelcars.com", "jobTitle", "Test Rep",
				"office", "/api/offices/1");

		mockMvc.perform(post("/api/employees").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(body))).andExpect(status().isCreated());
	}

	@Test
	@Order(11)
	void createEmployee_missingLastName_returns400() throws Exception {

		Map<String, Object> body = Map.of("employeeNumber", 9992, "firstName", "OnlyFirst", "extension", "x992",
				"email", "bad9992@classicmodelcars.com", "jobTitle", "Rep", "office", "/api/offices/1");

		mockMvc.perform(post("/api/employees").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(body))).andExpect(status().isBadRequest());
	}

	@Test
	@Order(12)
	void createEmployee_duplicateId_returns201_currentBehavior() throws Exception {

		Map<String, Object> body = Map.of("employeeNumber", TEST_EMP_NUM, "lastName", "Dup", "firstName", "Dup",
				"extension", "x000", "email", "dup@classicmodelcars.com", "jobTitle", "Rep", "office",
				"/api/offices/1");

		mockMvc.perform(post("/api/employees").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(body))).andExpect(status().isCreated());
	}

	@Test
	@Order(20)
	void patchEmployee_jobTitle_returns204() throws Exception {

		Map<String, String> patch = Map.of("jobTitle", "Senior Test Rep");

		mockMvc.perform(patch("/api/employees/" + TEST_EMP_NUM).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(patch))).andExpect(status().isNoContent());
	}

	@Test
	@Order(21)
	void patchEmployee_emailAndExtension_returns204() throws Exception {

		Map<String, String> patch = Map.of("email", "updated9991@classicmodelcars.com", "extension", "x999");

		mockMvc.perform(patch("/api/employees/" + TEST_EMP_NUM).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(patch))).andExpect(status().isNoContent());
	}

	@Test
	@Order(22)
	void patchEmployee_nonExistent_returns404() throws Exception {

		Map<String, String> patch = Map.of("jobTitle", "Ghost");

		mockMvc.perform(patch("/api/employees/99999").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(patch))).andExpect(status().isNotFound());
	}

	@Test
	@Order(99)
	void patchEmployee1002_restoreJobTitle() throws Exception {

		Map<String, String> restore = Map.of("jobTitle", "President");

		mockMvc.perform(patch("/api/employees/1002").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(restore))).andExpect(status().isNoContent());
	}
}