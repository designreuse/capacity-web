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

INSERT INTO working_hours (id, dayOfWeek, start, "end", employee_id) VALUES ( 1, 2, '08:00:00', '16:00:00', 1);
INSERT INTO working_hours (id, dayOfWeek, start, "end", employee_id) VALUES ( 2, 2, '08:00:00', '16:00:00', 2);
INSERT INTO working_hours (id, dayOfWeek, start, "end", employee_id) VALUES ( 3, 2, '08:00:00', '16:00:00', 3);
INSERT INTO working_hours (id, dayOfWeek, start, "end", employee_id) VALUES ( 5, 2, '08:00:00', '16:00:00', 5);
INSERT INTO working_hours (id, dayOfWeek, start, "end", employee_id) VALUES ( 7, 2, '08:00:00', '16:00:00', 7);
INSERT INTO working_hours (id, dayOfWeek, start, "end", employee_id) VALUES (13, 2, '08:00:00', '16:00:00', 13);
INSERT INTO working_hours (id, dayOfWeek, start, "end", employee_id) VALUES (15, 2, '08:00:00', '16:00:00', 15);

INSERT INTO ability(employee_id, name) VALUES (1, 'Working');
INSERT INTO ability(employee_id, name) VALUES (2, 'Working');
INSERT INTO ability(employee_id, name) VALUES (2, 'Relaxing');

INSERT INTO absence (id, created, modified, employee_id, start, "end", reason, externalId) VALUES (1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, '2015-02-01', '2015-02-28', 'Whole February', NULL);

INSERT INTO episode (id, created, modified, name, start, "end") VALUES (1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'The Wrath of Khan', '2015-01-01', '2015-06-30');
INSERT INTO episode (id, created, modified, name, start, "end") VALUES (2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Into Darkness', '2015-07-01', '2015-12-31');

INSERT INTO employee_episode (id, created, modified, employee_id, episode_id, velocity) VALUES (1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, 1, NULL);
INSERT INTO employee_episode (id, created, modified, employee_id, episode_id, velocity) VALUES (2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, 2, NULL);
INSERT INTO employee_episode (id, created, modified, employee_id, episode_id, velocity) VALUES (3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 2, 1, 33);
INSERT INTO employee_episode (id, created, modified, employee_id, episode_id, velocity) VALUES (4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 2, 2, 66);
INSERT INTO employee_episode (id, created, modified, employee_id, episode_id, velocity) VALUES (5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 3, 2, 15);
INSERT INTO employee_episode (id, created, modified, employee_id, episode_id, velocity) VALUES (6, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 4, 2, 15);

INSERT INTO ical_import (id, created, modified, name, url, lastImported) VALUES (1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Test', 'http://localhost', NULL);