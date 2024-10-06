create table astrology_readings
(
    id             bigint auto_increment
        primary key,
    user_id        bigint                                     not null,
    reading_type   enum ('natal', 'transits', 'progressions') not null,
    chart_data     json                                       not null,
    reading_result text                                       not null,
    created_at     timestamp default CURRENT_TIMESTAMP        not null,
    constraint fk_astrology_reading_user
        foreign key (user_id) references users (id)
            on delete cascade
);

