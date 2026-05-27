DROP DATABASE IF EXISTS apartment_db;
CREATE DATABASE IF NOT EXISTS apartment_db;
USE apartment_db;

CREATE TABLE administrator (
    admin_id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY
) ENGINE = InnoDB;


CREATE TABLE user (
    user_id    INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50)  NOT NULL,
    last_name  VARCHAR(50)  NOT NULL,
    birth_date DATE         NOT NULL,
    admin_id   INT UNSIGNED,
    CONSTRAINT fk_user_admin
        FOREIGN KEY (admin_id) REFERENCES administrator (admin_id)
        ON DELETE SET NULL
) ENGINE = InnoDB;

CREATE TABLE host (
    user_id INT UNSIGNED PRIMARY KEY,
    CONSTRAINT fk_host_user
        FOREIGN KEY (user_id) REFERENCES user (user_id)
        ON DELETE CASCADE
) ENGINE = InnoDB;

CREATE TABLE guest (
    user_id INT UNSIGNED PRIMARY KEY,
    CONSTRAINT fk_guest_user
        FOREIGN KEY (user_id) REFERENCES user (user_id)
        ON DELETE CASCADE
) ENGINE = InnoDB;

CREATE TABLE location (
    postalCode VARCHAR(20) PRIMARY KEY,
    city       VARCHAR(50) NOT NULL,
    country    VARCHAR(50) NOT NULL
) ENGINE = InnoDB;

CREATE TABLE apartment (
    apartmentID   INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    apartmentName VARCHAR(100)   NOT NULL,
    description   TEXT,
    pricePerNight DECIMAL(10, 2) NOT NULL,
    propertyType  VARCHAR(50)    NOT NULL,
    dateOfReg     DATE           NOT NULL,
    street        VARCHAR(100),
    postalCode    VARCHAR(20),
    overallRating DECIMAL(3, 2)  DEFAULT NULL,
    hostID        INT UNSIGNED   NOT NULL,
    admin_id      INT UNSIGNED,
    CONSTRAINT fk_apartment_host
        FOREIGN KEY (hostID)     REFERENCES host (user_id),
    CONSTRAINT fk_apartment_location
        FOREIGN KEY (postalCode) REFERENCES location (postalCode)
        ON UPDATE CASCADE,
    CONSTRAINT fk_apartment_admin
        FOREIGN KEY (admin_id)   REFERENCES administrator (admin_id)
        ON DELETE SET NULL
) ENGINE = InnoDB;

CREATE TABLE apartment_amenities (
    apartment_id INT UNSIGNED NOT NULL,
    amenity_name VARCHAR(50)  NOT NULL,
    PRIMARY KEY (apartment_id, amenity_name),
    CONSTRAINT fk_amenities_apartment
        FOREIGN KEY (apartment_id) REFERENCES apartment (apartmentID)
        ON DELETE CASCADE
) ENGINE = InnoDB;

CREATE TABLE booking (
    booking_id   INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    guest_id     INT UNSIGNED NOT NULL,
    apartment_id INT UNSIGNED NOT NULL,
    admin_id     INT UNSIGNED,
    CONSTRAINT fk_booking_guest
        FOREIGN KEY (guest_id)     REFERENCES guest (user_id),
    CONSTRAINT fk_booking_apartment
        FOREIGN KEY (apartment_id) REFERENCES apartment (apartmentID),
    CONSTRAINT fk_booking_admin
        FOREIGN KEY (admin_id)     REFERENCES administrator (admin_id)
        ON DELETE SET NULL
) ENGINE = InnoDB;

CREATE TABLE review (
    review_id  INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    booking_id INT UNSIGNED NOT NULL UNIQUE,
    CONSTRAINT fk_review_booking
        FOREIGN KEY (booking_id) REFERENCES booking (booking_id)
        ON DELETE CASCADE
) ENGINE = InnoDB;