create table posts
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

