ALTER TABLE employee
ADD COLUMN velocity INT DEFAULT 100 NOT NULL;

CREATE TABLE episode (
  id IDENTITY NOT NULL,
  created datetime DEFAULT NULL,
  modified datetime DEFAULT NULL,
  start date NOT NULL,
  "end" date NOT NULL,
  name varchar(255) NOT NULL
);

CREATE TABLE employee_episode (
  id IDENTITY NOT NULL,
  created datetime DEFAULT NULL,
  modified datetime DEFAULT NULL,
  employee_id int NOT NULL,
  episode_id int NOT NULL,
  velocity INT
);

ALTER TABLE employee_episode
ADD CONSTRAINT FK_employeeepisode_employee FOREIGN KEY (employee_id) REFERENCES employee(id);

ALTER TABLE employee_episode
ADD CONSTRAINT FK_employeeepisode_episode FOREIGN KEY (episode_id) REFERENCES episode(id);
