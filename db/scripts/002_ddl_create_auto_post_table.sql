CREATE TABLE auto_post
(
    id             serial primary key,
    description    varchar not null,
    created        timestamp not null,
    status         boolean default false,
    price          int not null,
    auto_user_id   int references auto_user(id)
);