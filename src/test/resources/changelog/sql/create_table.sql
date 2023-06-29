create table if not exists driver
(
    id                         bigint generated always as identity primary key,
    name                       varchar(50) not null,
    passport_id                varchar(10) not null,
    driving_licence_categories varchar(5)  not null,
    birthday_date              date   not null
);

comment on table driver is 'Таблица водитель';
comment on column driver.id is 'Идентификатор водителя';
comment on column driver.passport_id is 'Номер и серия паспорта';
comment on column driver.driving_licence_categories is 'Категория водительского удостоверения';
comment on column driver.birthday_date is 'Дата дня рождения';

create unique index passport_id on driver (passport_id);

comment on index passport_id is 'Индекс для поиска по номеру и серии паспорта';

create table if not exists vehicle
(
    id                  bigint generated always as identity primary key,
    driver_id           bigint      default 0,
    VIN                 varchar(17) not null,
    government_number   varchar(10) not null,
    manufacturer        varchar(50) not null,
    brand               varchar(30) not null,
    year_of_manufacture date   not null,
    constraint fk_driver_id foreign key (driver_id) references driver (id) on delete set default

);

comment on table vehicle is 'Таблица транспортное средство';
comment on column vehicle.id is 'Идентификатор транспортного средства';
comment on column vehicle.driver_id is 'Внешний ключ на таблицу водитель';
comment on column vehicle.VIN is 'Шифр транспортного средства, состоящий из семнадцати символов';
comment on column vehicle.government_number is 'Государственный номер';
comment on column vehicle.manufacturer is 'Изготовитель';
comment on column vehicle.brand is 'Бренд';
comment on column vehicle.year_of_manufacture is 'Дата выпуска';

create unique index VIN on vehicle (VIN);

comment on index VIN is 'Индекс для поиска по шифру';

create table if not exists detail
(
    id            bigint generated always as identity primary key,
    vehicle_id    bigint      default 0,
    serial_number varchar(10) not null,
    constraint fk_vehicle_id foreign key (vehicle_id) references vehicle (id) on delete set default

);

comment on table detail is 'Таблица детали';
comment on column detail.id is 'Идентификатор детали';
comment on column detail.serial_number is 'Серийный номер';
comment on column detail.vehicle_id is 'Внешний ключ на таблицу транспорт';

create table if not exists account
(
    id          bigint generated always as identity primary key,
    driver_id   bigint not null,
    red_value   bigint not null,
    green_value bigint not null,
    blue_value  bigint not null,
    constraint fk_driver_id foreign key (driver_id) references driver (id) on delete cascade
);

comment on table account is 'Таблица счета  водителя';
comment on column account.driver_id is 'Внешний ключ на таблицу водитель';
comment on column account.red_value is 'Красная валюта';
comment on column account.green_value is 'Зеленая валюта';
comment on column account.blue_value is 'Синяя валюта';
