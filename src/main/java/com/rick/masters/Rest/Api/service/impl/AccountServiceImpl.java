package com.rick.masters.Rest.Api.service.impl;


import com.rick.masters.Rest.Api.domain.entity.Account;
import com.rick.masters.Rest.Api.domain.entity.Driver;
import com.rick.masters.Rest.Api.domain.record.AccountRecord;
import com.rick.masters.Rest.Api.exception.ElemNotFound;
import com.rick.masters.Rest.Api.loger.FormLogInfo;
import com.rick.masters.Rest.Api.mapper.AccountMapper;
import com.rick.masters.Rest.Api.repository.AccountRepository;
import com.rick.masters.Rest.Api.repository.DriverRepository;
import com.rick.masters.Rest.Api.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Сервис для работы с аккаунтом
 */


@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final DriverRepository driverRepository;
    private final AccountMapper accountMapper;

    /**
     * Положить деньги
     *
     * @param color    цвет валюты
     * @param value    количество денег
     * @param driverId идентификатор водителя
     */
    @Override
    public void putMoney(String color, Long value, Long driverId) {

        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(() -> new ElemNotFound("Нет водителя с айди: " + driverId));

        Account account = driver.getAccount();

        if (account != null) {
            log.info(FormLogInfo.getInfo("Кладем деньги на счет"));
            selectorForMoneyPlus(account, color, value);
            accountRepository.save(account);
        } else {
            log.info(FormLogInfo.getInfo("Создаем аккаунт и кладем деньги на счет"));
            Account newAccount = new Account();
            newAccount.setDriver(driver);
            selectorForMoneyPlus(newAccount, color, value);
            accountRepository.save(newAccount);
        }
    }

    /**
     * Cписать деньги
     *
     * @param color    цвет валюты
     * @param value    количество денег
     * @param driverId идентификатор водителя
     */
    @Override
    public void writeOff(String color, Long value, Long driverId) {

        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(() -> new ElemNotFound("Нет водителя с айди: " + driverId));

        Account account = driver.getAccount();

        if (account != null) {
            log.info(FormLogInfo.getInfo("Снимаем деньги со счета"));
            selectorForMoneyMinus(account, color, value);
            accountRepository.save(account);
        } else {
            log.info(FormLogInfo.getInfo("Создаем аккаунт и снимаем деньги со счета"));
            Account newAccount = new Account();
            newAccount.setDriver(driver);
            selectorForMoneyMinus(newAccount, color, value);
            accountRepository.save(newAccount);
        }
    }

    /**
     * Посмотреть счет
     *
     * @param driverId идентификатор водителя
     */
    public AccountRecord showAccount(Long driverId) {

        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(() -> new ElemNotFound("Нет водителя с айди: " + driverId));

        if (driver.getAccount() == null) {
            throw new ElemNotFound("У вас еще нет аккаунта");
        }

        return accountMapper.toDTO(driver.getAccount());
    }


    //метод для подсчета количества валют при пополнение счета
    private void selectorForMoneyPlus(Account newAccount, String color, Long value) {

        switch (color) {
            case "красный":
                newAccount.setRedValue(newAccount.getRedValue() + value);
                newAccount.setGreenValue((long) (newAccount.getGreenValue() + (value * 2.5)));
                newAccount.setBlueValue((long) (newAccount.getBlueValue() + (value * 1.5)));
                break;
            case "зеленый":
                newAccount.setGreenValue((newAccount.getGreenValue() + value));
                newAccount.setRedValue((long) (newAccount.getRedValue() + (value / 2.5)));
                newAccount.setBlueValue((long) (newAccount.getBlueValue() + (value * 0.6)));
                break;
            case "голубой":
                newAccount.setBlueValue(newAccount.getBlueValue() + value);
                newAccount.setRedValue((long) (newAccount.getRedValue() + (value / 1.5)));
                newAccount.setGreenValue((long) (newAccount.getGreenValue() + (value * 1.6)));
                break;
        }

    }

    //метод для подсчета количества валют при снятия денег со счета
    private void selectorForMoneyMinus(Account newAccount, String color, Long value) {
        long redValue;
        long greenValue;
        long blueValue;

        switch (color) {
            case "красный":
                redValue = newAccount.getRedValue() - value;
                greenValue = (long) (newAccount.getGreenValue() - (value * 2.5));
                blueValue = (long) (newAccount.getBlueValue() - (value * 1.5));
                if (redValue < 0 || greenValue < 0 || blueValue < 0) {
                    throw new IllegalArgumentException("Баланс не может быть отрицательный\n " +
                            "Красная валюта: " + redValue + "\n" +
                            "Зеленая валюта: " + greenValue + "\n" +
                            "Синяя валюта: " + blueValue + "\n");
                }
                newAccount.setRedValue(redValue);
                newAccount.setGreenValue(greenValue);
                newAccount.setBlueValue(blueValue);
                break;
            case "зеленый":
                redValue = (long) (newAccount.getRedValue() + (value / 2.5));
                greenValue = newAccount.getGreenValue() + value;
                blueValue = (long) (newAccount.getBlueValue() + (value * 0.6));
                if (redValue < 0 || greenValue < 0 || blueValue < 0) {
                    throw new IllegalArgumentException("Баланс не может быть отрицательный\n " +
                            "Красная валюта: " + redValue + "\n" +
                            "Зеленая валюта: " + greenValue + "\n" +
                            "Синяя валюта: " + blueValue + "\n");
                }
                newAccount.setRedValue(redValue);
                newAccount.setGreenValue(greenValue);
                newAccount.setBlueValue(blueValue);
                break;
            case "голубой":
                redValue = (long) (newAccount.getRedValue() + (value / 1.5));
                greenValue = (long) (newAccount.getGreenValue() + (value * 1.6));
                blueValue = newAccount.getBlueValue() + value;
                if (redValue < 0 || greenValue < 0 || blueValue < 0) {
                    throw new IllegalArgumentException("Баланс не может быть отрицательный\n " +
                            "Красная валюта: " + redValue + "\n" +
                            "Зеленая валюта: " + greenValue + "\n" +
                            "Синяя валюта: " + blueValue + "\n");
                }
                newAccount.setRedValue(redValue);
                newAccount.setGreenValue(greenValue);
                newAccount.setBlueValue(blueValue);
                break;
        }

    }
}
