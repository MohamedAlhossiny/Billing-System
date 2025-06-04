# Billing-System
Customer Bill Invoice Management System
Overview
This system is designed to manage telecom billing operations for customers, including customer management, service and rate plan assignment, usage-based rating, billing, and invoice generation.

Features
Web Interface
Customer Management

Add new customers

Search for existing customers

View a customer's invoice history

Profile & Services Management

Create new profiles and services

Assign rate plans, service packages, and individual services

View available profiles and their associated prices

Assign recurring services and one-time fees

Rating Module
Calculate charges based on:

Contracted rate plan

Service package

Service ID and rating package (destination zone)

Optional support for time-based pricing

Rate services:

Voice (per minute)

SMS (per message)

Data (external volume charges)

Billing
Generate bills for:

Free unit usage

Recurring services

One-time fees

Actual usage (voice/SMS/data)

Add tax (10%) to total invoice

Invoicing
Generate PDF invoices containing:

Company name

Customer details

Profile and services

Usage and charges

Tax and total cost

Technologies Used
Java Servlets

REST APIs

HTML/CSS/JavaScript (Frontend)

iText (for PDF generation)

PostgreSQL / MongoDB (for data persistence and logs)

Future Enhancements
Dockerize components (Web server, DB, Asterisk)

Web UI for log viewing

Modularize rating engine

Add support for multiple tax rules


