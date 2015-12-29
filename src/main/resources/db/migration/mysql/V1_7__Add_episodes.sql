ALTER TABLE employee
ADD COLUMN velocity INT(11) NOT NULL DEFAULT 100;

CREATE TABLE episode (
  id int(11) NOT NULL AUTO_INCREMENT,
  created datetime DEFAULT NULL,
  modified datetime DEFAULT NULL,
  start date NOT NULL,
  end date NOT NULL,
  name varchar(255) NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE employee_episode (
  id int(11) NOT NULL AUTO_INCREMENT,
  created datetime DEFAULT NULL,
  modified datetime DEFAULT NULL,
  employee_id int(11) NOT NULL,
  episode_id int(11) NOT NULL,
  velocity INT(11),
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE employee_episode
ADD INDEX FK_employeeepisode_employee_IX(employee_id),
ADD CONSTRAINT FOREIGN KEY FK_employeeepisode_employee(employee_id) REFERENCES employee(id);

ALTER TABLE employee_episode
ADD INDEX FK_employeeepisode_episode_IX(episode_id),
ADD CONSTRAINT FOREIGN KEY FK_employeeepisode_episode(episode_id) REFERENCES episode(id);
