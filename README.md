# Code Hierarchy Application

## Overview
This application provides functionality to analyze and document Oracle PL/SQL packages using AI-powered descriptions. It connects to both Oracle and PostgreSQL databases, using Gemini AI to generate natural language descriptions of PL/SQL packages.

## Application Structure

### Core Components

#### 1. Controllers
- `PackageDescriptionController`: REST endpoints for package description operations
  - GET `/api/v1/package-descriptions/{owner}`: Fetch package descriptions
  - POST `/api/v1/package-descriptions/generate/{owner}`: Generate descriptions for all packages
  - POST `/api/v1/package-descriptions/generate/{owner}/{packageName}`: Generate description for specific package

#### 2. Services
- `PackageDescriptionService`: Core service for package description operations
  - `generateAndSavePackageDescriptions`: Generate descriptions for all packages
  - `processAndSavePackageDescription`: Generate description for a specific package
  - `getPackageDescriptions`: Retrieve stored package descriptions

#### 3. Repositories
- `PackageDescriptionRepository`: JPA repository for package descriptions
  - `findByOwner`: Find descriptions by schema owner

#### 4. Models
- `PackageDescription`: Entity for storing package descriptions
  - Fields: owner, packageName, description
- `GenerateContentRequest`: Model for Gemini API requests
  - Nested classes: Content, Part
- `GenerateContentResponse`: Model for Gemini API responses
  - Nested classes: Candidate, Content, Part

#### 5. Clients
- `GeminiFeignClient`: Feign client for Gemini API integration
  - `generateContent`: Send requests to Gemini API

### Database Configuration

#### Oracle Database
- Configuration in `application.yml`
- JDBC template: `oracleJdbcTemplate`
- Used for: Reading PL/SQL package definitions

#### PostgreSQL Database
- Configuration in `application.yml`
- JPA/Hibernate configuration
- Used for: Storing package descriptions

### External Services
- Gemini AI API
  - API Key configuration in `application.yml`
  - Used for: Generating natural language descriptions

## API Endpoints

### Package Descriptions
1. **Get Package Descriptions**
   ```
   GET /api/v1/package-descriptions/{owner}
   Response: List<PackageDescription>
   ```

2. **Generate All Package Descriptions**
   ```
   POST /api/v1/package-descriptions/generate/{owner}
   Response: 200 OK
   ```

3. **Generate Single Package Description**
   ```
   POST /api/v1/package-descriptions/generate/{owner}/{packageName}
   Response: 200 OK
   ```

## Configuration
Key configuration files:
- `application.yml`: Main application configuration
  - Database connections
  - Gemini API key
  - JPA/Hibernate settings

## Dependencies
- Spring Boot
- Spring Data JPA
- Spring JDBC
- Feign Client
- Lombok
- PostgreSQL Driver
- Oracle JDBC Driver

## Usage
1. Configure database connections in `application.yml`
2. Set Gemini API key in `application.yml`
3. Start the application
4. Use the REST endpoints to:
   - Generate package descriptions
   - Retrieve stored descriptions

## Error Handling
- Service layer includes comprehensive error handling
- Logging implemented using SLF4J
- Transaction management for database operations

## Security
- API key management for Gemini API
- Database credentials managed through configuration # Oracle-code-hierachy-backend
