DROP TABLE IF EXISTS hotel;

CREATE TABLE hotel (
                       id SERIAL PRIMARY KEY,
                       name TEXT NOT NULL,
                       location FLOAT8[] NOT NULL,
                       description TEXT NOT NULL,
                       amenities TEXT[] NOT NULL,
                       images TEXT[] NOT NULL
);