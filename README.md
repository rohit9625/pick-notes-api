# Pick Notes API
This is the backend of the [pick-notes](https://github.com/rohit9625/pick-notes) Android App. It is built using **Ktor Client** and **JWT** for secure authentication & authorization. The API enables users to create, update, delete, and manage their notes. It is designed to be simple, secure, and easy to integrate with front-end applications such as web or mobile apps.

## API End Points
**Base URL**: 
```
https://pick-notes-api-1e9ac8fd48ce.herokuapp.com
```
### Authentication

- **Register a new user**
  
  ```http
  POST /users/register

  {
    "username": "your_username",
    "email": "your_email",
    "password": "your_password"
  }
  ```
  Response with code **`201 Created`**
  ```http
  {
    "success": true,
    "data": {
        "token": "eyJhb....."
    },
    "message": "User successfully registered"
  }
  ```
- **Login user**
  
  ```http
  POST /users/login

  {
    "email": "your_email",
    "password": "your_password"
  }
  ```
  Response with code **`200 OK`**
  ```http
  {
    "success": true,
    "data": {
        "token": "eyJhb....."
    },
    "message": "User successfully logged in"
  }
  ```
### Notes
- Get All Notes
  ```http
  GET /notes

  {
    "success": true,
    "data": [
        {
            "id": 1,
            "userId": 1,
            "title": "First Modified Note",
            "description": "This is my first note for sample."
        },
        {
            "id": 2,
            "userId": 1,
            "title": "Second Note",
            "description": "This is my second note for sample."
        }
    ],
    "message": "All notes retrieved successfully"
  }
  ```
- Create Note
  ```http
  POST /notes/create
  
  {
      "title": "First Notes",
      "description": "This is my first note for sample."
  }
  ```
  Response with code **`201 Created`**
  ```http
  {
      "success": true,
      "data": {
          "id": 1,
          "userId": 1,
          "title": "First Notes",
          "description": "This is my first note for sample."
      },
      "message": "Note created successfully"
  }
  ```
- Read Note

    Response with code **`200 OK`**
  ```http
  GET /notes/{id}
  
  {
      "success": true,
      "data": {
          "id": 1,
          "userId": 1,
          "title": "First Notes",
          "description": "This is my first note for sample."
      },
      "message": "Note created successfully"
  }
  ```

- Update Note

    Response with code **`200 OK`**
  ```http
  PUT /notes/update
  
  {
    "id": 1,
    "userId": 1,
    "title": "First Modified Note",
    "description": "This is my first note for sample."
  }
  ```
- Delete Note

  Response with code **`200 OK`**
  ```http
  DELETE /notes/delete/{id}
  
  {
    "success": true,
    "data": {
        "id": 35,
        "userId": 67,
        "title": "Second Note",
        "description": "This is my second note for sample."
    },
    "message": "Note deletion successful"
  }
  ```

