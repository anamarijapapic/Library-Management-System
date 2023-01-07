CREATE TABLE IF NOT EXISTS work_category (
    work_id SERIAL
    REFERENCES work(id) NOT NULL,
    category_id  SERIAL
    REFERENCES category(id) NOT NULL,
    PRIMARY KEY (work_id, category_id)
);