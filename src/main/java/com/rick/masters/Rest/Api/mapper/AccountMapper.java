package com.rick.masters.Rest.Api.mapper;

import com.rick.masters.Rest.Api.domain.entity.Account;
import com.rick.masters.Rest.Api.domain.record.AccountRecord;
import org.mapstruct.Mapper;

import java.util.Collection;

/**
 *  Маппер для {@link Account}
 */

@Mapper(componentModel = "spring")
public interface AccountMapper {

    Account toEntity(AccountRecord accountRecord);

    AccountRecord toDTO(Account account);

    Collection<Account> toEntityList(Collection<AccountRecord> accountRecords);

    Collection<AccountRecord> toDTOList(Collection<Account> accounts);

}
