# Hostel Management Backend

This is a backend application for managing a hostel, built using Java, Spring Boot, and Maven. It provides RESTful APIs for managing residents and rooms in the hostel.

## Features

- Manage residents (CRUD operations)
- Manage rooms (CRUD operations)
- Export residents data to CSV

## Technologies Used

- Java
- Spring Boot
- Maven
- JPA (Hibernate)
- OpenCSV

## Prerequisites

- Java 17 or higher
- Maven 3.6.0 or higher
- MySQL database

## Getting Started

### Clone the Repository

```bash
git clone https://github.com/Amrish-Sharma/hostel_management.git
cd hostel_management
```

### Configure the Database

Update the `application.properties` file located in `src/main/resources` with your MySQL database configuration:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/hostel_management
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
```

### Build the Project

```bash
mvn clean install
```

### Run the Application

```bash
mvn spring-boot:run
```

The application will start on `http://localhost:9090`.

## API Endpoints

### Residents

- **Get all residents**
    - **URL:** `/api/residents`
    - **Method:** `GET`
    - **Response:** List of residents

- **Get resident by ID**
    - **URL:** `/api/residents/{id}`
    - **Method:** `GET`
    - **Response:** Resident object

- **Add a new resident**
    - **URL:** `/api/residents`
    - **Method:** `POST`
    - **Request Body:** Resident object
    - **Response:** Created resident object

- **Update a resident**
    - **URL:** `/api/residents/{id}`
    - **Method:** `PUT`
    - **Request Body:** Resident object
    - **Response:** Updated resident object

- **Delete a resident**
    - **URL:** `/api/residents/{id}`
    - **Method:** `DELETE`
    - **Response:** `204 No Content`

- **Export residents to CSV**
    - **URL:** `/api/residents/csv/export`
    - **Method:** `GET`
    - **Response:** CSV file download

### Rooms

- **Get all rooms**
    - **URL:** `/api/rooms`
    - **Method:** `GET`
    - **Response:** List of rooms

- **Get available rooms**
    - **URL:** `/api/rooms/available`
    - **Method:** `GET`
    - **Response:** List of available rooms

- **Add a new room**
    - **URL:** `/api/rooms`
    - **Method:** `POST`
    - **Request Body:** Room object
    - **Response:** Created room object

- **Delete a room**
    - **URL:** `/api/rooms/{roomId}`
    - **Method:** `DELETE`
    - **Response:** `204 No Content`

## Error Handling

In case of errors, the API will return appropriate HTTP status codes along with error messages.

## License

This project is licensed under the MIT License. See the `LICENSE` file for details.