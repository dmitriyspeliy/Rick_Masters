package com.rick.masters.Rest.Api.domain.record;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rick.masters.Rest.Api.domain.entity.Account;
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
 * Dto {@link Driver}
 */

@Getter
@Setter
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DriverRecord extends AbstractRecord implements Serializable {

    @NotNull(message = "Не может быть null")
    @Size(min = 1, max = 50, message = "ФИО")
    String name;

    @NotNull(message = "Не может быть null")
    @Pattern(regexp = "^[0-9]{10}", message = "Неправильный номер, например 1234111111")
    @Size(min = 10, max = 10, message = "Серия и номер паспорта, например 1234111111")
    String passportId;

    @NotNull(message = "Не может быть null")
    @Size(min = 1, max = 5, message = "Категория прав, например B")
    String drivingLicenceCategories;

    @NotNull(message = "Не может быть null")
    @Pattern(regexp = "^[0-9]{4}-[0-9]{2}-[0-9]{2}", message = "День рождения, например 1993-27-12")
    LocalDate birthdayDate;

    @JsonIgnore
    Set<Vehicle> vehicles;

    @JsonIgnore
    Account account;
}
