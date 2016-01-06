CREATE TABLE absence (
  id int NOT NULL IDENTITY,
  created datetime DEFAULT NULL,
  modified datetime DEFAULT NULL,
  start date NOT NULL,
  [end] date NOT NULL,
  reason varchar(255) NOT NULL,
  employee_id int NOT NULL,
  PRIMARY KEY (id)
)
GO

ALTER TABLE absence
ADD CONSTRAINT FK_absence_employee FOREIGN KEY (employee_id) REFERENCES employee(id)
GO

CREATE INDEX FK_absence_employee_IX ON absence(employee_id)
GO
