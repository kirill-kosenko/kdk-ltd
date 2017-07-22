INSERT INTO users(
  not_expired, not_locked, email, enabled, password, username) VALUES (true,true,'nikeya444@gmail.com',true,'$2a$06$N3cKWUV/INEqXQQYnY3V9exW7m.G7LVql3YSCtFdCYNSOSw0C55t6','kkm');
INSERT INTO users(
  not_expired, not_locked, email, enabled, password, username) VALUES (true,true,'vovah4uk@mail.ru',true,'$2a$06$oalBoyfiHcUZiJsiJN4SW.3aAMtzfHFSGXZRRKA5v3rO0djy4gDx2','vv');
INSERT INTO users(
  not_expired, not_locked, email, enabled, password, username) VALUES (true,true,'test@test.com',true,'$2a$10$XF.u6rHrU3ZOAn.COuaYzu2Xetmo7M1uSRgBWGa0TbfoQQiesnWBC','test');

INSERT INTO authorities(
  version, authority, username) VALUES (0,'ROLE_ADMIN',1);
INSERT INTO authorities(
  version, authority, username) VALUES (0,'ROLE_BASIC',2);
INSERT INTO authorities(
  version, authority, username) VALUES (0,'ROLE_ADMIN',3);

INSERT INTO partners(
  version, fathername, firstname, fullname, lastname, name) VALUES (0,'Олег','????','Олег Олег Олег','????',NULL);
INSERT INTO partners(
  version, fathername, firstname, fullname, lastname, name) VALUES (0,NULL,NULL,'',NULL,NULL);
INSERT INTO partners(
  version, fathername, firstname, fullname, lastname, name) VALUES (0,'',NULL,'?????',NULL,NULL);
INSERT INTO partners(
  version, fathername, firstname, fullname, lastname, name) VALUES (0,NULL,NULL,'????????',NULL,NULL);
INSERT INTO partners(
  version, fathername, firstname, fullname, lastname, name) VALUES (0,NULL,'???????','??????? ????????','??????????',NULL);
INSERT INTO partners(
  version, fathername, firstname, fullname, lastname, name) VALUES (0,NULL,'????','???? ????????',NULL,NULL);
INSERT INTO partners(
  version, fathername, firstname, fullname, lastname, name) VALUES (0,NULL,'??????','?????? ?????',NULL,NULL);
INSERT INTO partners(
  version, fathername, firstname, fullname, lastname, name) VALUES (0,NULL,NULL,'?????????','?????????',NULL);
INSERT INTO partners(
  version, fathername, firstname, fullname, lastname, name) VALUES (0,NULL,'????','???? ??15',NULL,NULL);
INSERT INTO partners(
  version, fathername, firstname, fullname, lastname, name) VALUES (0,NULL,'?????? ???',' ?????? ??? ','',NULL);
INSERT INTO partners(
  version, fathername, firstname, fullname, lastname, name) VALUES (0,NULL,'???? ?????','???? ?????',NULL,NULL);
INSERT INTO partners(
  version, fathername, firstname, fullname, lastname, name) VALUES (0,NULL,'?????','?????',NULL,NULL);
INSERT INTO partners(
  version, fathername, firstname, fullname, lastname, name) VALUES (0,NULL,NULL,'?????????',NULL,NULL);
INSERT INTO partners(
  version, fathername, firstname, fullname, lastname, name) VALUES (0,NULL,NULL,'???? ??????',NULL,NULL);
INSERT INTO partners(
  version, fathername, firstname, fullname, lastname, name) VALUES (0,NULL,'??????',NULL,'????',NULL);
INSERT INTO partners(
  version, fathername, firstname, fullname, lastname, name) VALUES (0,NULL,'??????','?????',NULL,'????????');

INSERT INTO products(
  version, description, name, packing, unit_name, unit_value, parent_id) VALUES (0,NULL,'Водка','?',NULL,NULL,NULL);
INSERT INTO products(
  version, description, name, packing, unit_name, unit_value, parent_id) VALUES (0,NULL,'Коньяк','?',NULL,NULL,NULL);
INSERT INTO products(
  version, description, name, packing, unit_name, unit_value, parent_id) VALUES (0,NULL,'Пшеничная 10','шт',NULL,NULL,1);
INSERT INTO products(
  version, description, name, packing, unit_name, unit_value, parent_id) VALUES (0,NULL,'Ельцин','шт',NULL,NULL, 1);
INSERT INTO products(
  version, description, name, packing, unit_name, unit_value, parent_id) VALUES (0,NULL,'Ром-карамель','шт',NULL,NULL,2);
INSERT INTO products(
  version, description, name, packing, unit_name, unit_value, parent_id) VALUES (0,NULL,'Вишня','шт',NULL,NULL,2);
INSERT INTO products(
  version, description, name, packing, unit_name, unit_value, parent_id) VALUES (0,NULL,'Шоколад','шт',NULL,NULL,2);
INSERT INTO products(
  version, description, name, packing, unit_name, unit_value, parent_id) VALUES (0,NULL,'Миндаль','шт',NULL,NULL,2);
INSERT INTO products(
  version, description, name, packing, unit_name, unit_value, parent_id) VALUES (0,NULL,'Классик','шт',NULL,NULL,2);
INSERT INTO products(
  version, description, name, packing, unit_name, unit_value, parent_id) VALUES (0,NULL,'Чернослив','шт',NULL,NULL,2);

SELECT setval('products_id_seq', (SELECT MAX(id) FROM products));