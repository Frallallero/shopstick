DROP TABLE IF EXISTS role;
DROP TABLE IF EXISTS "user";
DROP TABLE IF EXISTS item;

CREATE TABLE role (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  name VARCHAR(20) NOT NULL
);

CREATE TABLE "user" (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  role_id INT NOT NULL,
  name VARCHAR(80) NOT NULL,
  username VARCHAR(30) NOT NULL,
  password VARCHAR(50) DEFAULT NULL
);

CREATE TABLE item (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  name VARCHAR(80) NOT NULL,
  description VARCHAR(250) DEFAULT NULL,
  item_type ENUM('fork', 'spoon', 'chopstick') NOT NULL,
  stock_number INT NOT NULL,
  price NUMERIC NOT NULL,
  image VARCHAR(250) DEFAULT NULL
);

INSERT INTO role (id, name) VALUES
  (1, 'OWNER'),
  (2, 'CUSTOMER');

INSERT INTO "user" (id, role_id, name, username, password) VALUES
  (1, 2, 'Mario', 'Bianco', 'Password.1'),
  (2, 1, 'Bill', 'Gates', 'Password.2');