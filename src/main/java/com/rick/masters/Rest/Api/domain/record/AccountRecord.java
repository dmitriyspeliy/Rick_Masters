package com.rick.masters.Rest.Api.domain.record;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rick.masters.Rest.Api.domain.entity.Account;
import com.rick.masters.Rest.Api.domain.entity.Driver;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Dto {@link Account}
 */

@Getter
@Setter
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountRecord extends AbstractRecord implements Serializable {

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
