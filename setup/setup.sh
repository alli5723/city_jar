#!/bin/sh
seed_database()
{
  current_directory=$(pwd)
  csv_seed=$current_directory"/cities.csv"
  awk -v csv_seed="$csv_seed" '{gsub("./cities.csv", csv_seed); print $0}' ./db.sql | psql -U postgres -d cities
}

createdb cities
seed_database
