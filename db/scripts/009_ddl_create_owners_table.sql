CREATE TABLE owners
(
    id SERIAL PRIMARY KKEY,
    name varchar,
    user_id int NOT NULL references auto_user(id)
);