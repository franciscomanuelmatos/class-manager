CREATE SEQUENCE bookings_seq;

CREATE TABLE IF NOT EXISTS bookings (
    id bigint DEFAULT nextval('bookings_seq') PRIMARY KEY ,
    name varchar(255) NOT NULL,
    class_id bigint NOT NULL,
    CONSTRAINT fk_classes FOREIGN KEY (class_id) REFERENCES classes(id)
);
