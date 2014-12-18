-- Initial database

# --- !Ups

CREATE TABLE invites (
  id INT ,
  email VARCHAR(255),
  hash VARCHAR(255),
  createdAt TIMESTAMP NOT NULL
);

CREATE TABLE objectives (
  id INT ,
  userId INT,
  parentId INT,
  name VARCHAR,
  description VARCHAR,
  createdAt TIMESTAMP,
  updatedAt TIMESTAMP
);

CREATE TABLE users (
  id INT ,
  name VARCHAR(255),
  username VARCHAR(255),
  email VARCHAR(255),
  password VARCHAR(255),
  createdAt TIMESTAMP NOT NULL,
  updatedAt TIMESTAMP
);

CREATE SEQUENCE invites_id_seq
START WITH 1
INCREMENT BY 1
NO MINVALUE
NO MAXVALUE
CACHE 1;

ALTER SEQUENCE invites_id_seq OWNED BY invites.id;
ALTER TABLE ONLY invites ALTER COLUMN id SET DEFAULT nextval('invites_id_seq'::regclass);


CREATE SEQUENCE objectives_id_seq
  START WITH 1
  INCREMENT BY 1
  NO MINVALUE
  NO MAXVALUE
  CACHE 1;

ALTER SEQUENCE objectives_id_seq OWNED BY objectives.id;
ALTER TABLE ONLY objectives ALTER COLUMN id SET DEFAULT nextval('objectives_id_seq'::regclass);

CREATE SEQUENCE users_id_seq
START WITH 1
INCREMENT BY 1
NO MINVALUE
NO MAXVALUE
CACHE 1;

ALTER SEQUENCE users_id_seq OWNED BY users.id;
ALTER TABLE ONLY users ALTER COLUMN id SET DEFAULT nextval('users_id_seq'::regclass);

---- Seed data
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

# --- !Downs
DROP TABLE IF EXISTS invites;
DROP SEQUENCE IF EXISTS invites_id_seq;

DROP TABLE IF EXISTS objectives;
DROP SEQUENCE IF EXISTS objectives_id_seq;

DROP TABLE IF EXISTS users;
DROP SEQUENCE IF EXISTS users_id_seq;
