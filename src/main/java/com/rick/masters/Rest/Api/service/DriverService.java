package com.rick.masters.Rest.Api.service;

import com.rick.masters.Rest.Api.domain.record.DriverRecord;
import com.rick.masters.Rest.Api.utils.DriverSort;

import java.util.Collection;

/**
 * Сервис для водителя
 */
public interface DriverService {

    /**
     * Сохранить водителя в бд
     * @param driverRecord водитель
     */
    void saveDriver(DriverRecord driverRecord);

    /**
     * Сортировка и пагинация всех водителей
     *
     * @param offSet   номер страницы
     * @param limit    количество элементов на странице
     * @param sortedBy сортировка по полю
     * @return лист дто водителей
     */
    Collection<DriverRecord> getAllDrivers(Integer offSet, Integer limit, DriverSort sortedBy);

    /**
     * Поиск водителя по паспорту
     * @param passportId серия и номер паспорта
     */
    DriverRecord findDriverByPassportId(String passportId);

}
