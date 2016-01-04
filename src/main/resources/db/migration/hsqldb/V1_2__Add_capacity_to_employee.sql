CREATE TABLE holiday (
  id IDENTITY NOT NULL,
  created datetime DEFAULT NULL,
  modified datetime DEFAULT NULL,
  date date NOT NULL,
  hoursReduction int NOT NULL,
  name varchar(255) NOT NULL
);

CREATE TABLE location (
  id IDENTITY NOT NULL,
  created datetime DEFAULT NULL,
  modified datetime DEFAULT NULL,
  name varchar(255) NOT NULL
);

ALTER TABLE employee
ADD COLUMN start date DEFAULT NULL;

ALTER TABLE employee
ADD COLUMN end date DEFAULT NULL;

ALTER TABLE employee
ADD COLUMN workHoursPerDay int NOT NULL;

ALTER TABLE employee
ADD COLUMN location_id int NULL;

ALTER TABLE employee
ADD CONSTRAINT FK_employee_location FOREIGN KEY (location_id) REFERENCES location(id);

CREATE TABLE holiday_location (
  id IDENTITY NOT NULL,
  holiday_id int NOT NULL,
  location_id int NOT NULL
);

ALTER TABLE holiday_location
ADD CONSTRAINT FK_holidaylocation_location FOREIGN KEY (location_id) REFERENCES location(id);

ALTER TABLE holiday_location
ADD CONSTRAINT FK_holidaylocation_holiday FOREIGN KEY (holiday_id) REFERENCES holiday(id);
