# Citizen Engagement System

A Spring Boot-based system for managing citizen complaints and feedback on public services.

## Features

- Submit and track citizen complaints
- Categorize and route complaints to appropriate agencies
- Status tracking for citizens
- Admin interface for government response
- Swagger UI for API documentation

## Tech Stack

- Backend: Spring Boot 3.x
- Database: PostgreSQL
- API Documentation: Swagger/OpenAPI 3
- Security: Spring Security with JWT

## Prerequisites

- Java 17 or higher
- Maven
- PostgreSQL

## Getting Started

1. Clone the repository:

```bash
git clone [https://github.com/mahingaRodin/-citizen-engagement-system-Rwanda-ICT-Chamber]
```

2. Configure the database in `application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/citizen_engagement
spring.datasource.username=your_username
spring.datasource.password=your_password
```

3. Build the project:

```bash
mvn clean install
```

4. Run the application:

```bash
mvn spring-boot:run
```

The application will start on port 8080 by default.

## API Documentation

Once the application is running, you can access:

- Swagger UI: http://localhost:8080/swagger-ui.html
- OpenAPI documentation: http://localhost:8080/v3/api-docs

## API Endpoints

### Complaints

- POST `/api/complaints` - Submit a new complaint
- GET `/api/complaints/{id}` - Get complaint by ID
- PUT `/api/complaints/{id}/status` - Update complaint status

## Security

The application uses JWT authentication. To access protected endpoints:

1. Obtain a JWT token through the authentication endpoint
2. Include the token in the Authorization header: `Bearer <your-token>`

## Development

### Project Structure

recreate the files but dnt change anything in the code base

I'll help you recreate the files with the same code. Let's start with the OpenAPI configuration:
Now let's recreate the Security configuration:
Now let's create a sample controller to demonstrate Swagger annotations:
Now that we have created these files, you'll need to:
Make sure you have the following dependencies in your pom.xml:
Apply
Add these properties to your application.properties or application.yml:
Apply
After setting this up, you can access:
Swagger UI at: http://localhost:8080/swagger-ui.html
OpenAPI documentation at: http://localhost:8080/v3/api-docs
The configuration provides:
Custom API information (title, description, version)
JWT authentication support
API grouping by controllers
Detailed API documentation with examples
Security configuration for Swagger UI access
Would you like me to:
Add more controllers with different API groups?
Add more detailed response schemas?
Customize the Swagger UI theme?
Add more security configurations?
Let me know what aspect you'd like to focus on next!
without making any changes in the codebase :
give again those files and I will write them for myself

I'll provide you with the exact code for each file that you can copy and implement yourself:
OpenApiConfig.java:
Apply
SecurityConfig.java:
Apply
ComplaintController.java:
Apply
i
Remember to add these properties to your application.properties or application.yml:
Apply
And make sure you have the SpringDoc OpenAPI dependency in your pom.xml:
Apply
After implementing these files, you can access:
Swagger UI at: http://localhost:8080/swagger-ui.html
OpenAPI documentation at: http://localhost:8080/v3/api-docs
is 8080 the port the system will be running on ?

Yes, 8080 is the default port that Spring Boot applications run on. However, you can change this port in your application.properties or application.yml file. Here are the common ways to change the port:
In application.properties:
Apply
In application.yml:
Apply
You can also:
Set it via command line when running the application:
Apply
Run
Set it as an environment variable:
Apply
Run
Use different ports for different environments:
Apply
If you want to change the port, just add the appropriate configuration to your application.properties or application.yml file. The Swagger UI will then be available at:
http://localhost:YOUR_PORT/swagger-ui.html
http://localhost:YOUR_PORT/v3/api-docs
Would you like me to help you set up a specific port for your application?
give a simple readme.md for this

Here's a simple README.md for your Citizen Engagement System:
Apply
src/main/java/com/citizen/engagement/
├── config/ # Configuration classes
├── controller/ # REST controllers
├── model/ # Entity classes
├── repository/ # Data access layer
├── service/ # Business logic
└── security/ # Security related classes

### Running Tests

```bash
mvn test
```

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## License

This project is licensed under the Apache License 2.0 - see the [LICENSE](LICENSE) file for details.

## Contact

Citizen Engagement Team - support@citizenengagement.com

Project Link: [https://github.com/yourusername/citizen-engagement](https://github.com/mahingaRodin//-citizen-engagement-system-Rwanda-ICT-Chamber)
