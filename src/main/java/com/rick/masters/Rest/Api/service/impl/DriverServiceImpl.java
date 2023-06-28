package com.rick.masters.Rest.Api.service.impl;

import com.rick.masters.Rest.Api.domain.entity.Driver;
import com.rick.masters.Rest.Api.domain.record.DriverRecord;
import com.rick.masters.Rest.Api.exception.ElemNotFound;
import com.rick.masters.Rest.Api.loger.FormLogInfo;
import com.rick.masters.Rest.Api.mapper.DriverMapper;
import com.rick.masters.Rest.Api.repository.DriverRepository;
import com.rick.masters.Rest.Api.service.DriverService;
import com.rick.masters.Rest.Api.utils.DriverSort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Сервис для водителя
 */


@Service
@RequiredArgsConstructor
@Slf4j
public class DriverServiceImpl implements DriverService {

    private final DriverRepository driverRepository;
    private final DriverMapper driverMapper;


    /**
     * Сохранить водителя в бд
     *
     * @param driverRecord водитель
     */
    @Override
    public void saveDriver(DriverRecord driverRecord) {

        log.info(FormLogInfo.getInfo("Сохраняем водителя"));

        Driver driver = driverMapper.toEntity(driverRecord);

        driverRepository.save(driver);

    }

    /**
     * Сортировка и пагинация всех водителей
     *
     * @param offSet   номер страницы
     * @param limit    количество элементов на странице
     * @param sortedBy сортировка по полю
     * @return лист дто водителей
     */
    @Override
    public Collection<DriverRecord> getAllDrivers(Integer offSet, Integer limit, DriverSort sortedBy) {
        log.info(FormLogInfo.getInfo("Получаем объект page с параметрами: \n" + "Номер страницы: " + offSet + "\n" + "Количество элементов на странице: " + limit + "\n" + "Сортировка по полю: " + sortedBy.getSortValue()));
        Pageable pageable = PageRequest.of(offSet, limit, sortedBy.getSortValue());
        Page<Driver> page = driverRepository.findDriversWithSortAndPagination(pageable);
        return driverMapper.toDTOList(page.get().collect(Collectors.toList()));
    }

    /**
     * Поиск водителя по паспорту
     *
     * @param passportId серия и номер паспорта
     */
    @Override
    public DriverRecord findDriverByPassportId(String passportId) {
        log.info(FormLogInfo.getInfo("Ищем водителя по номеру паспорта : " + passportId));
        return driverMapper.toDTO(driverRepository.findByPassportId(passportId).orElseThrow(() -> new ElemNotFound("Нет водителя с номер паспорта : " + passportId)));
    }
}
