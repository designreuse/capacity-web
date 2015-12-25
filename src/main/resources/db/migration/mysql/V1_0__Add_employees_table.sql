CREATE TABLE employee (
  id int(11) NOT NULL AUTO_INCREMENT,
  created datetime DEFAULT NULL,
  modified datetime DEFAULT NULL,
  name varchar(255) NOT NULL,
  email varchar(255) NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;