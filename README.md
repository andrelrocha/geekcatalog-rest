## 💻 About

GeekCatalog API is designed to manage media and art content not covered by other widely-used market applications like Letterboxd. It offers users the capability to manage various types of media such as series, anime, games, or any other implemented media types, through CRUD operations following the business requirements. The API provides endpoints following REST standards, as well as multimedia communication channels, enabling users to manage storage and media responses such as images in the database for frontend consumption.

The API is built using Spring Boot and incorporates Flyway and Hibernate for database management, adhering to best practices in communication with the database. It manages multiple schemas within a multi-application schema structure, accommodating a legacy application already developed by me in: [https://github.com/andrelrocha/backlog-app-API].

## Documentation API

- Run the project by executing ApiApplication
- Check the visual interface of the documentation at http://localhost:8080/swagger-ui/index.html
- Access the JSON documentation at http://localhost:8080/v3/api-docs
- To build and run the project using Maven:
```
mvn package
java -jar target/your-project-name.jar
```
This will first build the project, package it into a JAR file, and then you can execute the JAR using the java -jar command. 
Replace your-project-name.jar with the actual name of your generated JAR file.

---

## ⚙️ Functionalities

- [x] Handling and conversion of information from .csv and .xlsm tables to entities properly mapped in the system, facilitating the control of this information with application-specific algorithms
- [x] User custom system with different levels of permissions
- [x] Sign In system with JWT authentication
- [x] OAuth Sign In with external identity providers
- [x] User Authentication Using a Refresh Token Scheme with User Experience Patterns
- [x] Access audit routines for application security logging
- [x] Cache-based User Management System to Minimize Database Queries for Logged-in Users
- [x] CRUD for different sort of media types, categorized as backlog, such as games, animes, mangas, with their er relationships
- [x] Routine for all entities in the system, easily traceable by the user
- [x] List management system, with users able to rate, indicate medium which they consumed the entity, and provide personal notes for them
- [x] Image storage with file compression system, facilitating quick communication with the front-end
- [x] CRUD for games, with image storage in the database and return in an ideal format for display on the front-end
- [x] Token validation system to be used by the front-end
- [x] Custom search system with pagination, enabling complete and customized access by the front-end
- [x] CRUD for lists, with user permission system
- [x] User invites for lists colaboration
- [x] List management system, with CRUD for games in a list
- [x] Public lists search system, with user search for others public list, for social media interaction
- [x] Games relationships in a social media format, with user comments and rating, for social interaction
- [x] All endpoints mapped in the REST standard
- [x] Security schema implemented in the backend for requests in different layers
- [x] Vulnerability Protection through HTTP Cookies, Rate Limiting Filters, and IP Blocking
- [x] SSL/TLS support for secure communication, ensuring data integrity and encryption for all requests between the client and server
- [x] ChatGPT Queries to smooth the Client Experience consuming the API
---

## 🛠 Technologies

The following technologies were used in the development of the REST API project:

- **[Java 17](https://www.oracle.com/java)**
- **[Spring Boot 3](https://spring.io/projects/spring-boot)**
- **[Maven](https://maven.apache.org)**
- **[Postgresql](https://www.postgresql.org/)**
- **[Hibernate](https://hibernate.org)**
- **[Flyway](https://flywaydb.org)**
- **[Lombok](https://projectlombok.org)**
- **[JWT](https://jwt.io/)**
- **[AWS](https://aws.amazon.com/sdk-for-java/)**
- **[S3](https://aws.amazon.com/pt/pm/serv-s3/)**
- **[Bucket4j](https://bucket4j.com/)**
- **[IGDB-API](https://www.igdb.com/api)**
- **[ThymeLeaf](https://www.thymeleaf.org/)**
- **[ChatGPT](https://openai.com/index/openai-api/)**