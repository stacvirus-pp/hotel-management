CREATE EXTENSION IF NOT EXISTS "pgcrypto";
DROP TABLE IF EXISTS hotel;

CREATE TABLE hotel
(
    id          UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name        TEXT NOT NULL,
    location    FLOAT8[] NOT NULL,
    description TEXT NOT NULL,
    amenities   TEXT[] NOT NULL,
    images      TEXT[] NOT NULL
);