INSERT INTO author (first_name, last_name, date_of_birth)
VALUES ('John', 'Smith', '1990-01-01'),
       ('Emily', 'Johnson', '1985-02-15'),
       ('Michael', 'Lee', '1995-05-10'),
       ('Maria', 'Garcia', '1988-12-20'),
       ('Wei', 'Wang', '1993-07-03'),
       ('Chan', 'Tai', '1985-03-06');

INSERT INTO genre (genre_name)
VALUES ('Mystic'),
       ('Horror'),
       ('Fantastic'),
       ('Comedy'),
       ('Drama'),
       ('Action');

INSERT INTO book (title, publication_date, author_id, genre_id)
VALUES ('The Haunting of Hill House', '1959-10-16', 5, 2),
       ('It', '1986-09-15', 4, 2),
       ('The Lord of the Rings', '1954-07-29', 1, 3),
       ('Bridget Jones’s Diary', '1996-04-01', 2, 4),
       ('The Catcher in the Rye', '1951-07-16', 3, 5),
       ('Mu-mu', '1958-09-16', 3, 4);

INSERT INTO comment(body, book_id)
VALUES ('Wonderful book', 1),
       ('Don''t read it', 2),
       ('I fell asleep while reading', 3),
       ('Not interested', 4),
       ('Best book', 5);



