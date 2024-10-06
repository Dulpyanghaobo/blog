create table task
(
    id           bigint auto_increment
        primary key,
    objective_id bigint                            not null,
    title        varchar(255)                      not null,
    description  text                              null,
    status       varchar(20) default 'Not Started' null,
    user_id      bigint                            null,
    complexity   int         default 1             null,
    priority     int         default 1             null,
    importance   int         default 1             null,
    constraint fk_task_objective
        foreign key (objective_id) references objective (id),
    constraint fk_task_user
        foreign key (user_id) references users (id)
);

create index idx_objective_id
    on task (objective_id);

create index idx_user_id
    on task (user_id);

