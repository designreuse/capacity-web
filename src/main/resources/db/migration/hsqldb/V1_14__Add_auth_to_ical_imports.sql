ALTER TABLE ical_import
ADD COLUMN auth VARCHAR(20) DEFAULT 'NONE';

ALTER TABLE ical_import
ADD COLUMN username VARCHAR(50);

ALTER TABLE ical_import
ADD COLUMN password VARCHAR(50);

UPDATE ical_import
SET auth = 'NONE' WHERE auth IS NULL;

ALTER TABLE ical_import
ALTER COLUMN auth SET NOT NULL;
