CREATE TABLE history_owners
(
    id SERIAL PRIMARY KEY,
    car_id      int NOT NULL references cars(id),
    owner_id    int NOT NULL references owners(id)
    startAt     TIMESTAMP NOT NULL,
    endAT       TIMESTAMP
);