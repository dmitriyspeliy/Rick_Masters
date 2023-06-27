package com.rick.masters.Rest.Api.repository;

import com.rick.masters.Rest.Api.domain.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * репозиторий аккаунта
 */

public interface AccountRepository extends JpaRepository<Account, Long> {

}
