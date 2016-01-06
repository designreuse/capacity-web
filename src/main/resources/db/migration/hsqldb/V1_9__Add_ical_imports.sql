CREATE TABLE ical_import (
  id IDENTITY NOT NULL,
  created datetime DEFAULT NULL,
  modified datetime DEFAULT NULL,
  name varchar(255) NOT NULL,
  url varchar(1023) NOT NULL
);
