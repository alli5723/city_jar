CREATE TABLE IF NOT EXISTS city (
  id serial primary key, 
  name text not null, 
  photo text not null
);

COPY city FROM '/tmp/cities.csv' DELIMITER ',' CSV HEADER;