create table user_tarot_access
(
    user_id           bigint                              not null,
    tarot_spread_id   bigint                              not null,
    access_granted_at timestamp default CURRENT_TIMESTAMP null,
    valid_until       datetime(6)                         null,
    remaining_uses    int                                 null,
    primary key (user_id, tarot_spread_id),
    constraint fk_user_tarot_access_spread
        foreign key (tarot_spread_id) references tarot_spreads (id),
    constraint fk_user_tarot_access_user
        foreign key (user_id) references users (id)
);

