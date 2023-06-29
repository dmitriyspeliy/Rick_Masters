package com.rick.masters.Rest.Api.controller;

import com.rick.masters.Rest.Api.domain.entity.Driver;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration тесты контроллера {@link com.rick.masters.Rest.Api.controller.AccountController}
 */

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureEmbeddedDatabase
@FieldDefaults(level = AccessLevel.PRIVATE)
class AccountControllerTest {

    final String requestMappingAccount = "/account";
    final String requestMappingDriver = "/driver";
    final String color = "Красный";
    final Long value = 20L;

    @Autowired
    MockMvc mockMvc;
    Driver driver;
    JSONObject jsonDriver;


    @BeforeEach
    void setUp() throws JSONException {
        driver = new Driver();
        driver.setId(1L);
        jsonDriver = new JSONObject();
        jsonDriver.put("name", "name");
        jsonDriver.put("passportId", "1234111111");
        jsonDriver.put("drivingLicenceCategories", "A");
        jsonDriver.put("birthdayDate", "1993-12-12");
        jsonDriver.put("vehicles", null);
        jsonDriver.put("account", null);
    }

    @AfterEach
    void tearDown() {
        driver = null;
        jsonDriver = null;
    }

    @Test
    void given_200_when_putMoney_withValid_Param() throws Exception {

        String url = requestMappingAccount + "/{id}/put_money";


        mockMvc.perform(multipart(requestMappingDriver, HttpMethod.POST)
                        .content(jsonDriver.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());


        mockMvc.perform(get(url, 1L)
                        .param("color", color)
                        .param("value", String.valueOf(value))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    void given_400_when_putMoney_with_Invalid_Param() throws Exception {

        String url = requestMappingAccount + "/{id}/put_money";


        mockMvc.perform(multipart(requestMappingDriver, HttpMethod.POST)
                        .content(jsonDriver.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());


        mockMvc.perform(get(url, 10L)
                        .param("color", color)
                        .param("value", String.valueOf(value))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());


        mockMvc.perform(get(url, 1L)
                        .param("color", color)
                        .param("value", String.valueOf(-1L))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }

    @Test
    void given_200_when_writeOff_with_Valid_Param() throws Exception {

        String url = requestMappingAccount + "/{id}/write_off";


        mockMvc.perform(multipart(requestMappingDriver, HttpMethod.POST)
                        .content(jsonDriver.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());


        mockMvc.perform(get(requestMappingAccount + "/{id}/put_money", 1L)
                        .param("color", color)
                        .param("value", String.valueOf(value))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());


        mockMvc.perform(get(url, 1L)
                        .param("color", color)
                        .param("value", String.valueOf(value))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    void given_exception_when_writeOff_with_more_than_in_value_account_value_return_void() throws Exception {
        String url = requestMappingAccount + "/{id}/write_off";


        mockMvc.perform(multipart(requestMappingDriver, HttpMethod.POST)
                        .content(jsonDriver.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());


        mockMvc.perform(get(requestMappingAccount + "/{id}/put_money", 1L)
                        .param("color", color)
                        .param("value", String.valueOf(value))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());


        mockMvc.perform(get(url, 1L)
                        .param("color", color)
                        .param("value", String.valueOf(-30L))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }

}