create table verification_tokens
(
    id          bigint auto_increment
        primary key,
    expiry_date datetime(6)  not null,
    token       varchar(255) not null,
    user_id     bigint       null,
    token_type  tinyint      null,
    email       varchar(255) null,
    constraint UK_6q9nsb665s9f8qajm3j07kd1e
        unique (token),
    constraint uk_tokens_type_email
        unique (token_type, email),
    constraint FK54y8mqsnq1rtyf581sfmrbp4f
        foreign key (user_id) references users (id),
    constraint chk_user_id_or_email
        check ((`user_id` is not null) or (`email` is not null))
);

