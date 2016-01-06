CREATE TABLE holiday (
  id int NOT NULL IDENTITY,
  created datetime DEFAULT NULL,
  modified datetime DEFAULT NULL,
  date date NOT NULL,
  hoursReduction int NOT NULL,
  name varchar(255) NOT NULL,
  PRIMARY KEY (id)
)
GO

CREATE TABLE location (
  id int NOT NULL IDENTITY,
  created datetime DEFAULT NULL,
  modified datetime DEFAULT NULL,
  name varchar(255) NOT NULL,
  PRIMARY KEY (id)
)
GO

ALTER TABLE employee
ADD start date DEFAULT NULL,
    [end] date DEFAULT NULL,
    workHoursPerDay int NOT NULL,
    location_id int NULL
    CONSTRAINT FK_employee_location FOREIGN KEY (location_id) REFERENCES location(id)
GO

CREATE INDEX FK_employee_location_IX ON employee(location_id)
GO

CREATE TABLE holiday_location (
  id int NOT NULL IDENTITY,
  holiday_id int NOT NULL,
  location_id int NOT NULL,
  PRIMARY KEY (id)
)
GO

ALTER TABLE holiday_location
ADD CONSTRAINT FK_holidaylocation_location FOREIGN KEY (location_id) REFERENCES location(id),
    CONSTRAINT FK_holidaylocation_holiday FOREIGN KEY (holiday_id) REFERENCES holiday(id)
GO

CREATE INDEX FK_holidaylocation_location_IX ON holiday_location(location_id)
GO
CREATE INDEX FK_holidaylocation_holiday_IX ON holiday_location(holiday_id)
GO
