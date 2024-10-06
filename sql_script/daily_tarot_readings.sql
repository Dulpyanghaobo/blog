create table daily_tarot_readings
(
    id              bigint auto_increment
        primary key,
    user_id         bigint                              not null,
    tarot_spread_id bigint                              not null,
    reading_date    date                                not null,
    reading_result  text                                not null,
    created_at      timestamp default CURRENT_TIMESTAMP null,
    constraint idx_user_reading_date
        unique (user_id, reading_date),
    constraint fk_daily_tarot_reading_spread
        foreign key (tarot_spread_id) references tarot_spreads (id),
    constraint fk_daily_tarot_reading_user
        foreign key (user_id) references users (id)
);

