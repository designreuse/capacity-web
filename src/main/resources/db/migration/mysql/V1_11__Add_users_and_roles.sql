CREATE TABLE user_ (
  id int(11) NOT NULL AUTO_INCREMENT,
  created datetime DEFAULT NULL,
  modified datetime DEFAULT NULL,
  email varchar(255) NOT NULL,
  login varchar(63) NOT NULL,
  name varchar(255) NOT NULL,
  password varchar(40) NOT NULL,
  id_creator int(11) DEFAULT NULL,
  id_modificator int(11) DEFAULT NULL,
  PRIMARY KEY (id),
  KEY FK_user_creator_IX (id_creator),
  KEY FK_user_modificator_IX (id_modificator),
  CONSTRAINT FK_user_creator FOREIGN KEY (id_creator) REFERENCES user_ (id),
  CONSTRAINT FK_user_modificator FOREIGN KEY (id_modificator) REFERENCES user_ (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE role (
  id int(11) NOT NULL AUTO_INCREMENT,
  created datetime DEFAULT NULL,
  modified datetime DEFAULT NULL,
  name varchar(255) NOT NULL,
  id_creator int(11) DEFAULT NULL,
  id_modificator int(11) DEFAULT NULL,
  PRIMARY KEY (id),
  KEY FK_role_creator_IX (id_creator),
  KEY FK_role_modificator_IX (id_modificator),
  CONSTRAINT FK_role_creator FOREIGN KEY (id_creator) REFERENCES user_ (id),
  CONSTRAINT FK_role_modificator FOREIGN KEY (id_modificator) REFERENCES user_ (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE role_permissions (
  id_role int(11) NOT NULL,
  permission varchar(255) DEFAULT NULL,
  KEY KF_rolepermission_role_IX (id_role),
  CONSTRAINT KF_rolepermission_role FOREIGN KEY (id_role) REFERENCES role (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE user_role (
  id_user int(11) NOT NULL,
  id_role int(11) NOT NULL,
  KEY FK_userrole_role_IX (id_role),
  KEY FK_userrole_user_IX (id_user),
  CONSTRAINT FK_userrole_role FOREIGN KEY (id_role) REFERENCES role (id),
  CONSTRAINT FK_userrole_user FOREIGN KEY (id_user) REFERENCES user_ (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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