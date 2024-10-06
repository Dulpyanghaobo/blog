create table mood
(
    id         bigint auto_increment
        primary key,
    user_id    bigint not null,
    mood_date  date   not null,
    mood_level int    not null,
    constraint user_id
        unique (user_id, mood_date),
    constraint fk_mood_user
        foreign key (user_id) references users (id)
);

