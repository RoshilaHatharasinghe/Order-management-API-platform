-- create users table
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,  
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL
);

