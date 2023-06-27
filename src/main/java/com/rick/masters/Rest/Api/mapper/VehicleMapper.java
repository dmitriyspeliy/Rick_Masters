package com.rick.masters.Rest.Api.mapper;

import com.rick.masters.Rest.Api.domain.entity.Vehicle;
import com.rick.masters.Rest.Api.domain.record.VehicleRecord;
import org.mapstruct.Mapper;

import java.util.Collection;

/**
 *  Маппер для {@link Vehicle}
 */

@Mapper(componentModel = "spring")
public interface VehicleMapper {
    Vehicle toEntity(VehicleRecord vehicleRecord);

    VehicleRecord toDTO(Vehicle vehicle);

    Collection<Vehicle> toEntityList(Collection<VehicleRecord> vehicleRecords);

    Collection<VehicleRecord> toDTOList(Collection<Vehicle> vehicles);
}
