package com.rick.masters.Rest.Api.mapper;

import com.rick.masters.Rest.Api.domain.entity.Driver;
import com.rick.masters.Rest.Api.domain.record.DriverRecord;
import org.mapstruct.Mapper;

import java.util.Collection;

/**
 *  Маппер для {@link Driver}
 */

@Mapper(componentModel = "spring")
public interface DriverMapper {
    Driver toEntity(DriverRecord driverRecord);

    DriverRecord toDTO(Driver driver);

    Collection<Driver> toEntityList(Collection<DriverRecord> driverRecords);

    Collection<DriverRecord> toDTOList(Collection<Driver> drivers);
}
