package com.rick.masters.Rest.Api.repository;

import com.rick.masters.Rest.Api.domain.entity.Driver;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

/**
 * репозиторий для водителя
 */
public interface DriverRepository extends PagingAndSortingRepository<Driver, Long> {

    Page<Driver> findDriversWithSortAndPagination(Pageable pageable);

    Optional<Driver> findByPassportId(String passportId);

}
