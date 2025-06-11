-- V1__Create_users_table.sql
CREATE TABLE users
(
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    rfc          VARCHAR(13) UNIQUE,
    curp         VARCHAR(18) UNIQUE,
    username     VARCHAR(50)  NOT NULL UNIQUE,
    first_name   VARCHAR(100) NOT NULL,
    last_name    VARCHAR(100) NOT NULL,
    email        VARCHAR(150) NOT NULL UNIQUE,
    phone_number VARCHAR(15),
    birth_date   DATE,
    status       VARCHAR(20)  NOT NULL DEFAULT 'ACTIVE',
    created_by   VARCHAR(255),
    created_at   TIMESTAMP             DEFAULT CURRENT_TIMESTAMP,
    updated_by   VARCHAR(255),
    updated_at   TIMESTAMP             DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_username (username),
    INDEX idx_email (email),
    INDEX idx_rfc (rfc),
    INDEX idx_curp (curp),
    INDEX idx_status (status)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;
