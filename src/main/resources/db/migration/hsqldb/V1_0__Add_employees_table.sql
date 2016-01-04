CREATE TABLE employee (
  id IDENTITY NOT NULL,
  created datetime DEFAULT NULL,
  modified datetime DEFAULT NULL,
  name varchar(255) NOT NULL,
  email varchar(255) NOT NULL
);