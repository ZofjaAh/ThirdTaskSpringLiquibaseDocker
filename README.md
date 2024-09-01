
# REST API for Managing Localities, Attractions and Services
This project provides a REST API for managing localities attractions and services. The API supports CRUD operations for each entity.

## Requirements
- JDK 17
- Gradle 8.3 or higher
- Docker 3.8 or higher


## Installation
1. Clone the repository:
    ```bash
    git clone <URL of your repository>
    ```
2. Navigate to the project directory:
    ```bash
    cd ThirdTaskSpringLiquibaseDocker
    ```
3. Build the project using Gradle:
    ```bash
   ./gradlew clean build
    ```
    
## Running with Docker
1. Ensure Docker is installed and running on your machine.
2. Create and start the Docker containers:
    ```bash
    docker-compose up --build
    ```
3. The application will be accessible at `http://localhost:8082`.

### Docker Configuration
The `docker-compose.yml` file is configured as follows:
```yaml
version: "3.8"
services:
  db:
    image: postgres:15.0
    hostname: db
    container_name: attraction_library
    ports:
      - "5432:5432"
    networks:
      - third_task
    restart: always
    environment:
      POSTGRES_DB: attraction_library
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: postgres
    healthcheck:
      test: [ "CMD-SHELL", "pg_isready -U postgres" ]
      interval: 10s
      timeout: 5s
      retries: 5
    volumes:
      - ./scripts/init-db.sh:/docker-entrypoint-initdb.d/init-db.sh

  tomcat:
    depends_on:
      - liquibase
    links:
      - db
    build:
      context: .
      dockerfile: Dockerfile
    volumes:
      - ./logs:/usr/local/tomcat/logs
    ports:
      - '8082:8080'
    environment:
      POSTGRES_URL: jdbc:postgresql://db/attraction_library
      POSTGRES_DB: attraction_library
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: postgres
    networks:
      - third_task
  liquibase:
    image: liquibase/liquibase
    depends_on:
      db:
        condition: service_healthy
    environment:
      - LIQUIBASE_SEARCH_PATH=/usr/local/tomcat/webapps/db/changelog
    volumes:
      - ./src/main/resources/db/changelog:/usr/local/tomcat/webapps/db/changelog
    entrypoint:
      - "liquibase"
      - "--url=jdbc:postgresql://db/attraction_library"
      - "--username=postgres"
      - "--password=postgres"
      - "--changeLogFile=db.changelog-master.xml"
      - "update"

    networks:
      - third_task

volumes:
  logs:
    driver: local
networks:
  third_task:
```

### Dockerfile
The Dockerfile is configured as follows:
```yaml
FROM liquibase/liquibase:4.15.0 as liquibase
FROM tomcat:10.1.28-jdk17-temurin-jammy
COPY --from=liquibase /liquibase /opt/liquibase
RUN ln -s /opt/liquibase/liquibase /usr/local/bin/liquibase
ADD src/main/resources/db/changelog/ liquibase/changelog
COPY src/main/resources/database.properties /usr/local/tomcat/conf/database.properties
COPY src/main/resources/db /usr/local/tomcat/webapps/db
ADD build/libs/attraction_library.war /usr/local/tomcat/webapps/
```
##  Dependencies
The project uses the following main dependencies:
- **Spring Framework**: For building the application.
- **Spring Data JPA**: For data persistence.
- **Hibernate**: For ORM.
- **PostgreSQL**: Driver for connecting to the PostgreSQL database.
- **Jakarta Servlet API**: For working with servlets.
- **MapStruct**: For object mapping.
- **Jackson Databind**: For working with JSON.
- **Lombok**: To simplify code writing.
- **Logback**: For logging.
- **SLF4J**: For logging abstraction.

## API Endpoints
### Coordinator
- **Create Locality**
  
You can use parameter Metro Availability types: **true**/ **false**

You can use parameter Population number lower 1272147483647
 ```bash
POST http://localhost:8082/attraction_library/rest/locality/create
```
Body: JSON 
 ```bash
{
"localityName": "SomeLocality",
"population": 123456789,
"metroAvailability": true
}
```
**Response: JSON**
```bash
{
    "localityId": 6,
    "localityName": "SomeLocality",
    "population": 123456789,
    "metroAvailability": true,
    "attractions": null
}
```
- **Update Locality Population and Metro Avalability**
  
  *With all parameters*
 ```bash
PATCH http://localhost:8082/attraction_library/rest/locality/change/1
```
Params: 
Key:
 ```bash
population
```
Value:
``` bash
1234567890
```
Key:
 ```bash
metroAvailability
```
Value:
``` bash
false
```
**Response: 1**

 *Only with Population*
 ```bash
PATCH http://localhost:8082/attraction_library/rest/locality/change/2
```
Params: 
Key:
 ```bash
population
```
Value:
``` bash
1234567890
```
 **Response: 1**

 *Only with Metro Availability*
 ```bash
PATCH http://localhost:8082/attraction_library/rest/locality/change/3
```
Key:
 ```bash
metroAvailability
```
Value:
``` bash
true
```
**Response: 1**

 *With Not Exist's Locality*
 ```bash
PATCH http://localhost:8082/attraction_library/rest/locality/change/10
```
Key:
 ```bash
metroAvailability
```
Value:
``` bash
true
```
**Response: JSON**
```bash
{
    "errorId": "Locality with Id: 10 does not exist"
}
```
 *Without Parameters*
 ```bash
PATCH http://localhost:8082/attraction_library/rest/locality/change/1
```
**Response: JSON**
```bash
{
    "errorId": "Parameters population and metro availability are not specified"
}
```

### Attraction
- **Create Attraction**
 ```bash
POST http://localhost:8082/attraction_library/rest/attraction/create
```
Body: JSON 
 ```bash
{
"attractionName": "SomeAttraction",
"creationDate": "2012-01-01",
"description": "some Description",
"type": "museums",
"localityName": "Warsaw"
}
```
**Response: JSON**
```bash
{
    "attractionId": 6,
    "attractionName": "SomeAttraction",
    "creationDate": "2012-01-01",
    "description": "some Description",
    "type": "museums",
    "localityName": "Warsaw"
}
```
 *Without Locality*
 
Body: JSON 
 ```bash
{
"attractionName": "SomeAttraction",
"creationDate": "2012-01-01",
"description": "some Description",
"type": "museums"
}
```
**Response: JSON**
```bash
{
        "errorId": "Could not find locality by Locality Name: [null]"
}
```

- **UpdateAttraction Description**
 ```bash
PATCH http://localhost:8082/attraction_library/rest/attraction/change/1
```
Params:

Key:
 ```bash
description value
```
Value:
``` bash
Some New Description
```
**Response: 1**

- Show All Attractions in Locality
 ```bash
GET http://localhost:8082/attraction_library/rest/attraction/show/Warsaw
```
**Response: JSON**
```bash
{
    "attractions": [
        {
            "attractionId": 1,
            "attractionName": "Old Town",
            "creationDate": "1339-01-01",
            "description": "Warsaw central Old Town neighborhood",
            "type": "palaces",
            "serviceNames": "tour guide, car rental"
        }
    ]
}
```
- **Show All Available Attractions using Filter And/Or Sorting By Type**

 You can use Filter types:  **PALACES** / **PARKS**/ **MUSEUMS**/ **ARCHAEOLOGICAL_SITES**/ **RESERVES**
 
 You can use Sorting  types: **ASC**/ **DESC**

 ```bash
GET http://localhost:8082/attraction_library/rest/attraction/show_all
```
*Without Any Sorting or Filter*

**Response: JSON**
```bash
{
    "attractions": [
        {
            "attractionId": 1,
            "attractionName": "Old Town",
            "creationDate": "1339-01-01",
            "description": "Warsaw central Old Town neighborhood",
            "type": "palaces",
            "serviceNames": "tour guide, car rental"
        },
        {
            "attractionId": 2,
            "attractionName": "Wawel Castle",
            "creationDate": "1364-01-01",
            "description": "Historic castle in Krakow",
            "type": "palaces",
            "serviceNames": "tour guide, catering"
        },
        {
            "attractionId": 3,
            "attractionName": "Wroclaw Zoo",
            "creationDate": "1865-07-10",
            "description": "One of the oldest zoos in Poland",
            "type": "parks",
            "serviceNames": "bike rental"
        },
        {
            "attractionId": 4,
            "attractionName": "Poznan Cathedral",
            "creationDate": "0968-01-01",
            "description": "Oldest cathedral in Poland",
            "type": "museums",
            "serviceNames": "tour guide"
        },
        {
            "attractionId": 5,
            "attractionName": "Gdansk Shipyard",
            "creationDate": "1945-01-01",
            "description": "Historic shipyard in Gdansk",
            "type": "archaeological_sites",
            "serviceNames": "boat tour"
        }
    ]
}
```
*With Sorting*

Params:

Key:
 ```bash
sortingDirection
```
Value:
``` bash
DESC
```
**Response: JSON**
```bash
{
    "attractions": [
        {
            "attractionId": 3,
            "attractionName": "Wroclaw Zoo",
            "creationDate": "1865-07-10",
            "description": "One of the oldest zoos in Poland",
            "type": "parks",
            "serviceNames": "bike rental"
        },
        {
            "attractionId": 2,
            "attractionName": "Wawel Castle",
            "creationDate": "1364-01-01",
            "description": "Historic castle in Krakow",
            "type": "palaces",
            "serviceNames": "tour guide, catering"
        },
        {
            "attractionId": 4,
            "attractionName": "Poznan Cathedral",
            "creationDate": "0968-01-01",
            "description": "Oldest cathedral in Poland",
            "type": "museums",
            "serviceNames": "tour guide"
        },
        {
            "attractionId": 1,
            "attractionName": "Old Town",
            "creationDate": "1339-01-01",
            "description": "Warsaw central Old Town neighborhood",
            "type": "palaces",
            "serviceNames": "tour guide, car rental"
        },
        {
            "attractionId": 5,
            "attractionName": "Gdansk Shipyard",
            "creationDate": "1945-01-01",
            "description": "Historic shipyard in Gdansk",
            "type": "archaeological_sites",
            "serviceNames": "boat tour"
        }
    ]
}
```
*With Filter*

Params:

Key:
 ```bash
typeFilter
```
Value:
``` bash
PALACES
```
**Response: JSON**
```bash
{
    "attractions": [
        {
            "attractionId": 1,
            "attractionName": "Old Town",
            "creationDate": "1339-01-01",
            "description": "Warsaw central Old Town neighborhood",
            "type": "palaces",
            "serviceNames": "tour guide, car rental"
        },
        {
            "attractionId": 2,
            "attractionName": "Wawel Castle",
            "creationDate": "1364-01-01",
            "description": "Historic castle in Krakow",
            "type": "palaces",
            "serviceNames": "tour guide, catering"
        }
    ]
}
```
*With Filter And Sorting*

Params:

Key:
 ```bash
sortingDirection
```
Value:
``` bash
DESC
```
Key:
 ```bash
typeFilter
```
Value:
``` bash
PALACES
```
**Response: JSON**
```bash
{
    "attractions": [
        {
            "attractionId": 2,
            "attractionName": "Wawel Castle",
            "creationDate": "1364-01-01",
            "description": "Historic castle in Krakow",
            "type": "palaces",
            "serviceNames": "tour guide, catering"
        },
        {
            "attractionId": 1,
            "attractionName": "Old Town",
            "creationDate": "1339-01-01",
            "description": "Warsaw central Old Town neighborhood",
            "type": "palaces",
            "serviceNames": "tour guide, car rental"
        }
    ]
}

```
- **Delete Attraction By ID**
 ```bash
DELETE http://localhost:8082/attraction_library/rest/attraction/delete/1
```




                                                                  
