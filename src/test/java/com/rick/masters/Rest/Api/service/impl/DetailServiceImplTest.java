package com.rick.masters.Rest.Api.service.impl;

import com.rick.masters.Rest.Api.domain.entity.Detail;
import com.rick.masters.Rest.Api.domain.entity.Vehicle;
import com.rick.masters.Rest.Api.domain.record.DetailRecord;
import com.rick.masters.Rest.Api.mapper.DetailMapper;
import com.rick.masters.Rest.Api.repository.DetailRepository;
import com.rick.masters.Rest.Api.repository.VehicleRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

/**
 * Тесты на сервис {@link com.rick.masters.Rest.Api.service.impl.DetailServiceImpl}
 */

@ExtendWith(MockitoExtension.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
class DetailServiceImplTest {

    @InjectMocks
    DetailServiceImpl out;
    @Mock
    DetailRepository detailRepository;
    @Mock
    VehicleRepository vehicleRepository;
    @Mock
    DetailMapper detailMapper;

    Detail detail;
    Detail detailNew;
    DetailRecord detailRecord;

    String serialNumber;
    String serialNumberNew;
    String VIN;
    Long vehicleId;
    Vehicle vehicle;

    @BeforeEach
    void setUp() {
        vehicle = new Vehicle();
        vehicleId = 5L;
        VIN = "11111111111111111";
        vehicle.setId(vehicleId);
        vehicle.setVIN(VIN);
        detail = new Detail();
        serialNumber = "2LDW90PWQV";
        serialNumberNew = "2LDW90PWQS";
        detailNew = new Detail();
        detailNew.setSerialNumber(serialNumberNew);
        detail.setSerialNumber(serialNumber);
        detailRecord = new DetailRecord();
        detailRecord.setSerialNumber(serialNumber);
    }

    @AfterEach
    void tearDown() {
        detail = null;
        detailRecord = null;
    }

    @Test
    void given_answer_ok_when_detail_save() {

        when(detailRepository.findDetailBySerialNumber(serialNumber))
                .thenReturn(Optional.empty());

        when(detailMapper.toEntity(detailRecord))
                .thenReturn(detail);

        out.saveDetail(detailRecord);


        verify(detailRepository, times(1)).save(detail);

    }

    @Test
    void given_answer_ok_when_save_detail_by_VIN() {
        when(detailRepository.findDetailBySerialNumber(serialNumber))
                .thenReturn(Optional.ofNullable(detail));
        when(detailMapper.toEntity(detailRecord))
                .thenReturn(detail);
        when(vehicleRepository.findVehicleByVIN(VIN))
                .thenReturn(Optional.ofNullable(vehicle));

        out.saveDetailByVIN(detailRecord, VIN);

        assertThat(detail.getVehicle()).isEqualTo(vehicle);

        verify(detailRepository, times(1)).save(detail);

    }

    @Test
    void given_answer_ok_when_save_detail_by_Id() {
        when(detailRepository.findDetailBySerialNumber(serialNumber))
                .thenReturn(Optional.ofNullable(detail));
        when(detailMapper.toEntity(detailRecord))
                .thenReturn(detail);
        when(vehicleRepository.findById(vehicleId))
                .thenReturn(Optional.ofNullable(vehicle));

        out.saveDetailByVehicleId(detailRecord, vehicleId);

        assertThat(detail.getVehicle()).isEqualTo(vehicle);

        verify(detailRepository, times(1)).save(detail);
    }

    @Test
    void given_answer_ok_when_change_detail() {
        when(detailRepository.findDetailBySerialNumber(serialNumber))
                .thenReturn(Optional.ofNullable(detail));
        when(detailRepository.findDetailBySerialNumber(serialNumberNew))
                .thenReturn(Optional.ofNullable(detailNew));

        out.changeDetail(serialNumber, serialNumberNew);

        assertThat(detail.getVehicle()).isEqualTo(null);
        assertThat(detailNew.getVehicle()).isEqualTo(detail.getVehicle());

        verify(detailRepository, times(1)).save(detail);
        verify(detailRepository, times(1)).save(detailNew);

    }
}