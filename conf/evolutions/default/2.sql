-- Initial database

# --- !Ups


CREATE TABLE taggroups (
  id INT ,
  taggroup VARCHAR(255),
  createdAt TIMESTAMP NOT NULL
);

CREATE TABLE tags (
  id INT ,
  taggroupid INT ,
  tag VARCHAR(255),
  createdAt TIMESTAMP NOT NULL
);

CREATE TABLE objectivetags (
  objectiveid INT ,
  tagid INT
);

CREATE SEQUENCE taggroups_id_seq
START WITH 1
INCREMENT BY 1
NO MINVALUE
NO MAXVALUE
CACHE 1;

ALTER SEQUENCE taggroups_id_seq OWNED BY taggroups.id;
ALTER TABLE ONLY taggroups ALTER COLUMN id SET DEFAULT nextval('taggroups_id_seq'::regclass);

CREATE SEQUENCE tags_id_seq
START WITH 1
INCREMENT BY 1
NO MINVALUE
NO MAXVALUE
CACHE 1;

ALTER SEQUENCE tags_id_seq OWNED BY tags.id;
ALTER TABLE ONLY tags ALTER COLUMN id SET DEFAULT nextval('tags_id_seq'::regclass);

---- Seed data
INSERT INTO taggroups(taggroup, createdAt)
VALUES ('Period', NOW());

INSERT INTO taggroups(taggroup, createdAt)
VALUES ('Department', NOW());

INSERT INTO taggroups(taggroup, createdAt)
VALUES ('Tag', NOW());


# --- !Downs
DROP TABLE IF EXISTS objectivetags;

DROP TABLE IF EXISTS tags;
DROP SEQUENCE IF EXISTS tags_id_seq;

DROP TABLE IF EXISTS taggroups;
DROP SEQUENCE IF EXISTS taggroups_id_seq;

