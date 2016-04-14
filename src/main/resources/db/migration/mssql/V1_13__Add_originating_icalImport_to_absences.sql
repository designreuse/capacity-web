ALTER TABLE absence
ADD icalimport_id int,
CONSTRAINT FK_absence_icalimport FOREIGN KEY (icalimport_id) REFERENCES ical_import(id)
GO

CREATE INDEX FK_absence_icalimport_IX ON absence(icalimport_id)
GO

DELETE FROM absence WHERE externalId IS NOT NULL
GO
