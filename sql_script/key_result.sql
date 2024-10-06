create table key_result
(
    id            bigint auto_increment
        primary key,
    objective_id  bigint           not null,
    title         varchar(255)     not null,
    description   text             null,
    start_value   double default 0 null,
    target_value  double default 0 null,
    current_value double default 0 null,
    user_id       bigint           null,
    complexity    int    default 1 null,
    priority      int    default 1 null,
    importance    int    default 1 null,
    constraint fk_key_result_objective
        foreign key (objective_id) references objective (id),
    constraint fk_key_result_user
        foreign key (user_id) references users (id)
);

create index idx_objective_id
    on key_result (objective_id);

create index idx_user_id
    on key_result (user_id);

