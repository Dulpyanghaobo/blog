create table roles
(
    id   bigint auto_increment
        primary key,
    name enum ('ROLE_ADMIN', 'ROLE_USER') null
);

