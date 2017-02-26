CREATE TABLE IF NOT EXISTS users (
  user_id int PRIMARY KEY AUTO_INCREMENT,
  name TEXT NOT NULL,
  email VARCHAR(100) NOT NULL UNIQUE,
  password TEXT NOT NULL,
  active BOOLEAN NOT NULL DEFAULT FALSE,
  api_key VARCHAR(300) UNIQUE,
  is_admin BOOLEAN NOT NULL,
  account_id VARCHAR(300) UNIQUE
);

CREATE TABLE IF NOT EXISTS purchased_tickets (
  purchased_ticket_id int PRIMARY KEY AUTO_INCREMENT,
  user_id int REFERENCES users(user_id),
  amount TEXT NOT NULL,
  currency TEXT NOT NULL,
  origin TEXT NOT NULL,
  destination TEXT NOT NULL
);

INSERT IGNORE INTO users (name, email, password, active, api_key, is_admin, account_id)
    VALUES ('admin', 'admin@admin.com', 'secret', true, 'FD9A70B3032C179A49EE7', true, '');
