package com.rick.masters.Rest.Api.service;

import com.rick.masters.Rest.Api.domain.record.VehicleRecord;
import com.rick.masters.Rest.Api.utils.VehicleSort;

import java.util.List;

/**
 * Сервис для автомобиля
 */

public interface VehicleService {

    /**
     * Сохранение автомобиля
     *
     * @param vehicleRecord автомобиль
     */
    void saveVehicle(VehicleRecord vehicleRecord);

    /**
     * Установка владение автомобилем
     *
     * @param driverId  айди водителя
     * @param vehicleId айди транспортного средства
     */
    void setDriverToVehicle(Long driverId, Long vehicleId);

    /**
     * Сортировка и пагинация всех автомобилей
     *
     * @param offSet   номер страницы
     * @param limit    количество элементов на странице
     * @param sortedBy сортировка по полю
     * @return лист дто автомобилей
     */
    List<VehicleRecord> getAllVehicle(Integer offSet, Integer limit, VehicleSort sortedBy);
}
