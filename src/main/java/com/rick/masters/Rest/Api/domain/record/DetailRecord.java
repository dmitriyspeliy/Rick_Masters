package com.rick.masters.Rest.Api.domain.record;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rick.masters.Rest.Api.domain.entity.Detail;
import com.rick.masters.Rest.Api.domain.entity.Vehicle;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Dto {@link Detail}
 */

@Getter
@Setter
@RequiredArgsConstructor
@EqualsAndHashCode
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DetailRecord {

    @NotNull(message = "Не может быть null")
    @Size(min = 10, max = 10, message = "Серийный номер, например 2LDW90PWQV")
    @Pattern(regexp = "^[A-Z0-9]{10}", message = "Неправильный номер, например 2LDW90PWQV")
    String serialNumber;

    @JsonIgnore
    Vehicle vehicle;

}
