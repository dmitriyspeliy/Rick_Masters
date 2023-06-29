package com.rick.masters.Rest.Api.repository;

import com.rick.masters.Rest.Api.domain.entity.Vehicle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Класс для транспортного средства
 */

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    /**
     * Достаем машину по VIN
     */
    Optional<Vehicle> findVehicleByVIN(String VIN);

}
