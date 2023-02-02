DROP TYPE status CASCADE;

DROP TABLE book CASCADE;
DROP TABLE work CASCADE;
DROP TABLE work_category CASCADE;
DROP TABLE category CASCADE;
DROP TABLE work_author CASCADE;
DROP TABLE author CASCADE;

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
CREATE CAST ( varchar AS status) WITH INOUT AS IMPLICIT;

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
