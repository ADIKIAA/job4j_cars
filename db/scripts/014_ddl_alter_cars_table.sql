ALTER TABLE cars
ADD COLUMN photo_id int references photos(id);