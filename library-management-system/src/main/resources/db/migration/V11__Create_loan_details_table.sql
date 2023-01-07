CREATE TABLE IF NOT EXISTS loan_details (
    id SERIAL NOT NULL PRIMARY KEY,
    loan_id SERIAL,
    book_id SERIAL,
    CONSTRAINT fk_loan
        FOREIGN KEY(loan_id)
            REFERENCES loan(id),
    CONSTRAINT fk_book
        FOREIGN KEY(book_id)
            REFERENCES book(id),
    date_returned TIMESTAMP
);