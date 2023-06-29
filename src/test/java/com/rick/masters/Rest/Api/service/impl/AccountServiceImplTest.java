package com.rick.masters.Rest.Api.service.impl;

import com.rick.masters.Rest.Api.domain.entity.Account;
import com.rick.masters.Rest.Api.domain.entity.Driver;
import com.rick.masters.Rest.Api.domain.record.AccountRecord;
import com.rick.masters.Rest.Api.exception.ElemNotFound;
import com.rick.masters.Rest.Api.mapper.AccountMapper;
import com.rick.masters.Rest.Api.repository.AccountRepository;
import com.rick.masters.Rest.Api.repository.DriverRepository;
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
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


/**
 * Тесты на сервис {@link com.rick.masters.Rest.Api.service.impl.AccountServiceImpl}
 */

@ExtendWith(MockitoExtension.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
class AccountServiceImplTest {

    @InjectMocks
    AccountServiceImpl out;
    @Mock
    AccountRepository accountRepository;
    @Mock
    DriverRepository driverRepository;
    @Mock
    AccountMapper accountMapper;

    Driver driver;
    Driver driverWithNullAccount;
    Account account;

    @BeforeEach
    void init() {
        driver = new Driver();
        account = new Account();
        account.setBlueValue(0L);
        account.setRedValue(0L);
        account.setGreenValue(0L);
        driver.setAccount(account);
        driverWithNullAccount = new Driver();
    }

    @AfterEach
    void clearAll() {
        driver = null;
        driverWithNullAccount = null;
        account = null;
    }

    @Test
    void given_answer_ok_when_put_money_and_save_return_void() {
        when(driverRepository.findById(anyLong()))
                .thenReturn(Optional.ofNullable(driver));


        out.putMoney("Красный", 20L, anyLong());

        assertThat(account.getRedValue()).isEqualTo(20L);
        assertThat(account.getBlueValue()).isEqualTo(30L);
        assertThat(account.getGreenValue()).isEqualTo(50L);
        verify(accountRepository).save(account);
    }

    @Test
    void given_exception_when_put_money_with_less_0_value_return_void() {

        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(
                () -> out.putMoney("Красный", -10L, 1L));

    }

    @Test
    void given_answer_ok_when_writeOff_and_save_return_void() {
        when(driverRepository.findById(anyLong()))
                .thenReturn(Optional.ofNullable(driver));

        account.setRedValue(20L);
        account.setBlueValue(30L);
        account.setGreenValue(50L);

        out.writeOff("Красный", 20L, anyLong());

        assertThat(account.getRedValue()).isEqualTo(0);
        assertThat(account.getBlueValue()).isEqualTo(0);
        assertThat(account.getGreenValue()).isEqualTo(0);
        verify(accountRepository).save(account);
    }

    @Test
    void given_exception_when_writeOff_with_less_0_value_return_void() {

        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(
                () -> out.writeOff("Красный", -10L, 1L));

    }

    @Test
    void given_exception_when_writeOff_with_more_than_in_value_account_value_return_void() {

        when(driverRepository.findById(anyLong()))
                .thenReturn(Optional.ofNullable(driver));

        account.setRedValue(0L);
        account.setBlueValue(0L);
        account.setGreenValue(0L);

        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(
                () -> out.writeOff("Красный", 10L, 1L));

    }

    @Test
    void given_exception_when_account_null() {

        when(driverRepository.findById(anyLong()))
                .thenReturn(Optional.ofNullable(driverWithNullAccount));

        assertThatExceptionOfType(ElemNotFound.class).isThrownBy(
                () -> out.showAccount(1L));

    }

    @Test
    void given_account() {

        when(driverRepository.findById(anyLong()))
                .thenReturn(Optional.ofNullable(driver));
        AccountRecord accountRecord = new AccountRecord();
        when(accountMapper.toDTO(account))
                .thenReturn(accountRecord);

        assertThat(out.showAccount(1L)).isEqualTo(accountRecord);

    }
}