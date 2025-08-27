CREATE TABLE Users (
    email VARCHAR(30),
    username VARCHAR(40),
    password VARCHAR(30),
    file_name VARCHAR(30),
    age INT
);

CREATE TABLE Files (
    name VARCHAR(30),
    content VARCHAR(3000),
    page_number INT,
    owner VARCHAR(30),
    tag VARCHAR(20)
);