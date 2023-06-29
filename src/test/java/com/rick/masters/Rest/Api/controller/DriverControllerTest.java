package com.rick.masters.Rest.Api.controller;

import com.rick.masters.Rest.Api.utils.DriverSort;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration тесты контроллера {@link com.rick.masters.Rest.Api.controller.DriverController}
 */

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureEmbeddedDatabase
@FieldDefaults(level = AccessLevel.PRIVATE)
class DriverControllerTest {

    final String requestMappingDriver = "/driver";

    @Autowired
    MockMvc mockMvc;
    JSONObject jsonDriver1;
    JSONObject jsonDriver2;
    JSONObject jsonDriver3;

    @BeforeEach
    void setUp() throws JSONException {
        jsonDriver1 = new JSONObject();
        jsonDriver1.put("name", "C");
        jsonDriver1.put("passportId", "1234111141");
        jsonDriver1.put("drivingLicenceCategories", "A");
        jsonDriver1.put("birthdayDate", "1993-12-12");
        jsonDriver1.put("vehicles", null);
        jsonDriver1.put("account", null);
        jsonDriver2 = new JSONObject();
        jsonDriver2.put("name", "B");
        jsonDriver2.put("passportId", "1234111311");
        jsonDriver2.put("drivingLicenceCategories", "A");
        jsonDriver2.put("birthdayDate", "1993-12-12");
        jsonDriver2.put("vehicles", null);
        jsonDriver2.put("account", null);
        jsonDriver3 = new JSONObject();
        jsonDriver3.put("name", "A");
        jsonDriver3.put("passportId", "1234117111");
        jsonDriver3.put("drivingLicenceCategories", "A");
        jsonDriver3.put("birthdayDate", "1993-12-12");
        jsonDriver3.put("vehicles", null);
        jsonDriver3.put("account", null);
    }

    @AfterEach
    void tearDown() {
        jsonDriver1 = null;
    }


    @Test
    void getAllDrivers() throws Exception {
        mockMvc.perform(multipart(requestMappingDriver, HttpMethod.POST)
                        .content(jsonDriver1.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(multipart(requestMappingDriver, HttpMethod.POST)
                        .content(jsonDriver2.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(multipart(requestMappingDriver, HttpMethod.POST)
                        .content(jsonDriver3.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());


        mockMvc.perform(get(requestMappingDriver + "/get_all")
                        .param("offSet", String.valueOf(1))
                        .param("limit", String.valueOf(3))
                        .param("sort", String.valueOf(DriverSort.NAME_ASC))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());

    }

    @Test
    void findDriverByPassportId() throws Exception {

        mockMvc.perform(multipart(requestMappingDriver, HttpMethod.POST)
                        .content(jsonDriver1.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(get(requestMappingDriver + "/by_passport_id")
                        .param("passportId", jsonDriver1.get("passportId").toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }
}