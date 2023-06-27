package com.rick.masters.Rest.Api.repository;

import com.rick.masters.Rest.Api.domain.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * репозиторий детали
 */

public interface DetailRepository extends JpaRepository<Account, Long> {

}
