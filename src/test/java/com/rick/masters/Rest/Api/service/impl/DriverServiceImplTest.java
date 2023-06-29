package com.rick.masters.Rest.Api.service.impl;

import com.rick.masters.Rest.Api.domain.entity.Driver;
import com.rick.masters.Rest.Api.domain.record.DriverRecord;
import com.rick.masters.Rest.Api.mapper.DriverMapper;
import com.rick.masters.Rest.Api.repository.DriverRepository;
import com.rick.masters.Rest.Api.utils.DriverSort;
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
import java.util.Optional;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Mockito.*;

/**
 * Тесты на сервис {@link com.rick.masters.Rest.Api.service.impl.DriverServiceImpl}
 */

@ExtendWith(MockitoExtension.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
class DriverServiceImplTest {

    @InjectMocks
    DriverServiceImpl out;
    @Mock
    DriverRepository driverRepository;
    @Mock
    DriverMapper driverMapper;

    DriverRecord driverRecord;
    Driver driver;
    Pageable page;

    private Page<Driver> driverPage;

    @BeforeEach
    void setUp() {
        page = PageRequest.of(1, 1, DriverSort.ID_ASC.getSortValue());
        driver = new Driver();
        driverRecord = new DriverRecord();
        driverPage = new PageImpl<>(new ArrayList<>(), page, 1L);
    }

    @AfterEach
    void tearDown() {
        driver = null;
        driverRecord = null;
    }


    @Test
    void given_answer_ok_when_save_driver() {
        when(driverMapper.toEntity(driverRecord)).thenReturn(driver);

        out.saveDriver(driverRecord);

        verify(driverRepository, times(1)).save(driver);
    }

    @Test
    void given_answer_ok_when_get_all_drivers_with_pagination_return_list() {
        when(driverRepository.findAll(page)).thenReturn(driverPage);
        ArrayList arrayList = new ArrayList();
        when(driverMapper.toDTOList(anyCollection()))
                .thenReturn(arrayList);

        assertThat(out.getAllDrivers(1, 1, DriverSort.ID_ASC)).isEqualTo(arrayList);

    }

    @Test
    void given_answer_ok_when_find_driver_by_passport_id_return_dto() {
        when(driverRepository.findByPassportId("1111111111")).thenReturn(Optional.ofNullable(driver));
        when(driverMapper.toDTO(driver)).thenReturn(driverRecord);

        assertThat(out.findDriverByPassportId("1111111111")).isEqualTo(driverRecord);

    }
}