CREATE TABLE user_ (
  id int NOT NULL IDENTITY,
  created datetime DEFAULT NULL,
  modified datetime DEFAULT NULL,
  email varchar(255) NOT NULL,
  login varchar(63) NOT NULL,
  name varchar(255) NOT NULL,
  password varchar(40) NOT NULL,
  id_creator int DEFAULT NULL,
  id_modificator int DEFAULT NULL,
  PRIMARY KEY (id),
  CONSTRAINT FK_user_creator FOREIGN KEY (id_creator) REFERENCES user_ (id),
  CONSTRAINT FK_user_modificator FOREIGN KEY (id_modificator) REFERENCES user_ (id)
)
GO

CREATE INDEX FK_user_creator_IX ON user_(id_creator)
GO
CREATE INDEX FK_user_modificator_IX ON user_(id_modificator)
GO

CREATE TABLE role (
  id int NOT NULL IDENTITY,
  created datetime DEFAULT NULL,
  modified datetime DEFAULT NULL,
  name varchar(255) NOT NULL,
  id_creator int DEFAULT NULL,
  id_modificator int DEFAULT NULL,
  PRIMARY KEY (id),
  CONSTRAINT FK_role_creator FOREIGN KEY (id_creator) REFERENCES user_ (id),
  CONSTRAINT FK_role_modificator FOREIGN KEY (id_modificator) REFERENCES user_ (id)
)
GO

CREATE INDEX FK_role_creator_IX ON role(id_creator)
GO
CREATE INDEX FK_role_modificator_IX ON role(id_modificator)
GO

CREATE TABLE role_permissions (
  id_role int NOT NULL,
  permission varchar(255) DEFAULT NULL,
  CONSTRAINT FK_rolepermission_role FOREIGN KEY (id_role) REFERENCES role (id)
)
GO

CREATE INDEX FK_rolepermission_role_IX ON role_permssion(id_role)
GO

CREATE TABLE user_role (
  id_user int NOT NULL,
  id_role int NOT NULL,
  CONSTRAINT FK_userrole_role FOREIGN KEY (id_role) REFERENCES role (id),
  CONSTRAINT FK_userrole_user FOREIGN KEY (id_user) REFERENCES user_ (id)
)
GO

CREATE INDEX FK_userrole_role ON user_role(id_role)
GO
CREATE INDEX FK_userrole_user ON user_role(id_user)
GO


INSERT INTO role(name,created,modified,id_creator,id_modificator)
 VALUES ('Administrators',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,NULL,NULL),
        ('Users',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,NULL,NULL)
GO

INSERT INTO role_permissions(id_role,permission)
 VALUES ((SELECT id FROM role WHERE name = 'Administrators'),'SHOW_USERS'),
        ((SELECT id FROM role WHERE name = 'Administrators'),'ADMIN_USERS'),
        ((SELECT id FROM role WHERE name = 'Administrators'),'SHOW_CAPACITY'),
        ((SELECT id FROM role WHERE name = 'Administrators'),'ADMIN_CAPACITY'),
        ((SELECT id FROM role WHERE name = 'Users'),'SHOW_USERS'),
        ((SELECT id FROM role WHERE name = 'Users'),'SHOW_CAPACITY')
GO

INSERT INTO user_(name,login,password,email,created,modified,id_creator,id_modificator)
 VALUES ('Administrator','admin','d033e22ae348aeb5660fc2140aec35850c4da997','admin@localhost',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,NULL,NULL)
GO

INSERT INTO user_role(id_user,id_role)
 VALUES ((SELECT id FROM user_ WHERE name = 'Administrator'),(SELECT id FROM role WHERE name = 'Administrators'))
GO
