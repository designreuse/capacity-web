CREATE TABLE ical_import (
  id int(11) NOT NULL AUTO_INCREMENT,
  created datetime DEFAULT NULL,
  modified datetime DEFAULT NULL,
  name varchar(255) NOT NULL,
  url varchar(1023) NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

