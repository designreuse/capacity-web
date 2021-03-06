CREATE TABLE working_hours (
  id IDENTITY NOT NULL,
  dayOfWeek int NOT NULL,
  start time NOT NULL,
  "end" time NOT NULL,
  employee_id int NOT NULL
);

ALTER TABLE working_hours
ADD CONSTRAINT FK_workinghours_employee FOREIGN KEY (employee_id) REFERENCES employee(id);

INSERT INTO working_hours(dayOfWeek, start, "end", employee_id)
SELECT 1, '08:00', CONCAT(workHoursPerDay + 8, ':00'), id FROM employee;
INSERT INTO working_hours(dayOfWeek, start, "end", employee_id)
SELECT 2, '08:00', CONCAT(workHoursPerDay + 8, ':00'), id FROM employee;
INSERT INTO working_hours(dayOfWeek, start, "end", employee_id)
SELECT 3, '08:00', CONCAT(workHoursPerDay + 8, ':00'), id FROM employee;
INSERT INTO working_hours(dayOfWeek, start, "end", employee_id)
SELECT 4, '08:00', CONCAT(workHoursPerDay + 8, ':00'), id FROM employee;
INSERT INTO working_hours(dayOfWeek, start, "end", employee_id)
SELECT 5, '08:00', CONCAT(workHoursPerDay + 8, ':00'), id FROM employee;

ALTER TABLE employee
DROP COLUMN workHoursPerDay;