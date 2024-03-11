## 💻 About

Backlog API that reads XLSX files, logs them into a database, and provides user-friendly output, with a full CRUD scheme, system of recording which games i'm playing, in addition to connect with third person api's for database tools, regarding data analysis and file backups.
In addition, it also has a full system to post-backlog maintence system for my opinions on these games, with a full life cycle for those entities.
It has all its endpoints mapped in REST standard, for easy connection with different front-end applications, regardless of the technology used.
It has an engine fueled by IGDB API, which is used to save some data about the games, just as they are added to the backlog, also for providing images to the front end app.

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
- [x] Login system with JWT authentication
- [x] CRUD for different sort of games entities, categorized as backlog, such as playing and finished, with their er relationships
- [x] Routine for a Game entity in the system, easily traceable by the user
- [x] CRUD for finished games, with image storage in the database and return in an ideal format for display on the front-end
- [x] Manual backup system in the file system (CSV or XLSX)
- [x] Token validation system to be used by the front-end
- [x] User access to the backlog game list, generated from a Microsoft Excel table
- [x] Management of games in the dependent list, allowing the saving of new entities related to the previous game
- [x] Custom search system with pagination, enabling complete and customized access by the front-end
- [x] All endpoints mapped in the REST standard

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
- **[ConvertAPI](https://www.convertapi.com/)**
- **[JWT](https://jwt.io/)**
- **[IGDB-API](https://www.igdb.com/api)**
- **[Jackson](https://github.com/FasterXML/jackson-core)**
- **[Apache POI](https://poi.apache.org/)**
