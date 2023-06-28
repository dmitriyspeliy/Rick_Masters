package com.rick.masters.Rest.Api.domain.entity;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.text.DateFormat;
import java.util.Set;

/**
 * Класс транспортного средства
 */

@Setter
@Getter
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(callSuper = true)
public class Vehicle extends AbstractEntity {

    @Column(name = "VIN")
    String VIN;

    @Column(name = "government_number")
    String governmentNumber;

    @Column(name = "manufacturer")
    String manufacturer;

    @Column(name = "brand")
    String brand;

    @Column(name = "year_of_manufacture")
    DateFormat yearOfManufacture;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "driver_id")
    Driver driver;

    @OneToMany(mappedBy = "vehicle", fetch = FetchType.LAZY)
    Set<Detail> details;

}
