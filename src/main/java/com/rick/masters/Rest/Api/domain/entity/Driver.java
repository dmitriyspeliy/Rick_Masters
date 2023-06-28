package com.rick.masters.Rest.Api.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

/**
 * Класс водитель
 */

@Getter
@Setter
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(callSuper = true)
public class Driver extends AbstractEntity {

    @Column(name = "name")
    String name;

    @Column(name = "passport_id")
    String passportId;

    @Column(name = "driving_licence_categories")
    String drivingLicenceCategories;

    @Column(name = "birthday_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate birthdayDate;

    @OneToMany(mappedBy = "driver", fetch = FetchType.LAZY)
    Set<Vehicle> vehicles;

    @OneToOne(mappedBy = "driver", fetch = FetchType.LAZY)
    Account account;

}
