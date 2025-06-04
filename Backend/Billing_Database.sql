
CREATE DATABASE IF NOT EXISTS billing;
USE billing;

CREATE TABLE Customers (
    customer_id INT AUTO_INCREMENT PRIMARY KEY,
    full_name VARCHAR(100),
    email VARCHAR(100) UNIQUE,
    phone_number VARCHAR(20),
    address TEXT
);

CREATE TABLE RatePlans (
    rateplan_id INT AUTO_INCREMENT PRIMARY KEY,
    plan_name VARCHAR(100),
    description TEXT,
    monthly_fee DECIMAL(10,2)
);

CREATE TABLE Profiles (
    profile_id INT AUTO_INCREMENT PRIMARY KEY,
    customer_id INT,
    rateplan_id INT,
    activation_date DATE,
    status VARCHAR(20),
    FOREIGN KEY (customer_id) REFERENCES Customers(customer_id),
    FOREIGN KEY (rateplan_id) REFERENCES RatePlans(rateplan_id),
    CHECK (status IN ('active', 'inactive', 'suspended')) -- Example states
);

CREATE TABLE ServicePackages (
    package_id INT AUTO_INCREMENT PRIMARY KEY,
    rateplan_id INT,
    package_name VARCHAR(100),
    description TEXT,
    FOREIGN KEY (rateplan_id) REFERENCES RatePlans(rateplan_id)
);

CREATE TABLE Services (
    service_id INT AUTO_INCREMENT PRIMARY KEY,
    package_id INT,
    service_type ENUM('voice', 'sms', 'data'),
    price_per_unit DECIMAL(10,4),
    free_units BIGINT,
    FOREIGN KEY (package_id) REFERENCES ServicePackages(package_id)
);

CREATE TABLE DestinationZones (
    destination_zone_id INT AUTO_INCREMENT PRIMARY KEY,
    zone_name VARCHAR(100),
    country_code VARCHAR(10)
);

CREATE TABLE RatingRules (
    rating_rule_id INT AUTO_INCREMENT PRIMARY KEY,
    service_id INT,
    destination_zone_id INT,
    price_per_unit DECIMAL(10,4),
    FOREIGN KEY (service_id) REFERENCES Services(service_id),
    FOREIGN KEY (destination_zone_id) REFERENCES DestinationZones(destination_zone_id)
);

CREATE TABLE CDRs (
    cdr_id INT AUTO_INCREMENT PRIMARY KEY,
    profile_id INT,
    dial_a VARCHAR(50),
    dial_b VARCHAR(50),
    service_type ENUM('voice', 'sms', 'data'),
    duration_volume BIGINT,
    start_time DATETIME,
    FOREIGN KEY (profile_id) REFERENCES Profiles(profile_id)
);

CREATE TABLE RatedCDRs (
    rated_cdr_id INT AUTO_INCREMENT PRIMARY KEY,
    cdr_id INT,
    rating_rule_id INT,
    profile_id INT,
    service_id INT,
    rated_amount DECIMAL(10,4),
    rated_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (cdr_id) REFERENCES CDRs(cdr_id),
    FOREIGN KEY (rating_rule_id) REFERENCES RatingRules(rating_rule_id),
    FOREIGN KEY (profile_id) REFERENCES Profiles(profile_id),
    FOREIGN KEY (service_id) REFERENCES Services(service_id)
);

CREATE TABLE Bills (
    bill_id INT AUTO_INCREMENT PRIMARY KEY,
    profile_id INT,
    service_package_id INT,
    billing_period_start DATE,
    billing_period_end DATE,
    total_amount DECIMAL(12,2),
    generated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (profile_id) REFERENCES Profiles(profile_id),
    FOREIGN KEY (service_package_id) REFERENCES ServicePackages(package_id)
);

CREATE TABLE Invoices (
    invoice_id INT AUTO_INCREMENT PRIMARY KEY,
    bill_id INT,
    pdf_path TEXT,
    invoice_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    due_date DATE,
    payment_status VARCHAR(20),
    FOREIGN KEY (bill_id) REFERENCES Bills(bill_id),
    CHECK (payment_status IN ('unpaid', 'paid', 'overdue'))
);
