-- Table for the feeds
CREATE TABLE IF NOT EXISTS feed
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    name       VARCHAR(255) NOT NULL,
    description VARCHAR(255), -- Optional <- it can also be null
    nutritionalValue INT(255)
    );

-- Enumeration for the gender of the horses - male/female
CREATE TYPE IF NOT EXISTS genderEnum AS ENUM ('male', 'female');

-- Table for the horses
CREATE TABLE IF NOT EXISTS horse
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255), -- Optional <- it can also be null
    dateBorn DATE NOT NULL,
    gender genderEnum NOT NULL,
    food BIGINT REFERENCES feed
    -- Delete every reference of this attribute if this gets deleted
    ON UPDATE CASCADE
    ON DELETE SET NULL,
    mother BIGINT REFERENCES horse
    -- Delete every reference of this attribute if this gets deleted
    ON UPDATE CASCADE
    ON DELETE SET NULL,
    father BIGINT REFERENCES horse
    ON UPDATE CASCADE
    ON DELETE SET NULL
    );
