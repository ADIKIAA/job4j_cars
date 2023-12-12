CREATE TABLE cars
(
    id          serial PRIMARY KEY,
    model       varchar,
    engine_id   int NOT NULL references engines(id)
);