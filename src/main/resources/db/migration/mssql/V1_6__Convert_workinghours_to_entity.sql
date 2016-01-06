CREATE TABLE working_hours (
  id int NOT NULL IDENTITY,
  dayOfWeek int NOT NULL,
  start time NOT NULL,
  [end] time NOT NULL,
  employee_id int NOT NULL,
  PRIMARY KEY (id)
)
GO

ALTER TABLE working_hours
ADD CONSTRAINT FK_workinghours_employee FOREIGN KEY (employee_id) REFERENCES employee(id)
GO

CREATE INDEX FK_workinghours_employee_IX ON working_hours(employee_id);

INSERT INTO working_hours(dayOfWeek, start, [end], employee_id)
SELECT 1, '08:00', CAST(workHoursPerDay + 8 AS varchar(2)) + ':00', id FROM employee
GO

INSERT INTO working_hours(dayOfWeek, start, [end], employee_id)
SELECT 2, '08:00', CAST(workHoursPerDay + 8 AS varchar(2)) + ':00', id FROM employee
GO

INSERT INTO working_hours(dayOfWeek, start, [end], employee_id)
SELECT 3, '08:00', CAST(workHoursPerDay + 8 AS varchar(2)) + ':00', id FROM employee
GO

INSERT INTO working_hours(dayOfWeek, start, [end], employee_id)
SELECT 4, '08:00', CAST(workHoursPerDay + 8 AS varchar(2)) + ':00', id FROM employee
GO

INSERT INTO working_hours(dayOfWeek, start, [end], employee_id)
SELECT 5, '08:00', CAST(workHoursPerDay + 8 AS varchar(2)) + ':00', id FROM employee
GO

ALTER TABLE employee
DROP COLUMN workHoursPerDay
GO
