-- delete test data
DELETE FROM Invoices;
DELETE FROM Bills;
DELETE FROM RatedCDRs;
DELETE FROM CDRs;
DELETE FROM RatingRules;
DELETE FROM DestinationZones;
DELETE FROM Services;
DELETE FROM ServicePackages;
DELETE FROM Profiles;
DELETE FROM RatePlans;
DELETE FROM Customers;



-- Insert Customers
INSERT INTO Customers (customer_id, full_name, email, phone_number, address) VALUES
(1, 'John Doe', 'john.doe@email.com', '+1234567890', '123 Main St'),
(2, 'Jane Smith', 'jane.smith@email.com', '+9876543210', '456 Oak Ave'),
(3, 'Bob Wilson', 'bob.wilson@email.com', '+1122334455', '789 Pine Rd'),
(4, 'Alice Brown', 'alice.brown@email.com', '+5566778899', '321 Elm St');

-- Insert Rate Plans
INSERT INTO RatePlans (rateplan_id, plan_name, description, monthly_fee) VALUES
(1, 'Basic Plan', 'Basic voice, sms, and data plan', 29.99),
(2, 'Premium Plan', 'Premium voice, sms, and data plan with better rates', 49.99);

-- Insert Profiles
INSERT INTO Profiles (profile_id, customer_id, rateplan_id, activation_date, status) VALUES
(1, 1, 1, '2024-01-01', 'active'),
(2, 2, 1, '2024-01-01', 'active'),
(3, 3, 2, '2024-01-01', 'active'),
(4, 4, 2, '2024-01-01', 'active');

-- Insert Service Packages
INSERT INTO ServicePackages (package_id, rateplan_id, package_name, description) VALUES
(1, 1, 'Basic Voice Package', 'Basic voice package for Rate Plan 1'),
(2, 1, 'Basic SMS Package', 'Basic SMS package for Rate Plan 1'),
(3, 1, 'Basic Data Package', 'Basic data package for Rate Plan 1'),
(4, 2, 'Premium Voice Package', 'Premium voice package for Rate Plan 2'),
(5, 2, 'Premium SMS Package', 'Premium SMS package for Rate Plan 2'),
(6, 2, 'Premium Data Package', 'Premium data package for Rate Plan 2');

-- Insert Services
-- Services for Basic Plan (RatePlan ID 1)
INSERT INTO Services (service_id, package_id, service_type, price_per_unit, free_units) VALUES
(1, 1, 'voice', 0.10, 100),  -- Basic Voice (part of Basic Voice Package)
(2, 2, 'sms', 0.05, 50),    -- Basic SMS (part of Basic SMS Package)
(3, 3, 'data', 0.02, 1024); -- Basic Data (part of Basic Data Package)

-- Services for Premium Plan (RatePlan ID 2)
INSERT INTO Services (service_id, package_id, service_type, price_per_unit, free_units) VALUES
(4, 4, 'voice', 0.08, 200),  -- Premium Voice (part of Premium Voice Package)
(5, 5, 'sms', 0.03, 100),    -- Premium SMS (part of Premium SMS Package)
(6, 6, 'data', 0.015, 2048); -- Premium Data (part of Premium Data Package)

-- Insert Destination Zones
INSERT INTO DestinationZones (destination_zone_id, zone_name, country_code) VALUES
(1, 'US', '12'),  -- US numbers starting with +12
(2, 'US', '98'),  -- US numbers starting with +98
(3, 'US', '11'),  -- US numbers starting with +11
(4, 'US', '55'),  -- US numbers starting with +55
(5, 'UK', '44');  -- UK numbers starting with +44

-- Insert Rating Rules
-- Rating Rules for Basic Plan (RatePlan ID 1)
-- Voice
INSERT INTO RatingRules (rating_rule_id, service_id, destination_zone_id, price_per_unit) VALUES
(1, 1, 1, 0.10),  -- Basic Voice US (+12)
(2, 1, 2, 0.10),  -- Basic Voice US (+98)
(3, 1, 3, 0.10),  -- Basic Voice US (+11)
(4, 1, 4, 0.10),  -- Basic Voice US (+55)
(5, 1, 5, 0.20);  -- Basic Voice UK (+44)

-- SMS
INSERT INTO RatingRules (rating_rule_id, service_id, destination_zone_id, price_per_unit) VALUES
(6, 2, 1, 0.05),  -- Basic SMS US (+12)
(7, 2, 2, 0.05),  -- Basic SMS US (+98)
(8, 2, 3, 0.05),  -- Basic SMS US (+11)
(9, 2, 4, 0.05),  -- Basic SMS US (+55)
(10, 2, 5, 0.10); -- Basic SMS UK (+44)

-- Data
INSERT INTO RatingRules (rating_rule_id, service_id, destination_zone_id, price_per_unit) VALUES
(11, 3, 1, 0.02), -- Basic Data US (+12)
(12, 3, 2, 0.02), -- Basic Data US (+98)
(13, 3, 3, 0.02), -- Basic Data US (+11)
(14, 3, 4, 0.02), -- Basic Data US (+55)
(15, 3, 5, 0.04); -- Basic Data UK (+44)

-- Rating Rules for Premium Plan (RatePlan ID 2)
-- Voice
INSERT INTO RatingRules (rating_rule_id, service_id, destination_zone_id, price_per_unit) VALUES
(16, 4, 1, 0.08), -- Premium Voice US (+12)
(17, 4, 2, 0.08), -- Premium Voice US (+98)
(18, 4, 3, 0.08), -- Premium Voice US (+11)
(19, 4, 4, 0.08), -- Premium Voice US (+55)
(20, 4, 5, 0.15); -- Premium Voice UK (+44)

-- SMS
INSERT INTO RatingRules (rating_rule_id, service_id, destination_zone_id, price_per_unit) VALUES
(21, 5, 1, 0.03), -- Premium SMS US (+12)
(22, 5, 2, 0.03), -- Premium SMS US (+98)
(23, 5, 3, 0.03), -- Premium SMS US (+11)
(24, 5, 4, 0.03), -- Premium SMS US (+55)
(25, 5, 5, 0.06); -- Premium SMS UK (+44)

-- Data
INSERT INTO RatingRules (rating_rule_id, service_id, destination_zone_id, price_per_unit) VALUES
(26, 6, 1, 0.015), -- Premium Data US (+12)
(27, 6, 2, 0.015), -- Premium Data US (+98)
(28, 6, 3, 0.015), -- Premium Data US (+11)
(29, 6, 4, 0.015), -- Premium Data US (+55)
(30, 6, 5, 0.03); -- Premium Data UK (+44)


-- select all tables
SELECT * FROM bills;
SELECT * FROM cdrs;
SELECT * FROM customers;
SELECT * FROM destinationzones;
SELECT * FROM invoices;
SELECT * FROM profiles;
SELECT * FROM ratedcdrs;
SELECT * FROM rateplans;
SELECT * FROM ratingrules;
SELECT * FROM servicepackages;
SELECT * FROM services;




