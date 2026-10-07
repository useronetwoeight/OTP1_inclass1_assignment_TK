## Temperature Converter

### 1. Assignment Description

Temperature Converter is a Java app made for OTP1 courses individual assignments.

You can convert temperatures between Kelvin, Celsius and Fahrenheit. 
You also can enter time and distance to get an average speed. 
The app saves every calculation to a database. 
---

#### Problem Summary

No one likes calculating for a long time if it's about efficiency.
Our task was to implement the newly learned techs of Maven, Jenkins and Docker to make an app to make calculating efficient.
---

#### Key Requirements

- Working code with the conversion of temperatures and calculation of speed.
- JFX UI.
- Storing the data to a database.
- A Working link between Docker and Jenkins.
- Tests with JUnit and JaCoCo.

---

#### Deliverables

- GitHub repository links.
- `pom.xml` for JaCoCo tests.
- `Dockerfile` to run the app in a Docker container.
- `JenkinsFile.jf` and the pipeline.
- `README.md`

---

#### Features

- Conversion of temperature between Celsius, Fahrenheit and Kelvin.
- Calculate speed from time and distance.
- Save calculations to a database.

---

### 2. Technologies Used

- Java 21
- JFX 
- Maven
- Jenkins
- Docker
- H2
- JUnit 5
- Maven 
- JaCoCo
- Xming

---

### 3. Design Approach & Implementation Method

#### Overall structure

Multiple small classes with simple jobs.

- `DBConnection` database connection.
- `Launcher` to start `Main` to fix a error with JFX not starting.
- `Main` for JFX running and buttons.
- `TemperatureConverter` for logic and converting.
- `TemperatureUnit` to make code easier to read.
- `TemperatureUnitDAO` for database CRUD.
- `TempRecord` to make code easier to read.
- `TempRecordDAO` for database CRUD.

#### GUI design

Simple and easy to understand

#### Database design

The database has two tables:

- `temperature_unit` id, name, symbol
- `temp_record` id, from_unit_id, to_unit_id, input_value, result_value, distance_km, time_hours, speed_kmh

#### Key decisions

- Database in H2 because it is easy to run and works for testing.

---

### 4. Testing

Testing was done with JaCoCo, JUnit 5, H2 and manually.

- **Testing and results**

Convert 100 Celsius to Fahrenheit, distance 150km, time 3h
Outcome: 212 F and speed 50km/h

Shutting down the app and starting showed last results.

---

### 5. How to Run

#### Local running 
**Requirements**
- JDK 21 and Maven

**Run**
- Clone the code from my Github: https://github.com/useronetwoeight/OTP1_inclass1_assignment_TK
- Run `mvn clean install`
- Run `mvn javafx:run`

#### Running with Docker

**Requirements**
- Docker and XMing

- Clone the code from my Github: https://github.com/useronetwoeight/OTP1_inclass1_assignment_TK
- Make sure XMing is running
- Run `docker build -t temperature-converter`
- Run `docker run --rm -v tempdata:/app/data temperature-converter`

#### Running tests

- Run `mvn test`
- for coverage report run `mvn clean verify`

---

### 6. Author

- Tuomas Kolari
