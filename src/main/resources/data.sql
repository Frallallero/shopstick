DROP TABLE IF EXISTS role;
DROP TABLE IF EXISTS shop_user;
DROP TABLE IF EXISTS transaction;
DROP TABLE IF EXISTS item;
DROP TABLE IF EXISTS cart;
DROP TABLE IF EXISTS r_cart_item;
DROP SEQUENCE IF EXISTS shop_user_sq;
DROP SEQUENCE IF EXISTS transaction_sq;
DROP SEQUENCE IF EXISTS cart_sq;
DROP SEQUENCE IF EXISTS r_cart_item_sq;

CREATE TABLE role (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  name VARCHAR(20) NOT NULL
);

CREATE TABLE shop_user (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  role_id INT NOT NULL,
  name VARCHAR(20) NOT NULL,
  username VARCHAR(80) NOT NULL,
  password VARCHAR(40) NOT NULL
);

CREATE SEQUENCE shop_user_sq
  START WITH 1
  INCREMENT BY 1;

CREATE TABLE transaction (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  shop_user_id INT NOT NULL,
  status_id INT NOT NULL,
  foreign key (shop_user_id) references shop_user(id)
);

CREATE SEQUENCE transaction_sq
  START WITH 1
  INCREMENT BY 1;

CREATE TABLE item (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  name VARCHAR(80) NOT NULL,
  description VARCHAR(250) DEFAULT NULL,
  category ENUM('FORK', 'SPOON', 'CHOPSTICK') NOT NULL,
  stock_number INT NOT NULL,
  price NUMERIC NOT NULL,
  image VARCHAR(250) DEFAULT NULL
);

CREATE TABLE cart (
  id INT AUTO_INCREMENT PRIMARY KEY,
  transaction_id INT NOT NULL,
  foreign key (transaction_id) references transaction(id)
);

CREATE TABLE r_cart_item (
  id INT AUTO_INCREMENT PRIMARY KEY,
  cart_id INT NOT NULL,
  item_id INT NOT NULL,
  quantity INT NOT NULL,
  foreign key (cart_id) references cart(id),
  foreign key (item_id) references item(id)
);

CREATE SEQUENCE cart_sq
  START WITH 1
  INCREMENT BY 1;
  
CREATE SEQUENCE r_cart_item_sq
  START WITH 1
  INCREMENT BY 1;

ALTER TABLE cart ADD CONSTRAINT cart_transaction_uq UNIQUE(transaction_id);

INSERT INTO role (id, name) VALUES
  (1, 'OWNER'),
  (2, 'CUSTOMER');

INSERT INTO shop_user VALUES
   (1, 1, 'Mario', 'sg', '1111'),
   (2, 2, 'Luigi', 'mb', '2222');