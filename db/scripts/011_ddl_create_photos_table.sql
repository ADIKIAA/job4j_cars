CREATE TABLE photos
(
    id      SERIAL PRIMARY KEY,
    path    varchar,
    car_id  int references cars(id)
);