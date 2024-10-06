create table users
(
    id                      bigint auto_increment
        primary key,
    avatar                  varchar(255)                                           null,
    bio                     varchar(500)                                           null,
    disabled                bit                                                    not null,
    email                   varchar(255)                                           null,
    password                varchar(255)                                           null,
    phone_number            varchar(255)                                           null,
    registered_at           datetime(6)                                            not null,
    two_factor_auth_enabled bit                                                    not null,
    user_name               varchar(255)                                           not null,
    openid                  varchar(255)                                           null,
    nickname                varchar(100)                                           null,
    relationship_status     enum ('single', 'in_relationship', 'married', 'other') null,
    gender                  enum ('male', 'female', 'other')                       null,
    birthdate               date                                                   null,
    birth_time              time                                                   null,
    birth_place             varchar(255)                                           null,
    current_location        varchar(255)                                           null,
    timezone                varchar(50)                                            null,
    daylight_saving         bit                                                    null,
    zodiac_sign             varchar(20)                                            null,
    rising_sign             varchar(20)                                            null,
    moon_sign               varchar(20)                                            null,
    tarot_preference        varchar(255)                                           null,
    name                    varchar(255)                                           not null,
    birth_location_lat      decimal(9, 6)                                          null,
    birth_location_lng      decimal(9, 6)                                          null,
    constraint UK_6dotkott2kjsp8vw4d0m25fb7
        unique (email),
    constraint openid
        unique (openid),
    constraint chk_login_conditions
        check ((`email` is not null) or (`phone_number` is not null) or (`openid` is not null))
);

INSERT INTO blog.users (id, avatar, bio, disabled, email, password, phone_number, registered_at, two_factor_auth_enabled, user_name, openid, nickname, relationship_status, gender, birthdate, birth_time, birth_place, current_location, timezone, daylight_saving, zodiac_sign, rising_sign, moon_sign, tarot_preference, name, birth_location_lat, birth_location_lng) VALUES (37, null, null, false, '17843150270@163.com', '$2a$10$uYm3eRK6E3FDYNgjq7B6uOmLcoxAJ6XglExhB/2FwR68/ut8ZdWwG', null, '2024-10-01 05:23:52.340749', false, '1231111213', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '1231111213', null, null);
