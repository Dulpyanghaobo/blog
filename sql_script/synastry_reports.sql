create table synastry_reports
(
    id                bigint auto_increment
        primary key,
    user_id           bigint                              not null,
    partner_id        bigint                              not null,
    relationship_type varchar(255)                        null,
    synastry_data     json                                not null,
    report_result     text                                not null,
    created_at        timestamp default CURRENT_TIMESTAMP not null,
    constraint fk_synastry_report_partner
        foreign key (partner_id) references users (id),
    constraint fk_synastry_report_user
        foreign key (user_id) references users (id)
);

