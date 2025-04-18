CREATE DATABASE IF NOT EXISTS jid_thrillio;
USE jid_thrillio;

CREATE TABLE IF NOT EXISTS User (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(50) NOT NULL,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    gender_id INT,
    user_type_id INT,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS Book (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(500),
    book_url VARCHAR(500),
    image_url VARCHAR(500),
    publication_year INT,
    publisher_id BIGINT,
    book_genre_id INT,
    goodreads_rating DOUBLE,
    kid_friendly_status INT,
    kid_friendly_marked_by BIGINT,
    shared_by BIGINT,
    created_date DATETIME
);

CREATE TABLE IF NOT EXISTS WebLink (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(500) NOT NULL,
    url VARCHAR(500) NOT NULL,
    host VARCHAR(500) NOT NULL,
    download_status INT DEFAULT 0,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS User_Book (
    user_id BIGINT,
    book_id BIGINT,
    FOREIGN KEY (user_id) REFERENCES User(id),
    FOREIGN KEY (book_id) REFERENCES Book(id),
    PRIMARY KEY (user_id, book_id)
);

CREATE TABLE IF NOT EXISTS Publisher (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(200) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS Author (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(200) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS Book_Author (
    book_id BIGINT,
    author_id BIGINT,
    FOREIGN KEY (book_id) REFERENCES Book(id),
    FOREIGN KEY (author_id) REFERENCES Author(id),
    PRIMARY KEY (book_id, author_id)
);