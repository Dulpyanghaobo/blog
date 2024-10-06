create table user_history_reports
(
    id          bigint auto_increment
        primary key,
    user_id     bigint                                  not null,
    report_type enum ('tarot', 'astrology', 'synastry') not null,
    report_id   bigint                                  not null,
    created_at  timestamp default CURRENT_TIMESTAMP     not null,
    constraint fk_user_history_user
        foreign key (user_id) references users (id)
);

