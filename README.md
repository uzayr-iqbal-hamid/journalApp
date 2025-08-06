# JournalApp

JournalApp is a Spring Boot-based journaling application that allows users to create, manage, and store personal journal entries securely. It features RESTful APIs, MongoDB Atlas for cloud-based storage, and Spring Security with HTTP Basic authentication for secure access. The application is built using Java 8, Maven for dependency management, and Lombok to reduce boilerplate code.

## Features

- **RESTful APIs**: Create, read, update, and delete journal entries and user accounts via REST endpoints.
- **MongoDB Atlas**: Efficient cloud-based database management for storing users and journal entries.
- **Spring Security**: HTTP Basic authentication to secure `/journal/**` and `/user/**` endpoints, with public access to `/public/**` endpoints (e.g., user creation).
- **Lombok**: Simplifies development with annotations like `@Data` for automatic getter/setter generation.
- **User Management**: Create new users and manage authenticated user data.
- **Journal Management**: Create, retrieve, update, and delete journal entries for authenticated users.

## Technologies Used

- **Java**: 8
- **Spring Boot**: 2.7.15
- **Spring Data MongoDB**: For MongoDB Atlas integration
- **Spring Security**: For HTTP Basic authentication
- **Lombok**: To reduce boilerplate code
- **Maven**: For dependency management
- **MongoDB Atlas**: Cloud-based NoSQL database
- **IntelliJ IDEA**: Recommended IDE

## Project Structure

```plaintext
journalApp/
├── .gitattributes
├── .github/
│   └── workflows/
│       └── build.yml
├── .gitignore
├── .mvn/
│   └── wrapper/
│       └── maven-wrapper.properties
├── mvnw
├── mvnw.cmd
├── pom.xml
└── src/
    ├── main/
    │   ├── java/
    │   │   └── com/
    │   │       └── uzayr/
    │   │           └── journalApp/
    │   │               ├── api/
    │   │               │   └── response/
    │   │               │       └── WeatherResponse.java
    │   │               ├── cache/
    │   │               │   └── AppCache.java
    │   │               ├── config/
    │   │               │   ├── RedisConfig.java
    │   │               │   └── SpringSecurity.java
    │   │               ├── constants/
    │   │               │   └── Placeholders.java
    │   │               ├── controller/
    │   │               │   ├── AdminController.java
    │   │               │   ├── JournalEntryController.java
    │   │               │   ├── PublicController.java
    │   │               │   └── UserController.java
    │   │               ├── entity/
    │   │               │   ├── ConfigJournalAppEntity.java
    │   │               │   ├── JournalEntry.java
    │   │               │   └── User.java
    │   │               ├── enums/
    │   │               │   └── Sentiment.java
    │   │               ├── JournalAppApplication.java
    │   │               ├── repository/
    │   │               │   ├── ConfigJournalAppRepository.java
    │   │               │   ├── JournalEntryRepository.java
    │   │               │   ├── UserRepository.java
    │   │               │   └── UserRepositoryImpl.java
    │   │               ├── scheduler/
    │   │               │   └── UserScheduler.java
    │   │               └── service/
    │   │                   ├── EmailService.java
    │   │                   ├── JournalEntryService.java
    │   │                   ├── RedisService.java
    │   │                   ├── UserDetailsServiceImpl.java
    │   │                   ├── UserService.java
    │   │                   └── WeatherService.java
    │   └── resources/
    │       └── logback.xml
    └── test/
        └── java/
            └── com/
                └── uzayr/
                    └── journalApp/
                        ├── JournalAppApplicationTests.java
                        ├── repository/
                        │   └── UserRepositoryImplTests.java
                        ├── scheduler/
                        │   └── UserSchedulerTest.java
                        └── service/
                            ├── EmailServiceTests.java
                            ├── RedistTests.java
                            ├── UserArgumentsProvider.java
                            ├── UserDetailsServiceImplTests.java
                            └── UserServiceTests.java
```

## Prerequisites

- **Java 8**: Install JDK 8 (e.g., Amazon Corretto 1.8.0_452).
- **Maven**: For building and dependency management.
- **MongoDB Atlas**: A cloud-based MongoDB account for database storage.
- **IntelliJ IDEA**: Recommended IDE for development.
- **Postman**: For testing REST APIs.

## Setup Instructions

1. **Clone the Repository**:
    
    ```bash
    git clone https://github.com/uzayr-iqbal-hamid/journalApp.git
    cd journalApp
    
    ```
    
2. **Configure MongoDB Atlas**:
    - Create a MongoDB Atlas cluster at [MongoDB Atlas](https://www.mongodb.com/cloud/atlas).
    - Obtain your connection URI (e.g., `mongodb+srv://<username>:<password>@cluster0.2mugix6.mongodb.net/journaldb?retryWrites=true&w=majority`).
    - Update `src/main/resources/application.properties`:
        
        ```
        spring.application.name=Journal App
        spring.data.mongodb.uri=mongodb+srv://<username>:<password>@cluster0.2mugix6.mongodb.net/journaldb?retryWrites=true&w=majority
        spring.data.mongodb.auto-index-creation=true
        spring.main.allow-circular-references=true
        
        ```
        
3. **Install Dependencies**:
    - Run the following command to download dependencies:
        
        ```bash
        mvn clean install
        
        ```
        
4. **Run the Application**:
    - Start the Spring Boot application:
        
        ```bash
        mvn spring-boot:run
        
        ```
        
    - Or run `JournalAppApplication.java` in IntelliJ IDEA with Java 8 SDK.
    - The application will be accessible at `http://localhost:8080`.
5. **Test the APIs**:
    - Use Postman or curl to test the endpoints (see below).

## API Endpoints

### Public Endpoints

- **GET /public/health-check**
    - Description: Check if the application is running.
    - Response: `Ok`
    - Example:
        
        ```bash
        curl http://localhost:8080/public/health-check
        
        ```
        
- **POST /public/create-user**
    - Description: Create a new user.
    - Request Body:
        
        ```json
        {
            "userName": "testuser",
            "password": "testpass",
            "roles": ["USER"]
        }
        
        ```
        
    - Response: 200 OK (no body)
    - Example:
        
        ```bash
        curl -X POST http://localhost:8080/public/create-user -H "Content-Type: application/json" -d '{"userName":"testuser","password":"testpass","roles":["USER"]}'
        
        ```
        

### Authenticated Endpoints

- **GET /journal**
    - Description: Retrieve all journal entries for the authenticated user.
    - Authentication: HTTP Basic (username and password).
    - Example:
        
        ```bash
        curl -u testuser:testpass http://localhost:8080/journal
        
        ```
        
- **GET /journal/id/{id}**
    - Description: Retrieve a specific journal entry by ID.
    - Authentication: HTTP Basic.
    - Example:
        
        ```bash
        curl -u testuser:testpass http://localhost:8080/journal/id/507f1f77bcf86cd799439011
        
        ```
        
- **POST /journal**
    - Description: Create a new journal entry.
    - Authentication: HTTP Basic.
    - Request Body:
        
        ```json
        {
            "title": "My Journal",
            "content": "Today was a great day!"
        }
        
        ```
        
    - Example:
        
        ```bash
        curl -u testuser:testpass -X POST http://localhost:8080/journal -H "Content-Type: application/json" -d '{"title":"My Journal","content":"Today was a great day!"}'
        
        ```
        
- **PUT /journal/id/{id}**
    - Description: Update an existing journal entry.
    - Authentication: HTTP Basic.
    - Example:
        
        ```bash
        curl -u testuser:testpass -X PUT http://localhost:8080/journal/id/507f1f77bcf86cd799439011 -H "Content-Type: application/json" -d '{"title":"Updated Journal","content":"Updated content"}'
        
        ```
        
- **DELETE /journal/id/{id}**
    - Description: Delete a journal entry.
    - Authentication: HTTP Basic.
    - Example:
        
        ```bash
        curl -u testuser:testpass -X DELETE http://localhost:8080/journal/id/507f1f77bcf86cd799439011
        
        ```
        
- **PUT /user**
    - Description: Update the authenticated user’s details.
    - Authentication: HTTP Basic.
    - Example:
        
        ```bash
        curl -u testuser:testpass -X PUT http://localhost:8080/user -H "Content-Type: application/json" -d '{"userName":"newusername","password":"newpass"}'
        
        ```
        
- **DELETE /user**
    - Description: Delete the authenticated user.
    - Authentication: HTTP Basic.
    - Example:
        
        ```bash
        curl -u testuser:testpass -X DELETE http://localhost:8080/user
        
        ```
        

### Admin Endpoints

- **(TBD)**: Add admin-specific endpoints under `/admin/**` (requires `ADMIN` role).

## Testing

- **Unit Tests**: Use JUnit and Mockito for testing services and controllers (to be implemented).
- **API Testing**: Use Postman to test endpoints.
    - Import the provided Postman collection (if available) or manually test using the examples above.
- **Run Tests**:
    
    ```bash
    mvn test
    
    ```
    

## Security

- **HTTP Basic Authentication**: Used for securing `/journal/**` and `/user/**` endpoints.
- **Password Encoding**: Passwords are encoded using BCrypt via `BCryptPasswordEncoder`.
- **Public Access**: `/public/**` endpoints (e.g., `/public/create-user`) are accessible without authentication.

## Troubleshooting

- **401 Unauthorized**: Ensure correct username/password for authenticated endpoints or verify `SpringSecurity.java` allows `/public/**` without authentication.
- **MongoDB Connection**: Verify the MongoDB Atlas URI and network access in `application.properties`.
- **Lombok Issues**: Ensure the Lombok plugin is installed in IntelliJ and annotation processing is enabled (`File > Settings > Build, Execution, Deployment > Compiler > Annotation Processors`).
- **Build Errors**: Run `mvn clean install` and check for dependency issues.

## Contributing

1. Fork the repository.
2. Create a feature branch (`git checkout -b feature/your-feature`).
3. Commit changes (`git commit -m "Add your feature"`).
4. Push to the branch (`git push origin feature/your-feature`).
5. Open a pull request.

## License

This project is licensed under the MIT License - see the [LICENSE](https://grok.com/chat/LICENSE) file for details.

## Contact

- **Author**: Uzayr Iqbal Hamid
- **GitHub**: [uzayr-iqbal-hamid](https://github.com/uzayr-iqbal-hamid)
- **Email**: [hamiduzayr@gmail.com]
