-- Initial database

# --- !Ups

CREATE TABLE invites (
  id INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  email VARCHAR(255),
  hash VARCHAR(255),
  createdAt TIMESTAMP NOT NULL
) ENGINE=InnoDB CHARACTER SET=utf8;

CREATE TABLE objectives (
  id INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  userId INT,
  parentId INT,
  name VARCHAR(255),
  description VARCHAR(2048),
  createdAt TIMESTAMP,
  updatedAt TIMESTAMP
) ENGINE=InnoDB CHARACTER SET=utf8;

CREATE TABLE users (
  id INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255),
  username VARCHAR(255),
  email VARCHAR(255),
  password VARCHAR(255),
  createdAt TIMESTAMP NOT NULL,
  updatedAt TIMESTAMP
) ENGINE=InnoDB CHARACTER SET=utf8;

CREATE TABLE taggroups (
  id INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  taggroup VARCHAR(255),
  createdAt TIMESTAMP NOT NULL
) ENGINE=InnoDB CHARACTER SET=utf8;

CREATE TABLE tags (
  id INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  taggroupid INT(11),
  tag VARCHAR(255),
  createdAt TIMESTAMP NOT NULL
) ENGINE=InnoDB CHARACTER SET=utf8;

CREATE TABLE objectivetags (
  objectiveid INT(11) NOT NULL,
  tagid INT(11) NOT NULL
) ENGINE=InnoDB CHARACTER SET=utf8;


-- Seed data
INSERT INTO users(name, username, email, password, createdAt, updatedAt)
  VALUES ('ClearFit Inc', 'clearfit', '', '', NOW(), NOW());

INSERT INTO users(name, username, email, password, createdAt, updatedAt)
  VALUES ('Don Kittle', 'don', 'don@clearfit.com', 'DC724AF18FBDD4E59189F5FE768A5F8311527050', NOW(), NOW());

INSERT INTO objectives(userId, parentId, name, description, createdAt,updatedAt)
  VALUES(1, NULL, 'Raise the bar for talent', '', NOW(), NOW());

INSERT INTO objectives(userId, parentId, name, description, createdAt,updatedAt)
VALUES(1, NULL, 'Support and develop future leaders', '', NOW(), NOW());

INSERT INTO objectives(userId, parentId, name, description, createdAt,updatedAt)
VALUES(1, NULL, 'Reinforce the culture', '', NOW(), NOW());

INSERT INTO objectives(userId, parentId, name, description, createdAt,updatedAt)
VALUES(1, NULL, 'Deliver ideal outcomes', '', NOW(), NOW());

INSERT INTO objectives(userId, parentId, name, description, createdAt,updatedAt)
VALUES(1, NULL, 'Provide superior service', '', NOW(), NOW());

INSERT INTO objectives(userId, parentId, name, description, createdAt,updatedAt)
VALUES(1, NULL, 'Be top of mind for customers', '', NOW(), NOW());

INSERT INTO objectives(userId, parentId, name, description, createdAt,updatedAt)
VALUES(1, NULL, 'Be thought leaders', '', NOW(), NOW());

INSERT INTO objectives(userId, parentId, name, description, createdAt,updatedAt)
VALUES(1, NULL, 'Build a community of advocates', '', NOW(), NOW());

INSERT INTO objectives(userId, parentId, name, description, createdAt,updatedAt)
VALUES(1, NULL, 'Extend reach', '', NOW(), NOW());


INSERT INTO taggroups(taggroup, createdAt)
VALUES ('Period', NOW());

INSERT INTO taggroups(taggroup, createdAt)
VALUES ('Department', NOW());

INSERT INTO taggroups(taggroup, createdAt)
VALUES ('Tag', NOW());


# --- !Downs
DROP TABLE IF EXISTS invites;

DROP TABLE IF EXISTS objectives;

DROP TABLE IF EXISTS users;

DROP TABLE IF EXISTS objectivetags;

DROP TABLE IF EXISTS tags;

DROP TABLE IF EXISTS taggroups;

