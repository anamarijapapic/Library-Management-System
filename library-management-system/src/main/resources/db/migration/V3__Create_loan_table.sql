CREATE TABLE IF NOT EXISTS loan (
    id SERIAL NOT NULL PRIMARY KEY,
    member_id SERIAL,
    librarian_id SERIAL,
    CONSTRAINT fk_member
        FOREIGN KEY(member_id)
            REFERENCES "user"(id),
    CONSTRAINT fk_librarian
        FOREIGN KEY(librarian_id)
            REFERENCES "user"(id),
    date_issued TIMESTAMP NOT NULL
);