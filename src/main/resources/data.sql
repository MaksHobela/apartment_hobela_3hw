USE apartment_db;

INSERT INTO administrator (admin_id) VALUES (1);

INSERT INTO user (first_name, last_name, birth_date, admin_id) VALUES
('John', 'Doe', '1990-05-15', 1),
('Jane', 'Smith', '1995-08-22', 1),
('Alice', 'Johnson', '1992-12-01', 1);

INSERT INTO host (user_id) VALUES (1);
INSERT INTO guest (user_id) VALUES (2), (3);

INSERT INTO location (postalCode, city, country) VALUES
('79000', 'Lviv', 'Ukraine'),
('01001', 'Kyiv', 'Ukraine');

INSERT INTO apartment (apartmentName, description, pricePerNight, propertyType, dateOfReg, street, postalCode, hostID, admin_id) VALUES
('Central Loft', 'Modern apartment in the city center', 850.00, 'Apartment', '2026-03-01', 'Rynok Sq 5', '79000', 1, 1),
('Kyiv Studio', 'Cozy studio near metro station', 1200.00, 'Studio', '2026-03-15', 'Khreshchatyk 10', '01001', 1, 1);

INSERT INTO apartment_amenities (apartment_id, amenity_name) VALUES
(1, 'Wi-Fi'), (1, 'Air Conditioning'),
(2, 'Wi-Fi'), (2, 'TV'), (2, 'Kitchen');

INSERT INTO booking (guest_id, apartment_id, admin_id) VALUES
(2, 1, 1),
(3, 2, 1);

INSERT INTO review (booking_id) VALUES (1), (2);
