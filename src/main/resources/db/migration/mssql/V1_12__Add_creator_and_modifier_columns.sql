ALTER TABLE absence
ADD id_creator int,
id_modificator int,
CONSTRAINT FK_absence_creator FOREIGN KEY (id_creator) REFERENCES user_(id),
CONSTRAINT FK_absence_modificator FOREIGN KEY (id_modificator) REFERENCES user_(id);
GO

CREATE INDEX FK_absence_creator_IX ON absence(id_creator)
GO
CREATE INDEX FK_absence_modificator_IX ON absence(id_modificator)
GO

ALTER TABLE employee
ADD id_creator int,
id_modificator int,
CONSTRAINT FK_employee_creator FOREIGN KEY (id_creator) REFERENCES user_(id),
CONSTRAINT FK_employee_modificator FOREIGN KEY (id_modificator) REFERENCES user_(id)
GO

CREATE INDEX FK_employee_creator_IX ON employee(id_creator)
GO
CREATE INDEX FK_employee_modificator_IX ON employee(id_modificator)
GO

ALTER TABLE employee_episode
ADD id_creator int,
id_modificator int,
CONSTRAINT FK_employeeepisode_creator FOREIGN KEY (id_creator) REFERENCES user_(id),
CONSTRAINT FK_employeeepisode_modificator FOREIGN KEY (id_modificator) REFERENCES user_(id)
GO

CREATE INDEX FK_employeeepisode_creator_IX ON employee_episode(id_creator)
GO
CREATE INDEX FK_employeeepisode_modificator_IX ON employee_episode(id_modificator)
GO

ALTER TABLE episode
ADD id_creator int,
id_modificator int,
CONSTRAINT FK_episode_creator FOREIGN KEY (id_creator) REFERENCES user_(id),
CONSTRAINT FK_episode_modificator FOREIGN KEY (id_modificator) REFERENCES user_(id)
GO

CREATE INDEX FK_episode_creator_IX ON episode(id_creator)
GO
CREATE INDEX FK_episode_modificator_IX ON episode(id_modificator)
GO

ALTER TABLE holiday
ADD id_creator int,
id_modificator int,
CONSTRAINT FK_holiday_creator FOREIGN KEY (id_creator) REFERENCES user_(id),
CONSTRAINT FK_holiday_modificator FOREIGN KEY (id_modificator) REFERENCES user_(id)
GO

CREATE INDEX FK_holiday_creator_IX ON holiday(id_creator)
GO
CREATE INDEX FK_holiday_modificator_IX ON holiday(id_modificator)
GO    
    
ALTER TABLE ical_import
ADD id_creator int,
id_modificator int,
CONSTRAINT FK_icalimport_creator FOREIGN KEY (id_creator) REFERENCES user_(id),
CONSTRAINT FK_icalimport_modificator FOREIGN KEY (id_modificator) REFERENCES user_(id)
GO

CREATE INDEX FK_icalimport_creator_IX ON ical_import(id_creator)
GO
CREATE INDEX FK_icalimport_modificator_IX ON ical_import(id_modificator)
GO

ALTER TABLE location
ADD id_creator int,
id_modificator int,
CONSTRAINT FK_location_creator FOREIGN KEY (id_creator) REFERENCES user_(id),
CONSTRAINT FK_location_modificator FOREIGN KEY (id_modificator) REFERENCES user_(id);

CREATE INDEX FK_location_creator_IX ON location(id_creator)
GO
CREATE INDEX FK_location_modificator_IX ON location(id_modificator)
GO
