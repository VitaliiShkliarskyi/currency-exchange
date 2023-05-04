![Header](src/main/resources/pictures/currency_exchange.jpeg)

##  Description
A **REST** service that loads data from a third-party service into a local database using a scheduled job. Developed using **Spring Boot** and **Spring Data JPA**. 
**Docker Compose** is used to orchestrate the various containers required for the application. The API is documented and has a detailed description of all endpoints using **Swagger**.
**Liquibase** is used for database management.

---

##  Project structure
Project based on Three-Tier architecture:
- **Presentation tier** (controllers) - provides user interaction with the application
- **Logic tier** (services) - establishes the set of available operations and coordinates the program's response to each operation.
- **Data tier** (repository) - represents interaction with the database

---

## Technologies
- Java 17
- Gradle
- Spring Boot
- PosgreSQL
- Liquibase
- Swagger 
- Docker Compose

---
## Implemenation details
- **Models** represent a set of columns in the database
- **DTOs** represent communication between different layers
- **DTO mappers** for converting DTOs
- All interaction with the database occurs at the **repository** level
- **SpringFoxConfig class** is responsible for Swagger configuration
- All operations are available at the **Service**
- All interaction client-server occurs through **Controllers**
- **HttpClient** allows to fetch data from a third-party API
- **Liquibase** helps to define the desired state of a database schema
- **@Scheduled** and **Cron jobs** are used to automate repetitive tasks 
- All endpoints are documented and can be tested using **Swagger**
- **Checkstyle plugin** helps enforce coding standards and best practices
- **Docker Compose** runs the application and the database as separate services and configures the interaction between them
- **Gradle** provides fast and efficient build performance.

---

## Quickstart
### You can run the application in two ways: in a virtual machine or locally on your system
**For the first option, perform the following actions:**
1. Fork this repository
2. Clone the project to your computer
3. To obtain a request from the **Minfin**, registration is required in order to receive 10 free requests.
After receiving the key, copy and paste it into the value of **'minfin.user.key'** in the **application properties**. 
You can use an existing key, but there are no more than 5 requests left on it.
4. Install [Docker](https://www.docker.com/products/docker-desktop/)
5. Run **`./gradlew build -x test`** in terminal to build the project and then run **`docker compose up`**
6. The application and database with necessary configurations will run as separate services that can already interact with each other. The interaction description is located in the **docker-compose.yml** file. 
7. To send a request to the application, use the access port to the virtual machine: **http://localhost:6868**
8. For testing endpoints, you can use Swagger UI: **http://localhost:6868/swagger-ui/#/**
9. To connect to the local database, which is raised as a separate service, access port to the virtual machine is also used (it is already configured using parameters from .env):
Host: **localhost**; Port: **5433**; User: **postgres**; Password: **1234**; Database: **currency_exchange**


**To run application locally, perform the following actions:**
1. Fork this repository
2. Clone the project to your computer
3. Install [PostgreSQL](https://www.postgresql.org/download/) and configure the database connection.
4. Add **DB_USERNAME** and **DB_PASSWORD** to the **application.properties** file
5. Execute step 3 from the above instruction.
6. Run **CurrencyExchangeApplication**
7. To send a request to the application, use the local port: **http://localhost:8080**
8. For testing endpoints, you can use Swagger UI: **http://localhost:8080/swagger-ui/#/**


## Note:
#### The application is configured to synchronize with third-party services daily at 9:30 AM. 
#### Synchronization also occurs when the application is launched. 
#### Please keep this in mind, taking into account the limited number of requests per key.

---

## Author

[Vitalii Shkliarskyi](https://github.com/VitaliiShkliarskyi)
