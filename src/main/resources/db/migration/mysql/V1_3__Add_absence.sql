CREATE TABLE absence (
  id int(11) NOT NULL AUTO_INCREMENT,
  created datetime DEFAULT NULL,
  modified datetime DEFAULT NULL,
  start date NOT NULL,
  end date NOT NULL,
  reason varchar(255) NOT NULL,
  employee_id int(11) NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE absence
ADD INDEX FK_absence_employee_IX(employee_id),
ADD CONSTRAINT FOREIGN KEY FK_absence_employee(employee_id) REFERENCES employee(id);