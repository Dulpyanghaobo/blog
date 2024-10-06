create table user_interests
(
    user_id   bigint       not null,
    interests varchar(255) null,
    constraint FKdv9fflrh61wyuujfwx2yn1tb4
        foreign key (user_id) references users (id)
);

