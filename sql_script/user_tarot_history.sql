create table user_tarot_history
(
    id               bigint auto_increment
        primary key,
    user_id          bigint                              not null,
    tarot_reading_id bigint                              not null,
    viewed_at        timestamp default CURRENT_TIMESTAMP not null,
    constraint fk_user_tarot_history_reading
        foreign key (tarot_reading_id) references tarot_readings (id),
    constraint fk_user_tarot_history_user
        foreign key (user_id) references users (id)
);

