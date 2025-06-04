# Telecom Billing Rating Engine

This is a Java-based rating engine for a telecom billing system. It processes Call Detail Records (CDRs) and applies rating rules to calculate charges for different types of services (voice, SMS, data).

## Prerequisites

- Java 11 or higher
- Maven 3.6 or higher
- MySQL 8.0 or higher

## Database Setup

1. Create the database using the provided `Billing_Database.sql` script:
```bash
mysql -u root -p < Billing_Database.sql
```

2. Update the database configuration in `src/main/java/com/telecom/billing/config/DatabaseConfig.java` with your MySQL credentials.

## Project Structure

```
src/main/java/com/telecom/billing/
├── config/
│   └── DatabaseConfig.java
├── model/
│   ├── CDR.java
│   └── RatedCDR.java
├── service/
│   └── RatingService.java
└── RatingEngine.java
```

## Building the Project

```bash
mvn clean install
```

## Running the Application

```bash
mvn exec:java -Dexec.mainClass="com.telecom.billing.RatingEngine"
```

## Features

- Processes CDRs for voice, SMS, and data services
- Applies rating rules based on service type and destination
- Stores rated CDRs in the database
- Uses connection pooling for better performance
- Includes logging for monitoring and debugging

## Configuration

The main configuration points are:

1. Database connection settings in `DatabaseConfig.java`
2. Rating rules in the database (RatingRules table)
3. Service definitions in the database (Services table)
4. Destination zones in the database (DestinationZones table)

## Adding New Features

To add new features:

1. Create new model classes in the `model` package
2. Add new services in the `service` package
3. Update the database schema if needed
4. Add new configuration in the `config` package if required 