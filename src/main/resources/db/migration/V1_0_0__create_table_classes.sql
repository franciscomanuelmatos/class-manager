CREATE SEQUENCE classes_seq;

CREATE TABLE IF NOT EXISTS classes (
    id bigint DEFAULT nextval('classes_seq') PRIMARY KEY,
    name varchar(255) NOT NULL,
    date timestamp NOT NULL,
    capacity int NOT NULL
);