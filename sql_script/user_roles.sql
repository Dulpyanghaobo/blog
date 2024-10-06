create table user_roles
(
    user_id bigint      not null,
    role    varchar(20) not null,
    role_id bigint      not null,
    constraint FKh8ciramu9cc9q3qcqiv4ue8a6
        foreign key (role_id) references roles (id),
    constraint FKhfh9dx7w3ubf1co1vdev94g3f
        foreign key (user_id) references users (id)
);

