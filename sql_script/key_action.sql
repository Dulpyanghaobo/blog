create table key_action
(
    id           bigint auto_increment
        primary key,
    objective_id bigint                            not null,
    title        varchar(255)                      not null,
    description  text                              null,
    start_date   datetime(6)                       null,
    end_date     datetime(6)                       null,
    progress     double      default 0             null,
    status       varchar(20) default 'Not Started' null,
    user_id      bigint                            null,
    complexity   int         default 1             null,
    priority     int         default 1             null,
    importance   int         default 1             null,
    constraint fk_key_action_objective
        foreign key (objective_id) references objective (id),
    constraint fk_key_action_user
        foreign key (user_id) references users (id)
);

create index idx_objective_id
    on key_action (objective_id);

create index idx_user_id
    on key_action (user_id);

