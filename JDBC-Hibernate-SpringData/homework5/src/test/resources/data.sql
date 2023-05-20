INSERT INTO author (last_name, first_name, date_of_birth)
VALUES ('Smith', 'John', '1990-01-01'),
       ('Johnson', 'Emily', '1985-02-15'),
       ('Lee', 'Michael', '1995-05-10'),
       ('Garcia', 'Maria', '1988-12-20'),
       ('Wang', 'Wei', '1993-07-03');

INSERT INTO genre (genre_name)
VALUES ('Mystic'),
       ('Horror'),
       ('Fantastic'),
       ('Comedy'),
       ('Drama');

INSERT INTO book (title, publication_date, author_id, genre_id)
VALUES ('The Haunting of Hill House', '1959-10-16', 5, 2),
       ('It', '1986-09-15', 4, 2),
       ('The Lord of the Rings', '1954-07-29', 1, 3),
       ('Bridget Jones’s Diary', '1996-04-01', 2, 4),
       ('The Catcher in the Rye', '1951-07-16', 3, 5);



