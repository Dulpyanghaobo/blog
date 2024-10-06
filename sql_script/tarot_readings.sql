create table tarot_readings
(
    id              bigint auto_increment
        primary key,
    user_id         bigint                              not null,
    tarot_spread_id bigint                              not null,
    question        text                                not null,
    reading_result  text                                not null,
    created_at      timestamp default CURRENT_TIMESTAMP null,
    constraint fk_tarot_reading_spread
        foreign key (tarot_spread_id) references tarot_spreads (id),
    constraint fk_tarot_reading_user
        foreign key (user_id) references users (id)
);

create index idx_tarot_spread_id
    on tarot_readings (tarot_spread_id);

create index idx_user_id
    on tarot_readings (user_id);

