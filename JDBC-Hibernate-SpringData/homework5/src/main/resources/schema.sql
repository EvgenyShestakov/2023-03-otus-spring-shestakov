create table author(
                        id bigserial PRIMARY KEY,
                        last_name varchar(255),
                        first_name varchar(255),
                        date_of_birth date
);

create table genre(
                        id bigserial PRIMARY KEY,
                        genre_name varchar(255)
);

create table book(
                              id bigserial PRIMARY KEY,
                              title varchar(255),
                              publication_date date,
                              author_id bigint REFERENCES author(id),
                              genre_id bigint REFERENCES genre(id)
);

