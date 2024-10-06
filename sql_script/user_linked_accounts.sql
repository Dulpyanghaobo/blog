create table user_linked_accounts
(
    id           bigint auto_increment
        primary key,
    user_id      bigint      not null,
    account_type varchar(50) not null,
    linked_date  datetime(6) not null,
    constraint user_linked_accounts_ibfk_1
        foreign key (user_id) references users (id)
            on delete cascade
);

create index user_id
    on user_linked_accounts (user_id);

