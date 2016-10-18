ALTER TABLE ical_import
ADD auth VARCHAR(20) DEFAULT 'NONE',
username VARCHAR(50),
password VARCHAR(50)
GO

UPDATE ical_import
SET auth = 'NONE' WHERE auth IS NULL
GO

ALTER TABLE ical_import
ALTER COLUMN auth VARCHAR(20) NOT NULL
GO
