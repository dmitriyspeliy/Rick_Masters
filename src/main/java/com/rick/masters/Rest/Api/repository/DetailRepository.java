package com.rick.masters.Rest.Api.repository;

import com.rick.masters.Rest.Api.domain.entity.Detail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * репозиторий детали
 */

public interface DetailRepository extends JpaRepository<Detail, Long> {

    /**
     * Ищем деталь по серийнику
     */
    Optional<Detail> findDetailBySerialNumber(String serialNumber);
}
