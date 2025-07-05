CREATE EXTENSION IF NOT EXISTS "pgcrypto";
DROP TABLE IF EXISTS hotel;

CREATE TABLE hotel
(
    id          UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name        TEXT NOT NULL,
    location    FLOAT8[] NOT NULL,
    description TEXT NOT NULL,
    amenities   UUID[],
    images      TEXT[] NOT NULL,
    created_at TEXT NOT NULL,
    updated_at TEXT NOT NULL
);