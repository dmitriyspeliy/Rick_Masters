package com.rick.masters.Rest.Api.domain.record;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Класс для переопределения
 */

public class AbstractRecord {
    @JsonIgnore
    private Long id;
}
