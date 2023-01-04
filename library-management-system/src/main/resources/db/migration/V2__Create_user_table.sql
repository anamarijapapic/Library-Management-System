CREATE TYPE role AS ENUM ('member', 'librarian', 'admin');

CREATE TABLE IF NOT EXISTS "user" (
    id SERIAL NOT NULL PRIMARY KEY,
    first_name VARCHAR NOT NULL,
    last_name VARCHAR NOT NULL,
    passwd TEXT NOT NULL,
    email VARCHAR UNIQUE NOT NULL,
    date_of_birth TIMESTAMP NOT NULL,
    user_role role,
    contact_number VARCHAR NOT NULL
);