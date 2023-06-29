package com.rick.masters.Rest.Api.domain.entity;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

/**
 * Класс для аккаунта
 */

@Getter
@Setter
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Account extends AbstractEntity {

    @Column(name = "red_value")
    Long redValue;

    @Column(name = "green_value")
    Long greenValue;

    @Column(name = "blue_value")
    Long blueValue;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "driver_id", referencedColumnName = "id")
    Driver driver;


}
