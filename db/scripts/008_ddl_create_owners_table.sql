CREATE TABLE owners
(
    id SERIAL PRIMARY KEY,
    name varchar,
    user_id int NOT NULL references auto_user(id)
);