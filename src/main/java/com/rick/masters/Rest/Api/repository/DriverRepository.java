package com.rick.masters.Rest.Api.repository;

import com.rick.masters.Rest.Api.domain.entity.Driver;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * репозиторий для водителя
 */
public interface DriverRepository extends JpaRepository<Driver, Long> {
}
