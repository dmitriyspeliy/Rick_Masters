package com.rick.masters.Rest.Api.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;

@Getter
@RequiredArgsConstructor
public enum DriverSort {

    ID_ASC(Sort.by(Sort.Direction.ASC, "id")),
    ID_DESC(Sort.by(Sort.Direction.DESC, "id")),
    NAME_ASC(Sort.by(Sort.Direction.ASC, "name")),
    NAME_DESC(Sort.by(Sort.Direction.DESC, "name")),
    PASSPORT_ID_ASC(Sort.by(Sort.Direction.ASC, "passportId")),
    PASSPORT_ID_DESC(Sort.by(Sort.Direction.DESC, "passportId"));

    private final Sort sortValue;

}