ALTER TABLE absence
ADD COLUMN id_creator int(11),
ADD COLUMN id_modificator int(11),
ADD INDEX FK_absence_creator_IX (id_creator),
ADD INDEX FK_absence_modificator_IX (id_modificator),
ADD CONSTRAINT FK_absence_creator FOREIGN KEY (id_creator) REFERENCES user_(id),
ADD CONSTRAINT FK_absence_modificator FOREIGN KEY (id_modificator) REFERENCES user_(id);

ALTER TABLE employee
ADD COLUMN id_creator int(11),
ADD COLUMN id_modificator int(11),
ADD INDEX FK_employee_creator_IX (id_creator),
ADD INDEX FK_employee_modificator_IX (id_modificator),
ADD CONSTRAINT FK_employee_creator FOREIGN KEY (id_creator) REFERENCES user_(id),
ADD CONSTRAINT FK_employee_modificator FOREIGN KEY (id_modificator) REFERENCES user_(id);

ALTER TABLE employee_episode
ADD COLUMN id_creator int(11),
ADD COLUMN id_modificator int(11),
ADD INDEX FK_employeeepisode_creator_IX (id_creator),
ADD INDEX FK_employeeepisode_modificator_IX (id_modificator),
ADD CONSTRAINT FK_employeeepisode_creator FOREIGN KEY (id_creator) REFERENCES user_(id),
ADD CONSTRAINT FK_employeeepisode_modificator FOREIGN KEY (id_modificator) REFERENCES user_(id);

ALTER TABLE episode
ADD COLUMN id_creator int(11),
ADD COLUMN id_modificator int(11),
ADD INDEX FK_episode_creator_IX (id_creator),
ADD INDEX FK_episode_modificator_IX (id_modificator),
ADD CONSTRAINT FK_episode_creator FOREIGN KEY (id_creator) REFERENCES user_(id),
ADD CONSTRAINT FK_episode_modificator FOREIGN KEY (id_modificator) REFERENCES user_(id);

ALTER TABLE holiday
ADD COLUMN id_creator int(11),
ADD COLUMN id_modificator int(11),
ADD INDEX FK_holiday_creator_IX (id_creator),
ADD INDEX FK_holiday_modificator_IX (id_modificator),
ADD CONSTRAINT FK_holiday_creator FOREIGN KEY (id_creator) REFERENCES user_(id),
ADD CONSTRAINT FK_holiday_modificator FOREIGN KEY (id_modificator) REFERENCES user_(id);

ALTER TABLE ical_import
ADD COLUMN id_creator int(11),
ADD COLUMN id_modificator int(11),
ADD INDEX FK_icalimport_creator_IX (id_creator),
ADD INDEX FK_icalimport_modificator_IX (id_modificator),
ADD CONSTRAINT FK_icalimport_creator FOREIGN KEY (id_creator) REFERENCES user_(id),
ADD CONSTRAINT FK_icalimport_modificator FOREIGN KEY (id_modificator) REFERENCES user_(id);

ALTER TABLE location
ADD COLUMN id_creator int(11),
ADD COLUMN id_modificator int(11),
ADD INDEX FK_location_creator_IX (id_creator),
ADD INDEX FK_location_modificator_IX (id_modificator),
ADD CONSTRAINT FK_location_creator FOREIGN KEY (id_creator) REFERENCES user_(id),
ADD CONSTRAINT FK_location_modificator FOREIGN KEY (id_modificator) REFERENCES user_(id);
