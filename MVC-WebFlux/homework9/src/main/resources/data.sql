INSERT INTO author (first_name, last_name)
VALUES ('John', 'Smith'),
       ('Emily', 'Johnson'),
       ('Michael', 'Lee'),
       ('Maria', 'Garcia'),
       ('Wei', 'Wang'),
       ('Chan', 'Tai');

INSERT INTO genre (genre_name)
VALUES ('Mystic'),
       ('Horror'),
       ('Fantastic'),
       ('Comedy'),
       ('Drama'),
       ('Action');

INSERT INTO book (title, author_id, genre_id)
VALUES ('The Haunting of Hill House', 5, 2),
       ('It', 4, 2),
       ('The Lord of the Rings', 1, 3),
       ('Bridget Jones’s Diary', 2, 4),
       ('The Catcher in the Rye', 3, 5),
       ('Mu-mu', 3, 4);

INSERT INTO comment(body, book_id)
VALUES ('Wonderful book', 1),
       ('Don''t read it', 2),
       ('I fell asleep while reading', 3),
       ('Not interested', 4),
       ('Best book', 5),
       ('Super book', 6);



