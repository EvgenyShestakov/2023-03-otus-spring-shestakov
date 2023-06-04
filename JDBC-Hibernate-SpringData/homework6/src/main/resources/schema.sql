CREATE TABLE author
(
                        id bigserial PRIMARY KEY,
                        last_name varchar(255),
                        first_name varchar(255),
                        date_of_birth date
);

CREATE TABLE genre
(
                        id bigserial PRIMARY KEY,
                        genre_name varchar(255)
);

CREATE TABLE book
(
                              id bigserial PRIMARY KEY,
                              title varchar(255),
                              publication_date date,
                              author_id bigint REFERENCES author(id),
                              genre_id bigint REFERENCES genre(id)
);

CREATE TABLE comment
(
    id bigserial PRIMARY KEY,
    body text,
    book_id bigint REFERENCES book(id)
);

