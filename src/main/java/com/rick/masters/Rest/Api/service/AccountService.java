package com.rick.masters.Rest.Api.service;

import com.rick.masters.Rest.Api.domain.record.AccountRecord;

/**
 * Сервис для работы с аккаунтом
 */


public interface AccountService {

    /**
     * Положить деньги
     *
     * @param color    цвет валюты
     * @param value    количество денег
     * @param driverId идентификатор водителя
     */
    void putMoney(String color, Long value, Long driverId);

    /**
     * Cписать деньги
     *
     * @param color    цвет валюты
     * @param value    количество денег
     * @param driverId идентификатор водителя
     */
    void writeOff(String color, Long value, Long driverId);


    /**
     * Посмотреть счет
     *
     * @param driverId идентификатор водителя
     */
    AccountRecord showAccount(Long driverId);

}
