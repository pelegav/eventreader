# EventReader System

## Overview

EventReader Service is a Spring Boot application designed to parse XML event data and provide REST APIs to manage and retrieve product information associated with insured events. 
The system leverages JAXB for XML binding and exposes endpoints to query products grouped by insured IDs and source companies.

---

## Features

- Parses XML input into Java objects using JAXB annotations.
- Provides RESTful APIs to:
  - Retrieve all products.
  - Retrieve products grouped by SourceCompany for each InsuredId.
- Layered architecture separating controllers, services, and repositories.
- Unit and integration tests for controllers and service layers.
- Uses Spring Boot, Spring MVC, and Mockito for testing.

---

## Technologies Used

- Java 17+
- Spring Boot 3.x
- Spring MVC (REST Controllers)
- JAXB (Java XML Binding)
- Mockito (Unit Testing)
- JUnit 5
- Maven or Gradle (build tool)
- Lombok (for boilerplate code reduction)

---

## Getting Started

### Prerequisites

- Java JDK 17 or higher
- Maven 3.6+ or Gradle 7+
- IDE (IntelliJ IDEA, Eclipse, VSCode, etc.)

### Build and Run

# Build the project (Maven)
mvn clean install

# Run the application
mvn spring-boot:run
