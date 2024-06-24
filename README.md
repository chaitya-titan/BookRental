# Coding RentRead Application

## Description

* This is a Book Renting System backend application built with Spring Boot and Spring Data JPA using MySQL. It provides RESTful APIs for creating, reading, updating, and deleting Book and renting them.

## Running the Application

#### Just use run on LearningSystemApplication if on IntelliJ IDEA or use the following command if using Gradle

```bash
./gradlew bootRun
```

## Database

### MySQL

#### If you don't have mysql installed, run the following command
```bash
brew install mysql
mysql --version
```

#### To Login to MySQL

```bash
brew services start mysql
mysql
mysql -u root -p
CREATE DATABASE <your-database-name>;
```

#### Add this in application.properties
```
spring.application.name=learning_system
spring.datasource.url=jdbc:mysql://localhost:3306/lms
spring.datasource.username=root
spring.datasource.password=<your-password>
spring.jpa.hibernate.ddl-auto=update

server.port=8080
```



## API Endpoints

### Book API

### `POST /books`
Create a new book

- **Request Body**:
    ```json
    {
        "title": "string",
        "author": "string",
        "isbn": "string",
        ...
    }
    ```
- **Response**:
    - **Status**: `201 Created`
    - **Body**:
        ```json
        {
            "id": "string",
            "title": "string",
            "author": "string",
            "isbn": "string",
            ...
        }
        ```

### `GET /books`
Retrieve a list of all books

- **Response**:
    - **Status**: `200 OK`
    - **Body**:
        ```json
        [
            {
                "id": "string",
                "title": "string",
                "author": "string",
                "isbn": "string",
                ...
            },
            ...
        ]
        ```

### `GET /books/{bookId}`
Retrieve the details of a specific book

- **Parameters**:
    - `bookId` (path): ID of the book to retrieve

- **Response**:
    - **Status**: `200 OK`
    - **Body**:
        ```json
        {
            "id": "string",
            "title": "string",
            "author": "string",
            "isbn": "string",
            ...
        }
        ```

### `PUT /books/{bookId}`
Update the details of a specific book

- **Parameters**:
    - `bookId` (path): ID of the book to update

- **Request Body**:
    ```json
    {
        "title": "string",
        "author": "string",
        "isbn": "string",
        ...
    }
    ```

- **Response**:
    - **Status**: `200 OK`
    - **Body**:
        ```json
        {
            "id": "string",
            "title": "string",
            "author": "string",
            "isbn": "string",
            ...
        }
        ```

### `DELETE /books/{bookId}`
Delete a specific book

- **Parameters**:
    - `bookId` (path): ID of the book to delete

- **Response**:
    - **Status**: `200 OK`
    - **Body**:
        ```json
        {
            "message": "Book deleted successfully"
        }
        ```

### User API

### `POST /users/register`
Register a new user

- **Request Body**:
    ```json
    {
        "username": "string",
        "password": "string",
        ...
    }
    ```
- **Response**:
    - **Status**: `201 Created`
    - **Body**:
        ```json
        {
            "id": "string",
            "username": "string",
            ...
        }
        ```

### `POST /users/login`
Login a user

- **Request Body**:
    ```json
    {
        "username": "string",
        "password": "string"
    }
    ```
- **Response**:
    - **Status**: `200 OK`
    - **Body**:
        ```json
        {
            "token": "string",
            "username": "string",
            ...
        }
        ```

### `POST /users/admin/register`
Register a new admin

- **Request Body**:
    ```json
    {
        "username": "string",
        "password": "string",
        ...
    }
    ```
- **Response**:
    - **Status**: `201 Created`
    - **Body**:
        ```json
        {
            "id": "string",
            "username": "string",
            ...
        }
        ```


### Rent API

### `GET /rent`
Retrieve a list of all available books for rent

- **Response**:
    - **Status**: `200 OK`
    - **Body**:
        ```json
        [
            {
                "id": "string",
                "title": "string",
                "author": "string",
                "isbn": "string",
                ...
            },
            ...
        ]
        ```

### `POST /rent/{bookId}`
Rent a book

- **Parameters**:
    - `bookId` (path): ID of the book to rent

- **Response**:
    - **Status**: `200 OK`
    - **Body**:
        ```json
        {
            "id": "string",
            "title": "string",
            "author": "string",
            "isbn": "string",
            "rentedOn": "date",
            "dueDate": "date",
            ...
        }
        ```

### `POST /rent/return/{bookId}`
Return a rented book

- **Parameters**:
    - `bookId` (path): ID of the book to return

- **Response**:
    - **Status**: `200 OK`
    - **Body**:
        ```json
        {
            "message": "Book returned successfully"
        }
        ```



## Postman API

#### A Postman collection for testing the API is available [here](https://api.postman.com/collections/20879467-3a946d43-08d1-495b-a5aa-2a0c42f80b73?access_key=PMAT-01J15D47TSX6C030BM4N4EGFFC).