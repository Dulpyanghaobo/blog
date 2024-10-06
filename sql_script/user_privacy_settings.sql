create table user_privacy_settings
(
    id              bigint auto_increment
        primary key,
    user_id         bigint not null,
    share_birthdate bit    not null,
    share_gender    bit    not null,
    share_location  bit    not null,
    constraint user_privacy_settings_ibfk_1
        foreign key (user_id) references users (id)
            on delete cascade
);

create index user_id
    on user_privacy_settings (user_id);

