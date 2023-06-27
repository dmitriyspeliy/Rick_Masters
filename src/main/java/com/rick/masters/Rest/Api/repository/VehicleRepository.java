package com.rick.masters.Rest.Api.repository;

import com.rick.masters.Rest.Api.domain.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Класс для транспортного средства
 */

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
}
