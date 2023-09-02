CREATE TABLE author
(
                        id bigserial PRIMARY KEY,
                        first_name varchar(255),
                        last_name varchar(255)
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
                              author_id bigint REFERENCES author(id),
                              genre_id bigint REFERENCES genre(id)
);

CREATE TABLE comment
(
    id      bigserial PRIMARY KEY,
    body    text,
    book_id bigint REFERENCES book (id) ON DELETE CASCADE
);

