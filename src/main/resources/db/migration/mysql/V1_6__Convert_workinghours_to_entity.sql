CREATE TABLE working_hours (
  id int(11) NOT NULL AUTO_INCREMENT,
  dayOfWeek int(11) NOT NULL,
  start time NOT NULL,
  end time NOT NULL,
  employee_id int(11) NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE working_hours
ADD INDEX FK_workinghours_employee_IX(employee_id),
ADD CONSTRAINT FOREIGN KEY FK_workinghours_employee(employee_id) REFERENCES employee(id);

INSERT INTO working_hours(dayOfWeek, start, end, employee_id)
SELECT 1, '08:00', CONCAT(workHoursPerDay + 8, ':00'), id FROM employee;
INSERT INTO working_hours(dayOfWeek, start, end, employee_id)
SELECT 2, '08:00', CONCAT(workHoursPerDay + 8, ':00'), id FROM employee;
INSERT INTO working_hours(dayOfWeek, start, end, employee_id)
SELECT 3, '08:00', CONCAT(workHoursPerDay + 8, ':00'), id FROM employee;
INSERT INTO working_hours(dayOfWeek, start, end, employee_id)
SELECT 4, '08:00', CONCAT(workHoursPerDay + 8, ':00'), id FROM employee;
INSERT INTO working_hours(dayOfWeek, start, end, employee_id)
SELECT 5, '08:00', CONCAT(workHoursPerDay + 8, ':00'), id FROM employee;

ALTER TABLE employee
DROP COLUMN workHoursPerDay;