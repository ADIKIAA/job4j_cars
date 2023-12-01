CREATE TABLE participates
(
    user_id int NOT NULL references auto_user(id),
    post_id int NOT NULL references auto_post(id)
);