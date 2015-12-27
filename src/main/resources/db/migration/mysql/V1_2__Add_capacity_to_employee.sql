CREATE TABLE holiday (
  id int(11) NOT NULL AUTO_INCREMENT,
  created datetime DEFAULT NULL,
  modified datetime DEFAULT NULL,
  date date NOT NULL,
  hoursReduction int(11) NOT NULL,
  name varchar(255) NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE location (
  id int(11) NOT NULL AUTO_INCREMENT,
  created datetime DEFAULT NULL,
  modified datetime DEFAULT NULL,
  name varchar(255) NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE employee
ADD COLUMN start date DEFAULT NULL,
ADD COLUMN end date DEFAULT NULL,
ADD COLUMN workHoursPerDay int NOT NULL,
ADD COLUMN location_id int(11) NULL,
ADD INDEX FK_employee_location_IX(location_id),
ADD CONSTRAINT FOREIGN KEY FK_employee_location(location_id) REFERENCES location(id);

CREATE TABLE holiday_location (
  id int(11) NOT NULL AUTO_INCREMENT,
  holiday_id int(11) NOT NULL,
  location_id int(11) NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE holiday_location
ADD INDEX FK_holidaylocation_location_IX(location_id),
ADD CONSTRAINT FOREIGN KEY FK_holidaylocation_location(location_id) REFERENCES location(id),
ADD INDEX FK_holidaylocation_holiday_IX(holiday_id),
ADD CONSTRAINT FOREIGN KEY FK_holidaylocation_holiday(holiday_id) REFERENCES holiday(id);
