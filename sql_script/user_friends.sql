create table user_friends
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

