CREATE TABLE ical_import (
  id int NOT NULL IDENTITY,
  created datetime DEFAULT NULL,
  modified datetime DEFAULT NULL,
  name varchar(255) NOT NULL,
  url varchar(1023) NOT NULL,
  PRIMARY KEY (id)
)
GO
