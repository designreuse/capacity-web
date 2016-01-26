CREATE TABLE user_ (
  id IDENTITY NOT NULL,
  created datetime DEFAULT NULL,
  modified datetime DEFAULT NULL,
  email varchar(255) NOT NULL,
  login varchar(63) NOT NULL,
  name varchar(255) NOT NULL,
  password varchar(40) NOT NULL,
  id_creator int DEFAULT NULL,
  id_modificator int DEFAULT NULL
);

ALTER TABLE user_
ADD CONSTRAINT FK_user_creator FOREIGN KEY (id_creator) REFERENCES user_ (id);
ALTER TABLE user_
ADD CONSTRAINT FK_user_modificator FOREIGN KEY (id_modificator) REFERENCES user_ (id);

CREATE TABLE role (
  id IDENTITY NOT NULL,
  created datetime DEFAULT NULL,
  modified datetime DEFAULT NULL,
  name varchar(255) NOT NULL,
  id_creator int DEFAULT NULL,
  id_modificator int DEFAULT NULL
);

ALTER TABLE role
ADD CONSTRAINT FK_role_creator FOREIGN KEY (id_creator) REFERENCES user_ (id);
ALTER TABLE role
ADD CONSTRAINT FK_role_modificator FOREIGN KEY (id_modificator) REFERENCES user_ (id);

CREATE TABLE role_permissions (
  id_role int NOT NULL,
  permission varchar(255) DEFAULT NULL
);

ALTER TABLE role_permissions
ADD CONSTRAINT FK_rolepermission_role FOREIGN KEY (id_role) REFERENCES role (id);

CREATE TABLE user_role (
  id_user int NOT NULL,
  id_role int NOT NULL
);

ALTER TABLE user_role
ADD CONSTRAINT FK_userrole_role FOREIGN KEY (id_role) REFERENCES role (id);
ALTER TABLE user_role
ADD CONSTRAINT FK_userrole_user FOREIGN KEY (id_user) REFERENCES user_ (id);

INSERT INTO role(name,created,modified,id_creator,id_modificator)
 VALUES ('Administrators',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,NULL,NULL),
        ('Users',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,NULL,NULL);

INSERT INTO role_permissions(id_role,permission)
 VALUES ((SELECT id FROM role WHERE name = 'Administrators'),'SHOW_USERS'),
        ((SELECT id FROM role WHERE name = 'Administrators'),'ADMIN_USERS'),
        ((SELECT id FROM role WHERE name = 'Administrators'),'SHOW_CAPACITY'),
        ((SELECT id FROM role WHERE name = 'Administrators'),'ADMIN_CAPACITY'),
        ((SELECT id FROM role WHERE name = 'Users'),'SHOW_USERS'),
        ((SELECT id FROM role WHERE name = 'Users'),'SHOW_CAPACITY');

INSERT INTO user_(name,login,password,email,created,modified,id_creator,id_modificator)
 VALUES ('Administrator','admin','d033e22ae348aeb5660fc2140aec35850c4da997','admin@localhost',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,NULL,NULL);

INSERT INTO user_role(id_user,id_role)
 VALUES ((SELECT id FROM user_ WHERE name = 'Administrator'),(SELECT id FROM role WHERE name = 'Administrators'));