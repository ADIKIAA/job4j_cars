ALTER TABLE cars
ADD COLUMN owner_id int references owners(id);

ALTER TABLE cars
ADD COLUMN brand varchar;

ALTER TABLE cars
ADD COLUMN bodywork varchar;