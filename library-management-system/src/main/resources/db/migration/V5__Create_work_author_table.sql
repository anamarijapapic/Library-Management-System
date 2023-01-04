CREATE TABLE IF NOT EXISTS work_author (
    work_id SERIAL
        REFERENCES work(id) NOT NULL,
    author_id  SERIAL
        REFERENCES author(id) NOT NULL,
    PRIMARY KEY (work_id, author_id)
);