package com.rick.masters.Rest.Api.controller;

import com.rick.masters.Rest.Api.domain.entity.Driver;
import com.rick.masters.Rest.Api.domain.entity.Vehicle;
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

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration тесты контроллера {@link com.rick.masters.Rest.Api.controller.VehicleController}
 */

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureEmbeddedDatabase
@FieldDefaults(level = AccessLevel.PRIVATE)
class VehicleControllerTest {

    @Autowired
    MockMvc mockMvc;
    JSONObject jsonDriver;
    JSONObject jsonVehicle;

    @BeforeEach
    void setUp() throws JSONException {
        jsonVehicle = new JSONObject();
        jsonDriver = new JSONObject();
        jsonDriver.put("name", "name");
        jsonDriver.put("passportId", "1234111111");
        jsonDriver.put("drivingLicenceCategories", "A");
        jsonDriver.put("birthdayDate", "1993-12-12");
        jsonDriver.put("vehicles", new HashSet<>());
        jsonVehicle.put("vin", "A2A2A2A2A2A2A2A2A");
        jsonVehicle.put("governmentNumber", "12-as-as");
        jsonVehicle.put("manufacturer", "HK");
        jsonVehicle.put("brand", "HK");
        jsonVehicle.put("yearOfManufacture", "1292-12-12");
        jsonVehicle.put("driver", null);
        jsonVehicle.put("details", null);
    }

    @AfterEach
    void tearDown() {
        jsonVehicle = null;
        jsonDriver = null;
    }

    @Test
    void setDriverToVehicle() throws Exception {

        String requestMappingDriver = "/driver";
        mockMvc.perform(multipart(requestMappingDriver, HttpMethod.POST)
                        .content(jsonDriver.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        String requestMappingVehicle = "/vehicle";
        mockMvc.perform(multipart(requestMappingVehicle, HttpMethod.POST)
                        .content(jsonVehicle.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());


        mockMvc.perform(get(requestMappingVehicle + "/set_driver")
                        .param("driverId", String.valueOf(1L))
                        .param("vehicleId", String.valueOf(1L))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }
}