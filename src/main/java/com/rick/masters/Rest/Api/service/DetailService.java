package com.rick.masters.Rest.Api.service;

import com.rick.masters.Rest.Api.domain.record.DetailRecord;

/**
 * Сервис для работы с деталями
 */

public interface DetailService {

    /**
     * Сохранение деталей в бд
     *
     * @param detailRecord деталь
     */
    void saveDetail(DetailRecord detailRecord);


    /**
     * Установка детали в автомобиль по VIN
     *
     * @param detailRecord деталь
     * @param VIN          идентификатор транспортного средства
     */
    void saveDetailByVIN(DetailRecord detailRecord, String VIN);

    /**
     * Установка детали в автомобиль по Id
     *
     * @param detailRecord деталь
     * @param vehicleId    идентификатор транспортного средства
     */
    void saveDetailByVehicleId(DetailRecord detailRecord, Long vehicleId);

    /**
     * Замена детали в машине
     *
     * @param serialNumberDetailOld серийный номер детали, которую надо заменить
     * @param serialNumberDetailNew серийный номер детали на замену
     */
    void changeDetail(String serialNumberDetailOld, String serialNumberDetailNew);


}
