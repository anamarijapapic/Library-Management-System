DROP TYPE status CASCADE;

DROP TABLE book CASCADE;
DROP TABLE work CASCADE;
DROP TABLE work_category CASCADE;
DROP TABLE category CASCADE;
DROP TABLE work_author CASCADE;
DROP TABLE author CASCADE;
DROP TABLE loan_details CASCADE;
DROP TABLE loan CASCADE;
DROP TABLE role CASCADE;
DROP TABLE user_role CASCADE;
DROP TABLE "user" CASCADE;

CREATE TABLE IF NOT EXISTS "user" (
    id SERIAL NOT NULL PRIMARY KEY,
    first_name VARCHAR NOT NULL,
    last_name VARCHAR NOT NULL,
    "password" VARCHAR NOT NULL,
    email VARCHAR UNIQUE NOT NULL,
    date_of_birth TIMESTAMP NOT NULL,
    contact_number VARCHAR NOT NULL,
    "enabled" BOOLEAN NULL
);

CREATE TABLE IF NOT EXISTS role (
    id SERIAL NOT NULL PRIMARY KEY,
    "name" VARCHAR NOT NULL
);

CREATE TABLE user_role (
    user_id SERIAL NOT NULL,
    role_id SERIAL NOT NULL,
    CONSTRAINT fk_user
       FOREIGN KEY(user_id)
           REFERENCES "user"(id),
    CONSTRAINT fk_role
       FOREIGN KEY(role_id)
           REFERENCES role(id)
);

CREATE INDEX fk_user_idx ON user_role (user_id);
CREATE INDEX fk_role_idx ON user_role (role_id);

INSERT INTO role ("name") VALUES ('ADMIN');
INSERT INTO role ("name") VALUES ('LIBRARIAN');
INSERT INTO role ("name") VALUES ('MEMBER');

INSERT INTO "user" (
    first_name,
    last_name,
    "password",
    email,
    date_of_birth,
    contact_number,
    "enabled"
) VALUES (
    'admin',
    'admin',
    '$2a$12$3tZr1OLzCU1mojVnwAtBlOOy9WkJNM2Yifbr3iAxVQBFXlqIAlw22',
    'admin@admin.oss',
    '1980-01-10 00:00:00',
    '+1111111111',
    TRUE
);

INSERT INTO "user" (
    first_name,
    last_name,
    "password",
    email,
    date_of_birth,
    contact_number,
    "enabled"
) VALUES (
    'librarian',
    'librarian',
    '$2a$12$3tZr1OLzCU1mojVnwAtBlOOy9WkJNM2Yifbr3iAxVQBFXlqIAlw22',
    'librarian@librarian.oss',
    '1990-02-20 00:00:00',
    '+2222222222',
    TRUE
);

INSERT INTO "user" (
    first_name,
    last_name,
    "password",
    email,
    date_of_birth,
    contact_number,
    "enabled"
) VALUES (
    'member',
    'member',
    '$2a$12$3tZr1OLzCU1mojVnwAtBlOOy9WkJNM2Yifbr3iAxVQBFXlqIAlw22',
    'member@member.oss',
    '2000-03-30 00:00:00',
    '+3333333333',
    TRUE
);

INSERT INTO user_role (user_id, role_id) VALUES (1, 1); -- user admin has role ADMIN
INSERT INTO user_role (user_id, role_id) VALUES (2, 2); -- user librarian has role LIBRARIAN
INSERT INTO user_role (user_id, role_id) VALUES (3, 3); -- user member has role MEMBER

CREATE TABLE IF NOT EXISTS author (
    id SERIAL NOT NULL PRIMARY KEY,
    first_name VARCHAR NOT NULL,
    last_name VARCHAR NOT NULL
);

CREATE TABLE IF NOT EXISTS work (
    id SERIAL NOT NULL PRIMARY KEY,
    title VARCHAR NOT NULL,
    description VARCHAR NOT NULL
);

CREATE TABLE IF NOT EXISTS work_author (
    work_id SERIAL
        REFERENCES work(id) NOT NULL,
    author_id  SERIAL
        REFERENCES author(id) NOT NULL,
    PRIMARY KEY (work_id, author_id)
);

CREATE TABLE IF NOT EXISTS category (
    id SERIAL NOT NULL PRIMARY KEY,
    "name" VARCHAR NOT NULL
);

CREATE TABLE IF NOT EXISTS work_category (
    work_id SERIAL
        REFERENCES work(id) NOT NULL,
    category_id  SERIAL
        REFERENCES category(id) NOT NULL,
    PRIMARY KEY (work_id, category_id)
);

CREATE TYPE status AS ENUM ('OK', 'DAMAGED', 'LOST');
CREATE CAST (varchar AS status) WITH INOUT AS IMPLICIT;

CREATE TABLE IF NOT EXISTS book (
    id SERIAL NOT NULL PRIMARY KEY,
    work_id SERIAL,
    CONSTRAINT fk_work
        FOREIGN KEY(work_id)
            REFERENCES work(id),
    publisher_name VARCHAR NOT NULL,
    year_of_publishing TIMESTAMP NOT NULL,
    ISBN VARCHAR NOT NULL,
    book_status status
);

CREATE TABLE IF NOT EXISTS loan (
    id SERIAL NOT NULL PRIMARY KEY,
    member_id SERIAL,
    librarian_id SERIAL,
    book_id SERIAL,
    CONSTRAINT fk_member
        FOREIGN KEY(member_id)
            REFERENCES "user"(id),
    CONSTRAINT fk_librarian
        FOREIGN KEY(librarian_id)
            REFERENCES "user"(id),
    CONSTRAINT fk_book
        FOREIGN KEY(book_id)
            REFERENCES book(id),
    date_issued TIMESTAMP NOT NULL,
    date_returned TIMESTAMP
);
