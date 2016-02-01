INSERT INTO employee (id, created, modified, name, email, start, "end", location_id, color, velocity) VALUES ( 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'User 2015 complete', '2015@complete', '2015-01-01', '2015-12-31', NULL, '#ff0000', '50');
INSERT INTO employee (id, created, modified, name, email, start, "end", location_id, color, velocity) VALUES ( 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'User always', 'always', NULL, NULL, NULL, '#ff0000', '50');
INSERT INTO employee (id, created, modified, name, email, start, "end", location_id, color, velocity) VALUES ( 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'User until end september', 'until@september', NULL, '2015-09-30', NULL, '#ff0000', '50');
INSERT INTO employee (id, created, modified, name, email, start, "end", location_id, color, velocity) VALUES ( 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'User from start october', 'from@october', '2015-10-01', NULL, NULL, '#ff0000', '50');
INSERT INTO employee (id, created, modified, name, email, start, "end", location_id, color, velocity) VALUES ( 5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'User until end october', 'until@october', NULL, '2015-10-31', NULL, '#ff0000', '50');
INSERT INTO employee (id, created, modified, name, email, start, "end", location_id, color, velocity) VALUES ( 6, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'User from start november', 'from@november', '2015-11-01', NULL, NULL, '#ff0000', '50');
INSERT INTO employee (id, created, modified, name, email, start, "end", location_id, color, velocity) VALUES ( 7, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'User until mid october', 'until.mid@october', NULL, '2015-10-15', NULL, '#ff0000', '50');
INSERT INTO employee (id, created, modified, name, email, start, "end", location_id, color, velocity) VALUES ( 8, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'User from mid october', 'from.mid@october', '2015-10-15', NULL, NULL, '#ff0000', '50');
INSERT INTO employee (id, created, modified, name, email, start, "end", location_id, color, velocity) VALUES ( 9, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'User complete october', 'october@complete', '2015-10-01', '2015-10-31', NULL, '#ff0000', '50');
INSERT INTO employee (id, created, modified, name, email, start, "end", location_id, color, velocity) VALUES (10, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'User partial october', 'october@partial', '2015-10-10', '2015-10-20', NULL, '#ff0000', '50');
INSERT INTO employee (id, created, modified, name, email, start, "end", location_id, color, velocity) VALUES (13, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'User from jan until end september', 'until@september', '2015-01-01', '2015-09-30', NULL, '#ff0000', '50');
INSERT INTO employee (id, created, modified, name, email, start, "end", location_id, color, velocity) VALUES (14, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'User from start october until dec', 'from@october', '2015-10-01', '2015-12-31', NULL, '#ff0000', '50');
INSERT INTO employee (id, created, modified, name, email, start, "end", location_id, color, velocity) VALUES (15, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'User from jan until end october', 'until@october', '2015-01-01', '2015-10-31', NULL, '#ff0000', '50');
INSERT INTO employee (id, created, modified, name, email, start, "end", location_id, color, velocity) VALUES (16, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'User from start november until dec', 'from@november', '2015-11-01', '2015-12-31', NULL, '#ff0000', '50');
INSERT INTO employee (id, created, modified, name, email, start, "end", location_id, color, velocity) VALUES (17, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'User from jan until mid october', 'until.mid@october', '2015-01-01', '2015-10-15', NULL, '#ff0000', '50');
INSERT INTO employee (id, created, modified, name, email, start, "end", location_id, color, velocity) VALUES (18, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'User from mid october until dec', 'from.mid@november', '2015-10-15', '2015-12-31', NULL, '#ff0000', '50');

INSERT INTO absence (id, created, modified, employee_id, start, "end", reason, externalId) VALUES (1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, '2015-02-01', '2015-02-28', 'Whole February', NULL);
