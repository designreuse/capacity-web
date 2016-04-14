ALTER TABLE absence
ADD COLUMN icalimport_id int;
ALTER TABLE absence
ADD CONSTRAINT FK_absence_icalimport FOREIGN KEY (icalimport_id) REFERENCES ical_import(id);

DELETE FROM absence WHERE externalId IS NOT NULL;
