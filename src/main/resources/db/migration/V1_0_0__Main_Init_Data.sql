-- Добавление авторов
INSERT INTO t_authors (name) VALUES ('Mark Twain');
INSERT INTO t_authors (name) VALUES ('Jane Austen');
INSERT INTO t_authors (name) VALUES ('Ernest Hemingway');

-- Добавление жанров
INSERT INTO t_genres (genre_name, description) VALUES ('Novel', 'A long narrative work of fiction');
INSERT INTO t_genres (genre_name, description) VALUES ('Poetry', 'Literary work in which the expression of feelings and ideas is given intensity by the use of distinctive style and rhythm');
INSERT INTO t_genres (genre_name, description) VALUES ('Science Fiction', 'Fiction based on imagined future scientific or technological advances and major social or environmental changes');

-- Добавление издателей
INSERT INTO t_publishers (publisher_name, publisher_year, address) VALUES ('Penguin Books', 1935, 'London, England');
INSERT INTO t_publishers (publisher_name, publisher_year, address) VALUES ('HarperCollins', 1989, 'New York, USA');
INSERT INTO t_publishers (publisher_name, publisher_year, address) VALUES ('Random House', 1927, 'New York, USA');

-- Добавление книг
INSERT INTO t_books (title, isbn, language, author_id) VALUES ('The Adventures of Tom Sawyer', '978-0-12345-678-9', 'English', 1);
INSERT INTO t_books (title, isbn, language, author_id) VALUES ('Pride and Prejudice', '978-1-23456-789-0', 'English', 2);
INSERT INTO t_books (title, isbn, language, author_id) VALUES ('The Old Man and the Sea', '978-2-34567-890-1', 'English', 3);

-- Устанавливаем связи между книгами и жанрами
INSERT INTO books_genres (book_id, genre_id) VALUES (1, 1);
INSERT INTO books_genres (book_id, genre_id) VALUES (2, 1);
INSERT INTO books_genres (book_id, genre_id) VALUES (3, 3);

-- Устанавливаем связи между книгами и издателями
INSERT INTO books_publishers (book_id, publisher_id) VALUES (1, 1);
INSERT INTO books_publishers (book_id, publisher_id) VALUES (2, 2);
INSERT INTO books_publishers (book_id, publisher_id) VALUES (3, 3);
