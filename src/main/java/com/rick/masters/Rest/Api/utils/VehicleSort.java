package com.rick.masters.Rest.Api.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;

@Getter
@RequiredArgsConstructor
public enum VehicleSort {

    BRAND_ASC(Sort.by(Sort.Direction.ASC, "brand")),
    BRAND_DESC(Sort.by(Sort.Direction.DESC, "brand")),
    YEAR_ASC(Sort.by(Sort.Direction.ASC, "yearOfManufacture")),
    YEAR_DESC(Sort.by(Sort.Direction.DESC, "yearOfManufacture"));


    private final Sort sortValue;

}