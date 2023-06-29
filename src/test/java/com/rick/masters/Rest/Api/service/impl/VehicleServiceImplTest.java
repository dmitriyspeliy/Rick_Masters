package com.rick.masters.Rest.Api.service.impl;

import com.rick.masters.Rest.Api.domain.entity.Driver;
import com.rick.masters.Rest.Api.domain.entity.Vehicle;
import com.rick.masters.Rest.Api.domain.record.VehicleRecord;
import com.rick.masters.Rest.Api.mapper.VehicleMapper;
import com.rick.masters.Rest.Api.repository.DriverRepository;
import com.rick.masters.Rest.Api.repository.VehicleRepository;
import com.rick.masters.Rest.Api.utils.VehicleSort;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Mockito.*;


/**
 * Тесты на сервис {@link com.rick.masters.Rest.Api.service.impl.VehicleServiceImpl}
 */

@ExtendWith(MockitoExtension.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
class VehicleServiceImplTest {

    @InjectMocks
    VehicleServiceImpl out;
    @Mock
    VehicleRepository vehicleRepository;
    @Mock
    DriverRepository driverRepository;
    @Mock
    VehicleMapper vehicleMapper;

    VehicleRecord vehicleRecord;
    Vehicle vehicle;
    Pageable page;
    Page<Vehicle> vehicles;
    Driver driver;
    Set<Vehicle> vehicleSet;

    @BeforeEach
    void setUp() {
        page = PageRequest.of(1, 1, VehicleSort.YEAR_ASC.getSortValue());
        vehicle = new Vehicle();
        vehicleRecord = new VehicleRecord();
        vehicles = new PageImpl<>(new ArrayList<>(), page, 1L);
        driver = new Driver();
        vehicleSet = new HashSet<>();
        driver.setVehicles(vehicleSet);
    }

    @AfterEach
    void tearDown() {
        vehicle = null;
        vehicleRecord = null;
        driver = null;
    }

    @Test
    void given_answer_ok_when_save_vehicle() {
        when(vehicleMapper.toEntity(vehicleRecord)).thenReturn(vehicle);

        out.saveVehicle(vehicleRecord);

        verify(vehicleRepository, times(1)).save(vehicle);
    }

    @Test
    void given_answer_ok_when_get_all_vehicles_with_pagination_return_list() {
        when(vehicleRepository.findAll(page)).thenReturn(vehicles);
        ArrayList arrayList = new ArrayList();
        when(vehicleMapper.toDTOList(anyCollection()))
                .thenReturn(arrayList);

        assertThat(out.getAllVehicle(1, 1, VehicleSort.YEAR_ASC)).isEqualTo(arrayList);

    }

    @Test
    public void given_answer_ok_when_set_to_driver_new_vehicle() {

        when(vehicleRepository.findById(1L))
                .thenReturn(Optional.ofNullable(vehicle));


        when(driverRepository.findById(1L))
                .thenReturn(Optional.ofNullable(driver));


        out.setDriverToVehicle(1L, 1L);

        assertThat(vehicle.getDriver()).isEqualTo(driver);
        assertThat(driver.getVehicles()).contains(vehicle);


        verify(vehicleRepository, times(1)).save(vehicle);
        verify(driverRepository, times(1)).save(driver);

    }

}