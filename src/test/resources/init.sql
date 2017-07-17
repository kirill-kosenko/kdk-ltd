CREATE TABLE partners (
  id         BIGINT NOT NULL,
  version    INT(11)      DEFAULT NULL,
  insert_ts  DATETIME     DEFAULT NULL,
  update_ts  DATETIME     DEFAULT NULL,
  user       VARCHAR(255) DEFAULT NULL,
  fathername VARCHAR(255) DEFAULT NULL,
  firstname  VARCHAR(255) DEFAULT NULL,
  fullname   VARCHAR(255) DEFAULT NULL,
  lastname   VARCHAR(255) DEFAULT NULL,
  name       VARCHAR(255) DEFAULT NULL,
);

CREATE TABLE storages (
  id      BIGINT PRIMARY KEY AUTO_INCREMENT NOT NULL,
  version INT(11)      DEFAULT NULL,
  name    VARCHAR(255) DEFAULT NULL
);

CREATE TABLE users (
  id          BIGINT PRIMARY KEY AUTO_INCREMENT NOT NULL,
  email       VARCHAR(255) DEFAULT NULL,
  not_expired BIT          DEFAULT NULL,
  not_locked  BIT          DEFAULT NULL,
  enabled     BIT          NOT NULL,
  password    VARCHAR(255) DEFAULT NULL,
  username    VARCHAR(255) DEFAULT NULL,
);

CREATE TABLE product_types (
  id bigint PRIMARY KEY AUTO_INCREMENT NOT NULL,
  version int(11) DEFAULT NULL,
  description varchar(255) DEFAULT NULL,
  type varchar(255) DEFAULT NULL,
);

CREATE TABLE products (
  id          BIGINT PRIMARY KEY AUTO_INCREMENT NOT NULL,
  version     INT(11)      DEFAULT NULL,
  description VARCHAR(255) DEFAULT NULL,
  name        VARCHAR(255) DEFAULT NULL,
  unit_name   VARCHAR(255) DEFAULT NULL,
  parent_id   BIGINT       DEFAULT NULL,
  packing     VARCHAR(255) DEFAULT NULL,
  unit_value  BIGINT       DEFAULT NULL,
  FOREIGN KEY (parent_id) REFERENCES products (id)
);

CREATE TABLE authorities (
  id BIGINT PRIMARY KEY AUTO_INCREMENT NOT NULL,
  version  INT(11) DEFAULT NULL,
  authority VARCHAR(255) NOT NULL,
  username BIGINT NOT NULL,
  UNIQUE (username, authority),
  FOREIGN KEY (username) REFERENCES users (id)
);

CREATE TABLE deals (
  id         BIGINT PRIMARY KEY AUTO_INCREMENT NOT NULL,
  version    INT(11)      DEFAULT NULL,
  insert_ts  DATETIME     DEFAULT NULL,
  update_ts  DATETIME     DEFAULT NULL,
  user       VARCHAR(255) DEFAULT NULL,
  date_of    DATE         DEFAULT NULL,
  is_paid    BIT DEFAULT NULL,
  deal_type  VARCHAR(255) DEFAULT NULL,
  state      INT(11)      DEFAULT NULL,
  partner_id BIGINT  DEFAULT NULL,
  user_login BIGINT  DEFAULT NULL,
  FOREIGN KEY (partner_id) REFERENCES partners (id),
  FOREIGN KEY (user_login) REFERENCES users (id)
);

CREATE TABLE deal_details (
  id          BIGINT PRIMARY KEY AUTO_INCREMENT NOT NULL,
  version     INT(11)        DEFAULT NULL,
  insert_ts   DATETIME       DEFAULT NULL,
  update_ts   DATETIME       DEFAULT NULL,
  user        VARCHAR(255)   DEFAULT NULL,
  qnt         INT(11)        DEFAULT NULL,
  summ        DECIMAL(19, 2) DEFAULT NULL,
  product_id  BIGINT         DEFAULT NULL,
  document_id BIGINT         DEFAULT NULL,
  storage_id  BIGINT         DEFAULT NULL,
  FOREIGN KEY (storage_id) REFERENCES storages (id),
  FOREIGN KEY (document_id) REFERENCES deals (id),
  FOREIGN KEY (product_id) REFERENCES products (id)
);

CREATE TABLE orders (
  id             BIGINT PRIMARY KEY AUTO_INCREMENT NOT NULL,
  version        INT(11)      DEFAULT NULL,
  insert_ts      DATETIME     DEFAULT NULL,
  update_ts      DATETIME     DEFAULT NULL,
  user           VARCHAR(255) DEFAULT NULL,
  date_of        DATE         DEFAULT NULL,
  is_paid        BIT          DEFAULT 0,
  active         BIT          DEFAULT 0,
  deal_type      VARCHAR(255) DEFAULT NULL,
  completionDate DATE         DEFAULT NULL,
  partner_id     BIGINT       DEFAULT NULL,
  user_login     BIGINT       DEFAULT NULL,
  FOREIGN KEY (user_login) REFERENCES users (id),
  FOREIGN KEY (partner_id) REFERENCES partners (id)
);

CREATE TABLE order_detail (
  id         BIGINT PRIMARY KEY AUTO_INCREMENT NOT NULL,
  version    INT(11)        DEFAULT NULL,
  insert_ts  DATETIME       DEFAULT NULL,
  update_ts  DATETIME       DEFAULT NULL,
  user       VARCHAR(255)   DEFAULT NULL,
  qnt        INT(11)        DEFAULT NULL,
  summ       DECIMAL(19, 2) DEFAULT NULL,
  product_id BIGINT         DEFAULT NULL,
  order_id   BIGINT         DEFAULT NULL,
  FOREIGN KEY (product_id) REFERENCES products (id),
  FOREIGN KEY (order_id) REFERENCES orders (id)
);

CREATE TABLE phones (
  id         BIGINT PRIMARY KEY AUTO_INCREMENT NOT NULL,
  version    INT(11)      DEFAULT NULL,
  insert_ts  DATETIME     DEFAULT NULL,
  update_ts  DATETIME     DEFAULT NULL,
  user       VARCHAR(255) DEFAULT NULL,
  code       VARCHAR(255) DEFAULT NULL,
  fullNumber VARCHAR(255) DEFAULT NULL,
  number     VARCHAR(255) DEFAULT NULL,
  partner_id BIGINT DEFAULT NULL,
  FOREIGN KEY (partner_id) REFERENCES partners (id)
);

CREATE TABLE products_in_stock (
  id         BIGINT PRIMARY KEY AUTO_INCREMENT NOT NULL,
  version    INT(11)        DEFAULT NULL,
  qnt        INT(11)        DEFAULT NULL,
  restDate   DATE           DEFAULT NULL,
  summ       DECIMAL(19, 2) DEFAULT NULL,
  product_id BIGINT         DEFAULT NULL,
  storage_id BIGINT         DEFAULT NULL,
  FOREIGN KEY (product_id) REFERENCES products (id),
  FOREIGN KEY (storage_id) REFERENCES storages (id)
);







