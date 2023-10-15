CREATE TABLE cars
(
    id          serial PRIMARY KEY,
    name        varchar,
    engine_id   int NOT NULL references engines(id)
);