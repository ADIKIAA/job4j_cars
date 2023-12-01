ALTER TABLE cars
ADD COLUMN owner_id int references owners(id);