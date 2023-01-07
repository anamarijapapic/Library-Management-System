CREATE TYPE status AS ENUM ('OK', 'damaged', 'lost');

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