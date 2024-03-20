CREATE TABLE IF NOT EXISTS t_authors (
      id INT AUTO_INCREMENT PRIMARY KEY,
      name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS t_genres (
    id INT AUTO_INCREMENT PRIMARY KEY,
    genre_name VARCHAR(50) NOT NULL,
    description TEXT,
    CONSTRAINT uc_genre_name UNIQUE (genre_name)
);

CREATE TABLE IF NOT EXISTS t_publishers (
    id INT AUTO_INCREMENT PRIMARY KEY,
    publisher_name VARCHAR(255) NOT NULL,
    publisher_year INT,
    address TEXT
);

CREATE TABLE IF NOT EXISTS t_books (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(50) NOT NULL,
    isbn VARCHAR(255) NOT NULL,
    language VARCHAR(255) NOT NULL,
    author_id INT,
    FOREIGN KEY (author_id) REFERENCES t_authors(id) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS books_genres (
    book_id INT,
    genre_id INT,
    PRIMARY KEY (book_id, genre_id),
    FOREIGN KEY (book_id) REFERENCES t_books(id) ON DELETE CASCADE,
    FOREIGN KEY (genre_id) REFERENCES t_genres(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS books_publishers (
    book_id INT,
    publisher_id INT,
    PRIMARY KEY (book_id, publisher_id),
    FOREIGN KEY (book_id) REFERENCES t_books(id) ON DELETE CASCADE,
    FOREIGN KEY (publisher_id) REFERENCES t_publishers(id) ON DELETE CASCADE
);
