CREATE SCHEMA SA;

CREATE TABLE users (
  user_id int PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
  name VARCHAR(100) NOT NULL,
  email VARCHAR(100) NOT NULL UNIQUE,
  password VARCHAR(100) NOT NULL,
  active BOOLEAN NOT NULL DEFAULT FALSE,
  api_key VARCHAR(300) UNIQUE,
  is_admin BOOLEAN NOT NULL,
  account_id VARCHAR(300) UNIQUE
);

CREATE TABLE purchased_tickets (
  purchased_ticket_id int PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
  user_id int REFERENCES users(user_id),
  amount VARCHAR(100) NOT NULL,
  currency VARCHAR(100) NOT NULL,
  origin VARCHAR(100) NOT NULL,
  destination VARCHAR(100) NOT NULL
);

INSERT INTO users (name, email, password, active, api_key, is_admin, account_id)
    VALUES ('admin', 'admin@admin.com', '*14E65567ABDB5135D0CFD9A70B3032C179A49EE7', true, 'FD9A70B3032C179A49EE7', true, '');

