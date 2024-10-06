create table tarot_shares
(
    id               bigint auto_increment
        primary key,
    tarot_reading_id bigint                              not null,
    shared_with      bigint                              null,
    share_message    text                                null,
    created_at       timestamp default CURRENT_TIMESTAMP null,
    constraint fk_tarot_shares_reading
        foreign key (tarot_reading_id) references tarot_readings (id)
);

