package com.rick.masters.Rest.Api.service.impl;

import com.rick.masters.Rest.Api.domain.entity.Detail;
import com.rick.masters.Rest.Api.domain.entity.Vehicle;
import com.rick.masters.Rest.Api.domain.record.DetailRecord;
import com.rick.masters.Rest.Api.exception.ElemNotFound;
import com.rick.masters.Rest.Api.loger.FormLogInfo;
import com.rick.masters.Rest.Api.mapper.DetailMapper;
import com.rick.masters.Rest.Api.repository.DetailRepository;
import com.rick.masters.Rest.Api.repository.VehicleRepository;
import com.rick.masters.Rest.Api.service.DetailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;


/**
 * Сервис для работы с деталями
 */


@Service
@RequiredArgsConstructor
@Slf4j
public class DetailServiceImpl implements DetailService {

    private final DetailRepository detailRepository;

    private final VehicleRepository vehicleRepository;

    private final DetailMapper detailMapper;


    /**
     * Сохранение деталей в бд
     *
     * @param detailRecord деталь
     */
    @Override
    public void saveDetail(DetailRecord detailRecord) {

        Optional<Detail> check = detailRepository.findDetailBySerialNumber(detailRecord.getSerialNumber());

        if (check.isEmpty()) {
            log.info(FormLogInfo.getInfo("Сохраняем новую деталь"));

            Detail detail = detailMapper.toEntity(detailRecord);

            detailRepository.save(detail);
        }

    }

    /**
     * Установка детали в автомобиль по VIN
     *
     * @param detailRecord деталь
     * @param VIN          идентификатор транспортного средства
     */
    @Override
    public void saveDetailByVIN(DetailRecord detailRecord, String VIN) {

        Optional<Detail> check = detailRepository.findDetailBySerialNumber(detailRecord.getSerialNumber());

        if (check.isPresent()) {
            Detail detail = detailMapper.toEntity(detailRecord);

            Vehicle vehicle = vehicleRepository.findVehicleByVIN(VIN)
                    .orElseThrow(() -> new ElemNotFound("Нет машины с VIN : " + VIN));

            log.info(FormLogInfo.getInfo("Устанавливаем деталь в автомобиль"));

            detail.setVehicle(vehicle);
            detail.setId(check.get().getId());

            detailRepository.save(detail);
        }

    }

    /**
     * Установка детали в автомобиль по Id
     *
     * @param detailRecord деталь
     * @param vehicleId    идентификатор транспортного средства
     */
    @Override
    public void saveDetailByVehicleId(DetailRecord detailRecord, Long vehicleId) {

        Optional<Detail> check = detailRepository.findDetailBySerialNumber(detailRecord.getSerialNumber());

        if (check.isPresent()) {
            Detail detail = detailMapper.toEntity(detailRecord);

            Vehicle vehicle = vehicleRepository.findById(vehicleId)
                    .orElseThrow(() -> new ElemNotFound("Нет машины с Id : " + vehicleId));

            log.info(FormLogInfo.getInfo("Устанавливаем деталь в автомобиль"));

            detail.setVehicle(vehicle);
            detail.setId(check.get().getId());
            detailRepository.save(detail);
        }

    }

    /**
     * Замена детали в машине
     *
     * @param serialNumberDetailOld серийный номер детали, которую надо заменить
     * @param serialNumberDetailNew серийный номер детали на замену
     */
    @Override
    public void changeDetail(String serialNumberDetailOld, String serialNumberDetailNew) {

        Detail detailOld = detailRepository.findDetailBySerialNumber(serialNumberDetailOld)
                .orElseThrow(() -> new ElemNotFound("Нет детали с Id : " + serialNumberDetailOld));

        Detail detailNew = detailRepository.findDetailBySerialNumber(serialNumberDetailNew)
                .orElseThrow(() -> new ElemNotFound("Нет детали с Id : " + serialNumberDetailNew));

        log.info(FormLogInfo.getInfo("Замена детали в машине"));

        detailNew.setVehicle(detailOld.getVehicle());
        detailOld.setVehicle(null);

        detailRepository.save(detailNew);
        detailRepository.save(detailOld);

    }
}
