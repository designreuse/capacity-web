ALTER TABLE employee
ADD velocity INT NOT NULL DEFAULT 100
GO

CREATE TABLE episode (
  id int NOT NULL IDENTITY,
  created datetime DEFAULT NULL,
  modified datetime DEFAULT NULL,
  start date NOT NULL,
  [end] date NOT NULL,
  name varchar(255) NOT NULL,
  PRIMARY KEY (id)
)
GO

CREATE TABLE employee_episode (
  id int NOT NULL IDENTITY,
  created datetime DEFAULT NULL,
  modified datetime DEFAULT NULL,
  employee_id int NOT NULL,
  episode_id int NOT NULL,
  velocity INT,
  PRIMARY KEY (id)
)
GO

ALTER TABLE employee_episode
ADD CONSTRAINT FK_employeeepisode_employee FOREIGN KEY (employee_id) REFERENCES employee(id)
GO

CREATE INDEX FK_employeeepisode_employee_IX ON employee_episode(employee_id)
GO

ALTER TABLE employee_episode
ADD CONSTRAINT FK_employeeepisode_episode FOREIGN KEY (episode_id) REFERENCES episode(id)
GO

CREATE INDEX FK_employeeepisode_episode_IX ON employee_episode(episode_id)
GO