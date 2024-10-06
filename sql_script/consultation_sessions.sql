create table consultation_sessions
(
    id                  bigint auto_increment
        primary key,
    user_id             bigint                                                not null,
    question            text                                                  not null,
    consultation_result text                                                  not null,
    session_type        enum ('astrology', 'tarot', 'relationship', 'career') not null,
    created_at          timestamp default CURRENT_TIMESTAMP                   not null,
    constraint fk_consultation_user
        foreign key (user_id) references users (id)
            on delete cascade
);

