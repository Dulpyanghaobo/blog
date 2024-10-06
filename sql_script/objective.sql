create table objective
(
    id          bigint auto_increment
        primary key,
    title       varchar(255)                      not null,
    description text                              null,
    start_date  datetime(6)                       null,
    end_date    datetime(6)                       null,
    progress    double      default 0             null,
    status      varchar(20) default 'Not Started' null,
    user_id     bigint                            null,
    complexity  int         default 1             null,
    priority    int         default 1             null,
    importance  int         default 1             null,
    constraint fk_objective_user
        foreign key (user_id) references users (id)
);

create index idx_user_id
    on objective (user_id);

