# Billing System REST API

This project implements a REST API for a billing system using Java and Jersey, based on the provided database schema.

## Prerequisites

*   Java Development Kit (JDK) 11 or higher
*   Maven 3.6 or higher
*   MySQL Database server
*   A Jakarta EE compatible application server (e.g., Apache Tomcat, GlassFish, WildFly, Apache TomEE)

## Database Setup

1.  Ensure you have a MySQL server running.
2.  Execute the SQL script `Billing_Database.sql` to create the database and tables.
3.  Optionally, execute the SQL script `test_data.sql` to populate the database with test data.

## Project Setup

1.  Clone or download the project code.
2.  Navigate to the project's root directory in your terminal.
3.  Update the database connection details in `src/main/java/com/example/billingapi/util/DatabaseConnection.java`:
    ```java
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/billing?serverTimezone=UTC"; // TODO: Configure database URL
    private static final String USER = "root"; // TODO: Configure database username
    private static final String PASSWORD = "password"; // TODO: Configure database password
    ```
    Replace the placeholder values with your actual database URL, username, and password.

## Building the Project

Use Maven to build the project and create a WAR file:

```bash
mvn clean package
```

This will generate a `billing-api-1.0-SNAPSHOT.war` file in the `target` directory.

## Deployment

Deploy the generated WAR file to your Jakarta EE compatible application server. The deployment steps vary depending on the server you are using (refer to your server's documentation).

Ensure your application server is configured to connect to your MySQL database.

## API Endpoints

The API endpoints are typically accessible at a base URL like `/billing/api` after deployment, depending on your server configuration and the context path of the deployed application.

### Customer Endpoints (`/customer`)

Handles operations related to customer information.

*   **`GET /customer`**
    *   **Description:** Retrieves a list of all customers.
    *   **Method:** `GET`
    *   **Request Body:** None
    *   **Response Body:** JSON array of Customer objects.
        ```json
        [
          {
            "customerId": int,
            "fullName": "string",
            "email": "string",
            "phoneNumber": "string",
            "address": "string"
          }
          // ... more Customer objects
        ]
        ```

*   **`GET /customer/{mobileNumber}`**
    *   **Description:** Retrieves a single customer by their mobile number.
    *   **Method:** `GET`
    *   **URL Parameters:**
        *   `mobileNumber` (string): The mobile number of the customer to retrieve.
    *   **Request Body:** None
    *   **Response Body:** JSON object representing the Customer if found, or a 404 Not Found status with an error message if not found.
        ```json
        {
          "customerId": int,
          "fullName": "string",
          "email": "string",
          "phoneNumber": "string",
          "address": "string"
        }
        ```

*   **`POST /customer`**
    *   **Description:** Adds a new customer.
    *   **Method:** `POST`
    *   **Request Body:** JSON object representing the new customer (`customerId` will likely be ignored as it's auto-generated)
        ```json
        {
          "fullName": "string",
          "email": "string",
          "phoneNumber": "string",
          "address": "string"
        }
        ```
    *   **Response Body:** JSON object of the newly created customer (including the generated `customerId`) with a 201 Created status.
        ```json
        {
          "customerId": int,
          "fullName": "string",
          "email": "string",
          "phoneNumber": "string",
          "address": "string"
        }
        ```

*   **`PUT /customer`**
    *   **Description:** Updates an existing customer.
    *   **Method:** `PUT`
    *   **Request Body:** JSON object representing the customer to update (`customerId` is required for identification)
        ```json
        {
          "customerId": int,
          "fullName": "string",
          "email": "string",
          "phoneNumber": "string",
          "address": "string"
        }
        ```
    *   **Response Body:** JSON object of the updated customer with a 200 OK status.
        ```json
        {
          "customerId": int,
          "fullName": "string",
          "email": "string",
          "phoneNumber": "string",
          "address": "string"
        }
        ```

*   **`DELETE /customer/{customerId}`**
    *   **Description:** Deletes a customer by their ID.
    *   **Method:** `DELETE`
    *   **URL Parameters:**
        *   `customerId` (integer): The ID of the customer to delete.
    *   **Request Body:** None
    *   **Response Body:** A string message "Customer deleted" with a 200 OK status.

### Rate Plan Endpoints (`/rateplan`)

Handles operations related to rate plans.

*   **`POST /rateplan`**
    *   **Description:** Adds a new rate plan.
    *   **Method:** `POST`
    *   **Request Body:** JSON object representing the new rate plan (`rateplanId` will likely be ignored as it's auto-generated)
        ```json
        {
          "planName": "string",
          "description": "string",
          "monthlyFee": double
        }
        ```
    *   **Response Body:** JSON object of the newly created rate plan (including the generated `rateplanId`) with a 201 Created status.
        ```json
        {
          "rateplanId": int,
          "planName": "string",
          "description": "string",
          "monthlyFee": double
        }
        ```

*   **`GET /rateplan`**
    *   **Description:** Retrieves a list of all rate plans.
    *   **Method:** `GET`
    *   **Request Body:** None
    *   **Response Body:** JSON array of RatePlan objects.
        ```json
        [
          {
            "rateplanId": int,
            "planName": "string",
            "description": "string",
            "monthlyFee": double
          }
          // ... more RatePlan objects
        ]
        ```

### Service Package Endpoints (`/servicepackage`)

Handles operations related to service packages.

*   **`POST /servicepackage`**
    *   **Description:** Adds a new service package.
    *   **Method:** `POST`
    *   **Request Body:** JSON object representing the new service package (`packageId` will likely be ignored)
        ```json
        {
          "rateplanId": int,
          "packageName": "string",
          "description": "string"
        }
        ```
    *   **Response Body:** JSON object of the newly created service package (including the generated `packageId`) with a 201 Created status.
        ```json
        {
          "packageId": int,
          "rateplanId": int,
          "packageName": "string",
          "description": "string"
        }
        ```

*   **`PUT /servicepackage`**
    *   **Description:** Updates an existing service package.
    *   **Method:** `PUT`
    *   **Request Body:** JSON object representing the service package to update (`packageId` is required)
        ```json
        {
          "packageId": int,
          "rateplanId": int,
          "packageName": "string",
          "description": "string"
        }
        ```
    *   **Response Body:** JSON object of the updated service package with a 200 OK status.
        ```json
        {
          "packageId": int,
          "rateplanId": int,
          "packageName": "string",
          "description": "string"
        }
        ```

### Service Endpoints (`/service`)

Handles operations related to services.

*   **`POST /service`**
    *   **Description:** Adds a new service.
    *   **Method:** `POST`
    *   **Request Body:** JSON object representing the new service (`serviceId` will likely be ignored)
        ```json
        {
          "packageId": int,
          "serviceType": "string",
          "pricePerUnit": double,
          "freeUnits": long
        }
        ```
    *   **Response Body:** JSON object of the newly created service (including the generated `serviceId`) with a 201 Created status.
        ```json
        {
          "serviceId": int,
          "packageId": int,
          "serviceType": "string",
          "pricePerUnit": double,
          "freeUnits": long
        }
        ```

*   **`PUT /service`**
    *   **Description:** Updates an existing service.
    *   **Method:** `PUT`
    *   **Request Body:** JSON object representing the service to update (`serviceId` is required)
        ```json
        {
          "serviceId": int,
          "packageId": int,
          "serviceType": "string",
          "pricePerUnit": double,
          "freeUnits": long
        }
        ```
    *   **Response Body:** JSON object of the updated service with a 200 OK status.
        ```json
        {
          "serviceId": int,
          "packageId": int,
          "serviceType": "string",
          "pricePerUnit": double,
          "freeUnits": long
        }
        ```

### Profile Endpoints (`/profile`)

Hands information related to user profiles.

*   **`GET /profile`**
    *   **Description:** Retrieves a list of all profiles.
    *   **Method:** `GET`
    *   **Request Body:** None
    *   **Response Body:** JSON array of Profile objects.
        ```json
        [
          {
            "profileId": int,
            "customerId": int,
            "rateplanId": int,
            "activationDate": "date",
            "status": "string"
          }
          // ... more Profile objects
        ]
        ```

*   **`GET /profile/{profileId}`**
    *   **Description:** Retrieves a single profile by their ID.
    *   **Method:** `GET`
    *   **URL Parameters:**
        *   `profileId` (integer): The ID of the profile to retrieve.
    *   **Request Body:** None
    *   **Response Body:** JSON object representing the Profile if found, or a 404 Not Found status with an error message if not found.
        ```json
        {
          "profileId": int,
          "customerId": int,
          "rateplanId": int,
          "activationDate": "date",
          "status": "string"
        }
        ```

*   **`POST /profile`**
    *   **Description:** Adds a new profile.
    *   **Method:** `POST`
    *   **Request Body:** JSON object representing the new profile (`profileId` will likely be ignored as it's auto-generated)
        ```json
        {
          "customerId": int,
          "rateplanId": int,
          "activationDate": "date",
          "status": "string"
        }
        ```
    *   **Response Body:** JSON object of the newly created profile (including the generated `profileId`) with a 201 Created status.
        ```json
        {
          "profileId": int,
          "customerId": int,
          "rateplanId": int,
          "activationDate": "date",
          "status": "string"
        }
        ```

*   **`PUT /profile`**
    *   **Description:** Updates an existing profile.
    *   **Method:** `PUT`
    *   **Request Body:** JSON object representing the profile to update (`profileId` is required for identification)
        ```json
        {
          "profileId": int,
          "customerId": int,
          "rateplanId": int,
          "activationDate": "date",
          "status": "string"
        }
        ```
    *   **Response Body:** JSON object of the updated profile with a 200 OK status.
        ```json
        {
          "profileId": int,
          "customerId": int,
          "rateplanId": int,
          "activationDate": "date",
          "status": "string"
        }
        ```

*   **`DELETE /profile/{profileId}`**
    *   **Description:** Deletes a profile by their ID.
    *   **Method:** `DELETE`
    *   **URL Parameters:**
        *   `profileId` (integer): The ID of the profile to delete.
    *   **Request Body:** None
    *   **Response Body:** A string message "Profile deleted" with a 200 OK status.

### Current Assignment Endpoint (`/current-assignment`)

Provides a hierarchical view of rate plans, their service packages, and the services within them.

*   **`GET /current-assignment`**
    *   **Description:** Retrieves a list of all rate plans with their associated service packages and the services within each package.
    *   **Method:** `GET`
    *   **Request Body:** None
    *   **Response Body:** JSON array of RatePlanDetails objects.
        ```json
        [
          {
            "ratePlan": { // RatePlan object
              "rateplanId": int,
              "planName": "string",
              "description": "string",
              "monthlyFee": double
            },
            "servicePackageDetails": [ // List of ServicePackageDetails
              {
                "servicePackage": { // ServicePackage object
                  "packageId": int,
                  "rateplanId": int,
                  "packageName": "string",
                  "description": "string"
                },
                "services": [ // List of Service objects
                  {
                    "serviceId": int,
                    "packageId": int,
                    "serviceType": "string",
                    "pricePerUnit": double,
                    "freeUnits": long
                  }
                  // ... more Service objects
                ]
              }
              // ... more ServicePackageDetails
            ]
          }
          // ... more RatePlanDetails
        ]
        ```

### Invoice Endpoint (`/invoice`)

Handles retrieval of invoices.

*   **`GET /invoice/{mobileNumber}`**
    *   **Description:** Retrieves a list of invoices for a customer identified by their mobile number.
    *   **Method:** `GET`
    *   **URL Parameters:**
        *   `mobileNumber` (string): The mobile number of the customer whose invoices to retrieve.
    *   **Request Body:** None
    *   **Response Body:** JSON array of Invoice objects if found, or a 404 Not Found status with an error message if no invoices are found for the mobile number.
        ```json
        [
          {
            "invoiceId": int,
            "billId": int,
            "pdfPath": "string",
            "invoiceDate": "timestamp",
            "dueDate": "date",
            "paymentStatus": "string"
          }
          // ... more Invoice objects
        ]
        ``` 
