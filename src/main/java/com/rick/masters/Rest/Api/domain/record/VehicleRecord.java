package com.rick.masters.Rest.Api.domain.record;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rick.masters.Rest.Api.domain.entity.Detail;
import com.rick.masters.Rest.Api.domain.entity.Driver;
import com.rick.masters.Rest.Api.domain.entity.Vehicle;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

/**
 * Dto {@link Vehicle}
 */

@Getter
@Setter
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VehicleRecord extends AbstractRecord implements Serializable {

    @NotNull(message = "Не может быть null")
    @Pattern(regexp = "^[A-Z0-9]{17}", message = "Идентификационный номер транспортного средства")
    String VIN;

    @NotNull(message = "Не может быть null")
    @Size(min = 1, max = 10, message = "Государственный знак")
    String governmentNumber;

    @NotNull(message = "Не может быть null")
    @Size(min = 1, max = 20, message = "Производитель")
    String manufacturer;

    @NotNull(message = "Не может быть null")
    @Size(min = 1, max = 20, message = "Бренд")
    String brand;

    @NotNull(message = "Не может быть null")
    LocalDate yearOfManufacture;

    @JsonIgnore
    Driver driver;

    @JsonIgnore
    Set<Detail> details;
}
