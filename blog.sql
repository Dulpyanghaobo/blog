create database blog;
create table if not exists roles
(
    id   bigint auto_increment
    primary key,
    name enum ('ROLE_ADMIN', 'ROLE_USER') null
    );

create table if not exists users
(
    id                      bigint auto_increment
    primary key,
    avatar                  varchar(255)                                           null,
    bio                     varchar(500)                                           null,
    disabled                bit                                                    not null,
    email                   varchar(255)                                           null,
    password                varchar(255)                                           null,
    phone_number            varchar(255)                                           null,
    registered_at           datetime(6)                                            not null,
    two_factor_auth_enabled bit                                                    not null,
    user_name               varchar(255)                                           not null,
    openid                  varchar(255)                                           null,
    nickname                varchar(100)                                           null,
    relationship_status     enum ('single', 'in_relationship', 'married', 'other') null,
    gender                  enum ('male', 'female', 'other')                       null,
    birthdate               date                                                   null,
    birth_time              time                                                   null,
    birth_place             varchar(255)                                           null,
    current_location        varchar(255)                                           null,
    timezone                varchar(50)                                            null,
    daylight_saving         bit                                                    null,
    zodiac_sign             varchar(20)                                            null,
    rising_sign             varchar(20)                                            null,
    moon_sign               varchar(20)                                            null,
    constraint UK_6dotkott2kjsp8vw4d0m25fb7
    unique (email),
    constraint openid
    unique (openid),
    constraint chk_login_conditions
    check ((`email` is not null) or (`phone_number` is not null) or (`openid` is not null))
    );

create table if not exists mood
(
    id         bigint auto_increment
    primary key,
    user_id    bigint not null,
    mood_date  date   not null,
    mood_level int    not null,
    constraint user_id
    unique (user_id, mood_date),
    constraint fk_mood_user
    foreign key (user_id) references users (id)
    );

create table if not exists objective
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

create table if not exists key_action
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

create table if not exists key_result
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

create index idx_user_id
    on objective (user_id);

create table if not exists posts
(
    id               bigint auto_increment
    primary key,
    allow_comment    bit                                    not null,
    base_snapshot    varchar(255)                           null,
    categories       varchar(255)                           null,
    cover            varchar(255)                           null,
    deleted          bit                                    not null,
    excerpt          varchar(255)                           null,
    head_snapshot    varchar(255)                           null,
    html_metas       varchar(255)                           null,
    owner            varchar(255)                           null,
    pinned           bit                                    not null,
    priority         int                                    not null,
    publish          bit                                    not null,
    publish_time     datetime(6)                            null,
    release_snapshot varchar(255)                           null,
    slug             varchar(255)                           not null,
    tags             varchar(255)                           null,
    template         varchar(255)                           null,
    title            varchar(255)                           not null,
    visible          enum ('INTERNAL', 'PRIVATE', 'PUBLIC') not null,
    user_id          bigint                                 null,
    constraint UK_qmmso8qxjpbxwegdtp0l90390
    unique (slug),
    constraint FK5lidm6cqbc7u4xhqpxm898qme
    foreign key (user_id) references users (id)
    );

create table if not exists task
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

create table if not exists user_friends
(
    user_id   bigint                                                                        not null,
    friend_id bigint                                                                        not null,
    status    enum ('PENDING', 'ACCEPTED', 'REJECTED', 'BLOCKED') default 'PENDING'         not null,
    added_at  timestamp                                           default CURRENT_TIMESTAMP not null,
    primary key (user_id, friend_id),
    constraint FK_user_friend_friend
    foreign key (friend_id) references users (id)
    on delete cascade,
    constraint FK_user_friend_user
    foreign key (user_id) references users (id)
    on delete cascade,
    constraint chk_friendship
    check (`user_id` <> `friend_id`)
    );

create table if not exists user_linked_accounts
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

create table if not exists user_privacy_settings
(
    id              bigint auto_increment
    primary key,
    user_id         bigint not null,
    share_birthdate bit    not null,
    share_gender    bit    not null,
    share_location  bit    not null,
    constraint user_privacy_settings_ibfk_1
    foreign key (user_id) references users (id)
    on delete cascade
    );

create index user_id
    on user_privacy_settings (user_id);

create table if not exists user_roles
(
    user_id bigint      not null,
    role    varchar(20) not null,
    role_id bigint      not null,
    constraint FKh8ciramu9cc9q3qcqiv4ue8a6
    foreign key (role_id) references roles (id),
    constraint FKhfh9dx7w3ubf1co1vdev94g3f
    foreign key (user_id) references users (id)
    );

create table if not exists verification_tokens
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

