CREATE TABLE IF NOT EXISTS Todo (
  id INTEGER AUTO_INCREMENT,
  title varchar(255) NOT NULL,
  desc text,
  status VARCHAR(20) NOT NULL,
  date_created TIMESTAMP NOT NULL,
  date_updated TIMESTAMP,
  removed BOOLEAN,
  primary key(id)
);

INSERT INTO Todo(title, desc, status, date_created, removed)
VALUES (
  'Wash Car',
  'Buy new scrub brush and polising wax to wash car',
  'NOT_STARTED',
  CURRENT_TIMESTAMP,
  false
);

INSERT INTO Todo(title, desc, status, date_created, removed)
VALUES (
  'Clean Garage',
  'Remove boxes, dispose of electronics, and recylce batteries',
  'COMPLETED',
  CURRENT_TIMESTAMP,
  true
)