CREATE TABLE rate_limit_rule (
    ip VARCHAR(255) PRIMARY KEY,
    rate_limit INT NOT NULL,
    time_interval INT NOT NULL
) ENGINE=INNODB