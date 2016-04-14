ALTER TABLE absence
ADD COLUMN icalimport_id int(11),
ADD INDEX FK_absence_icalimport_IX (id_creator),
ADD CONSTRAINT FK_absence_icalimport FOREIGN KEY (icalimport_id) REFERENCES ical_import(id);

DELETE FROM absence WHERE externalId IS NOT NULL;
