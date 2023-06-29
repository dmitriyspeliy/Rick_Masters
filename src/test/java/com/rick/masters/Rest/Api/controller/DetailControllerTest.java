package com.rick.masters.Rest.Api.controller;

import com.rick.masters.Rest.Api.domain.entity.Vehicle;
import com.rick.masters.Rest.Api.domain.record.DetailRecord;
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
 * Integration тесты контроллера {@link com.rick.masters.Rest.Api.controller.DetailController}
 */

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureEmbeddedDatabase
@FieldDefaults(level = AccessLevel.PRIVATE)
class DetailControllerTest {

    final String requestMappingDetail = "/detail";

    @Autowired
    MockMvc mockMvc;

    private DetailRecord detailRecord;
    private Vehicle vehicle;

    JSONObject jsonDetail;
    JSONObject jsonDetail1;
    JSONObject jsonInvalidDetail;
    JSONObject jsonVehicle;


    @BeforeEach
    void setUp() throws JSONException {
        detailRecord = new DetailRecord();
        vehicle = new Vehicle();
        detailRecord.setSerialNumber("DDD232DDDD");
        detailRecord.setVehicle(vehicle);
        jsonDetail = new JSONObject();
        jsonDetail1 = new JSONObject();
        jsonVehicle = new JSONObject();
        jsonVehicle.put("vin", "A2A2A2A2A2A2A2A2A");
        jsonVehicle.put("governmentNumber", "12-as-as");
        jsonVehicle.put("manufacturer", "HK");
        jsonVehicle.put("brand", "HK");
        jsonVehicle.put("yearOfManufacture", "1292-12-12");
        jsonVehicle.put("driver", null);
        jsonVehicle.put("details", null);
        jsonDetail.put("serialNumber", "DDD232DDDD");
        jsonDetail.put("vehicle", jsonVehicle.toString());
        jsonDetail1.put("serialNumber", "DDD232DDDS");
        jsonDetail1.put("vehicle", jsonVehicle.toString());
        jsonInvalidDetail = new JSONObject();
        jsonInvalidDetail.put("serialNumber", "asd");
        jsonInvalidDetail.put("vehicle", null);
    }

    @AfterEach
    void tearDown() {
        detailRecord = null;
        vehicle = null;
        jsonDetail = null;
        jsonVehicle = null;
    }

    @Test
    void given_200_when_saveDetail() throws Exception {


        mockMvc.perform(multipart(requestMappingDetail, HttpMethod.POST)
                        .content(jsonDetail.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());


    }

    @Test
    void given_400_when_saveDetail_with_invalid_param() throws Exception {


        mockMvc.perform(multipart(requestMappingDetail, HttpMethod.POST)
                        .content(jsonInvalidDetail.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }


    @Test
    void given_200_when_setDetailToVehicleByVIN() throws Exception {

        String requestMappingVehicle = "/vehicle";
        mockMvc.perform(multipart(requestMappingVehicle, HttpMethod.POST)
                        .content(jsonVehicle.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());


        mockMvc.perform(multipart(requestMappingDetail, HttpMethod.POST)
                        .content(jsonDetail.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(multipart(requestMappingDetail + "/set_detail_vin", HttpMethod.POST)
                        .content(jsonDetail.toString())
                        .param("vin", "A2A2A2A2A2A2A2A2A")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());


    }

    @Test
    void given_200_when_changeDetail() throws Exception {

        mockMvc.perform(multipart(requestMappingDetail, HttpMethod.POST)
                        .content(jsonDetail.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(multipart(requestMappingDetail, HttpMethod.POST)
                        .content(jsonDetail1.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(get(requestMappingDetail + "/change_detail")
                        .param("detailOld", jsonDetail.get("serialNumber").toString())
                        .param("detailNew", jsonDetail1.get("serialNumber").toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }
}