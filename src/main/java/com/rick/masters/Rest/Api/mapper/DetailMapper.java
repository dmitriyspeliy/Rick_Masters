package com.rick.masters.Rest.Api.mapper;

import com.rick.masters.Rest.Api.domain.entity.Detail;
import com.rick.masters.Rest.Api.domain.record.DetailRecord;
import org.mapstruct.Mapper;

import java.util.Collection;

/**
 * Маппер для {@link Detail}
 */

@Mapper(componentModel = "spring")
public interface DetailMapper {
    Detail toEntity(DetailRecord detailRecord);

    DetailRecord toDTO(Detail detail);

    Collection<Detail> toEntityList(Collection<DetailRecord> detailRecords);

    Collection<DetailRecord> toDTOList(Collection<Detail> details);
}
