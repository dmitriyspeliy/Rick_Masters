package com.rick.masters.Rest.Api.cronjob;


import com.rick.masters.Rest.Api.domain.entity.Driver;
import com.rick.masters.Rest.Api.repository.DriverRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Вывод в логи всех, у кого день рождения
 */

@Service
@EnableScheduling
@Slf4j
@RequiredArgsConstructor
public class NotificationForBirthDayDriver {

    private final DriverRepository driverRepository;

    @Scheduled(cron = "0 1 1 * * ?")
    private void notification() {

        LocalDate localDate = LocalDate.now();

        List<Driver> driverList = new ArrayList<>();
        driverRepository.findAll().forEach(driverList::add);
        List<Driver> driverToDayBithDayList = driverList.stream().filter(x -> x.getBirthdayDate().isEqual(localDate)).collect(Collectors.toList());
        driverToDayBithDayList.forEach(x -> log.info("Сегодня день рождения у " + x.getName()));
    }


}
