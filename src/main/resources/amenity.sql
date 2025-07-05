CREATE EXTENSION IF NOT EXISTS "pgcrypto";
DROP TABLE IF EXISTS amenity;

CREATE TABLE amenity (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name TEXT NOT NULL,
    description TEXT,
    icon TEXT,
    created_at TEXT NOT NULL,
    updated_at TEXT NOT NULL
);