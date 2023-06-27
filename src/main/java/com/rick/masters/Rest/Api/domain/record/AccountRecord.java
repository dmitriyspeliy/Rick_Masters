package com.rick.masters.Rest.Api.domain.record;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rick.masters.Rest.Api.domain.entity.Account;
import com.rick.masters.Rest.Api.domain.entity.Driver;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Dto {@link Account}
 */

@Getter
@Setter
@RequiredArgsConstructor
@EqualsAndHashCode
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountRecord {

    @Min(value = 0, message = "меньше 0 значение не может быть")
    @NotNull(message = "Не может быть null")
    Long redValue;

    @Min(value = 0, message = "меньше 0 значение не может быть")
    @NotNull(message = "Не может быть null")
    Long greenValue;

    @Min(value = 0, message = "меньше 0 значение не может быть")
    @NotNull(message = "Не может быть null")
    Long blueValue;

    @JsonIgnore
    Driver driver;
}
