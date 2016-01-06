CREATE TABLE absence (
  id IDENTITY NOT NULL,
  created datetime DEFAULT NULL,
  modified datetime DEFAULT NULL,
  start date NOT NULL,
  "end" date NOT NULL,
  reason varchar(255) NOT NULL,
  employee_id int NOT NULL
);

ALTER TABLE absence
ADD CONSTRAINT FK_absence_employee FOREIGN KEY (employee_id) REFERENCES employee(id);