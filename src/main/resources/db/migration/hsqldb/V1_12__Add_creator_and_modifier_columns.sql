ALTER TABLE absence
ADD COLUMN id_creator int;
ALTER TABLE absence
ADD COLUMN id_modificator int;
ALTER TABLE absence
ADD CONSTRAINT FK_absence_creator FOREIGN KEY (id_creator) REFERENCES user_(id);
ALTER TABLE absence
ADD CONSTRAINT FK_absence_modificator FOREIGN KEY (id_modificator) REFERENCES user_(id);

ALTER TABLE employee
ADD COLUMN id_creator int;
ALTER TABLE employee
ADD COLUMN id_modificator int;
ALTER TABLE employee
ADD CONSTRAINT FK_employee_creator FOREIGN KEY (id_creator) REFERENCES user_(id);
ALTER TABLE employee
ADD CONSTRAINT FK_employee_modificator FOREIGN KEY (id_modificator) REFERENCES user_(id);

ALTER TABLE employee_episode
ADD COLUMN id_creator int;
ALTER TABLE employee_episode
ADD COLUMN id_modificator int;
ALTER TABLE employee_episode
ADD CONSTRAINT FK_employeeepisode_creator FOREIGN KEY (id_creator) REFERENCES user_(id);
ALTER TABLE employee_episode
ADD CONSTRAINT FK_employeeepisode_modificator FOREIGN KEY (id_modificator) REFERENCES user_(id);

ALTER TABLE episode
ADD COLUMN id_creator int;
ALTER TABLE episode
ADD COLUMN id_modificator int;
ALTER TABLE episode
ADD CONSTRAINT FK_episode_creator FOREIGN KEY (id_creator) REFERENCES user_(id);
ALTER TABLE episode
ADD CONSTRAINT FK_episode_modificator FOREIGN KEY (id_modificator) REFERENCES user_(id);

ALTER TABLE holiday
ADD COLUMN id_creator int;
ALTER TABLE holiday
ADD COLUMN id_modificator int;
ALTER TABLE holiday
ADD CONSTRAINT FK_holiday_creator FOREIGN KEY (id_creator) REFERENCES user_(id);
ALTER TABLE holiday
ADD CONSTRAINT FK_holiday_modificator FOREIGN KEY (id_modificator) REFERENCES user_(id);

ALTER TABLE ical_import
ADD COLUMN id_creator int;
ALTER TABLE ical_import
ADD COLUMN id_modificator int;
ALTER TABLE ical_import
ADD CONSTRAINT FK_icalimport_creator FOREIGN KEY (id_creator) REFERENCES user_(id);
ALTER TABLE ical_import
ADD CONSTRAINT FK_icalimport_modificator FOREIGN KEY (id_modificator) REFERENCES user_(id);

ALTER TABLE location
ADD COLUMN id_creator int;
ALTER TABLE location
ADD COLUMN id_modificator int;
ALTER TABLE location
ADD CONSTRAINT FK_location_creator FOREIGN KEY (id_creator) REFERENCES user_(id);
ALTER TABLE location
ADD CONSTRAINT FK_location_modificator FOREIGN KEY (id_modificator) REFERENCES user_(id);
