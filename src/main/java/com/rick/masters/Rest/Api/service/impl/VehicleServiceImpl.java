package com.rick.masters.Rest.Api.service.impl;

import com.rick.masters.Rest.Api.domain.entity.Driver;
import com.rick.masters.Rest.Api.domain.entity.Vehicle;
import com.rick.masters.Rest.Api.domain.record.VehicleRecord;
import com.rick.masters.Rest.Api.exception.ElemNotFound;
import com.rick.masters.Rest.Api.loger.FormLogInfo;
import com.rick.masters.Rest.Api.mapper.VehicleMapper;
import com.rick.masters.Rest.Api.repository.DriverRepository;
import com.rick.masters.Rest.Api.repository.VehicleRepository;
import com.rick.masters.Rest.Api.service.VehicleService;
import com.rick.masters.Rest.Api.utils.VehicleSort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Сервис для работы с транспортом
 */


@Service
@RequiredArgsConstructor
@Slf4j
public class VehicleServiceImpl implements VehicleService {


    private final VehicleRepository vehicleRepository;
    private final VehicleMapper vehicleMapper;
    private final DriverRepository driverRepository;


    /**
     * Сохранение автомобиля
     *
     * @param vehicleRecord автомобиль
     */
    @Override
    public void saveVehicle(VehicleRecord vehicleRecord) {

        log.info(FormLogInfo.getInfo("Сохраняем машину"));
        Vehicle vehicle = vehicleMapper.toEntity(vehicleRecord);

        vehicleRepository.save(vehicle);

    }

    /**
     * Установка владение автомобилем
     *
     * @param driverId  айди водителя
     * @param vehicleId айди транспортного средства
     */
    @Override
    public void setDriverToVehicle(Long driverId, Long vehicleId) {

        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new ElemNotFound("Нет такой машины с айди : " + vehicleId));

        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(() -> new ElemNotFound("Нет такоого водителя с айди : " + driverId));

        log.info(FormLogInfo.getInfo("Устанавливаем владение для машины с айди :" + vehicleId));

        vehicle.setDriver(driver);
        Set<Vehicle> vehicleSet = driver.getVehicles();
        vehicleSet.add(vehicle);
        driver.setVehicles(vehicleSet);

        driverRepository.save(driver);
        vehicleRepository.save(vehicle);

    }

    /**
     * Сортировка и пагинация всех автомобилей
     *
     * @param offSet   номер страницы
     * @param limit    количество элементов на странице
     * @param sortedBy сортировка по полю
     * @return лист дто автомобилей
     */
    @Override
    public Collection<VehicleRecord> getAllVehicle(Integer offSet, Integer limit, VehicleSort sortedBy) {
        log.info(FormLogInfo.getInfo("Получаем объект page с параметрами: \n" + "Номер страницы: " + offSet + "\n" + "Количество элементов на странице: " + limit + "\n" + "Сортировка по полю: " + sortedBy.getSortValue()));
        Pageable pageable = PageRequest.of(offSet, limit, sortedBy.getSortValue());
        Page<Vehicle> page = vehicleRepository.findVehicleWithSortAndPagination(pageable);
        return vehicleMapper.toDTOList(page.get().collect(Collectors.toList()));
    }
}
