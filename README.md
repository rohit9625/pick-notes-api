# Pick Notes API
This is the backend of the [pick-notes](https://github.com/rohit9625/pick-notes) Android App. It is built using **Ktor Client** and **JWT** for secure authentication & authorization. The API enables users to create, update, delete, and manage their notes. It is designed to be simple, secure, and easy to integrate with front-end applications such as web or mobile apps.

## API End Points
**Base URL**: 
```
https://pick-notes-api-1e9ac8fd48ce.herokuapp.com
```
### Authentication

- **Register a new user** ``` POST /users/register```

	Request Body:
  ```http
  {
    "username": "your_username",
    "email": "your_email",
    "password": "your_password"
  }
  ```
  Response Body:
  ```http
  {
    "success": true,
    "data": {
        "token": "eyJhb....."
    },
    "message": "User successfully registered"
  }
  ```
- **Login user** ```POST /users/login```

  Request Body:
  ```http
  {
    "email": "your_email",
    "password": "your_password"
  }
  ```
  Response Body:
  ```http
  {
    "success": true,
    "data": {
        "token": "eyJhb.....token"
    },
    "message": "User successfully logged in"
  }
  ```
  
### Notes
Set the **`Bearer token`** in the header before sending any **POST** request to the `/notes` end-point.

- **Get All Notes** ```GET /notes```

  ```http
  {
    "success": true,
    "data": [
        {
            "id": 1,
            "userId": 1,
            "title": "First Updated Note",
            "description": "This is my very first note.",
            "createdAt": 1727859582222,
            "updatedAt": 1727860286634
        },
        {
            "id": 2,
            "userId": 1,
            "title": "Second Note",
            "description": "This is my second note.",
            "createdAt": 1727867248390,
            "updatedAt": 1727867248390
        }
    ],
    "message": "All notes retrieved successfully"
  }
  ```
- **Create Note** ```POST /notes/create```
 Request Body:
  ```http
  {
    "title": "First Title",
    "description": "This is my first note."
  }
  ```
  Response Body:
  
  ```http
  {
    "success": true,
    "data": {
        "id": 1,
        "userId": 1,
        "title": "First Note",
        "description": "This is my first note.",
        "createdAt": 1727867248390,
        "updatedAt": 1727867248390
    },
    "message": "Note created successfully"
  }
  ```
  
- **Update Note** ```PUT /notes/update```

	Request Body:
  ```http
  {
    "id": 1,
    "title": "First Modified Note",
    "description": "This is my first modified note."
  }
  ```
    Response Body:
  ```http
  {
    "success": true,
    "data": {
        "id": 1,
        "userId": 1,
        "title": "First Updated Note",
        "description": "This is my first modified note.",
        "createdAt": 1727867248390,
        "updatedAt": 1727868535957
    },
    "message": "Note updation successful"
  }
  ```
- **Delete Note** ```DELETE /notes/delete/{id}```

  Response Body:
  ```http
  {
    "success": true,
    "data": {
        "id": 1,
        "userId": 1,
        "title": "First Modified Note",
        "description": "This is my first modified note."
    },
    "message": "Note deletion successful"
  }
  ```
  
## Local Setup

To set up and run the Pick Notes API locally, follow these steps:

### Prerequisites

- [IntelliJ IDEA](https://www.jetbrains.com/idea/) installed.
- [PostgreSQL](https://www.postgresql.org/download/) installed and running.
- [PgAdmin](https://www.pgadmin.org/) installed (optional).

### Step 1: Clone the Repository

```bash
git clone https://github.com/rohit9625/pick-notes-api.git
cd pick-notes-api
```
### Step 2: Open & Setup the project

- Open project in **IntelliJ IDEA**
- Setup Database from **Postgresql CLI** or **PgAdmin** by running:
  
  ```bash
  CREATE DATABASE db-name;
  ```
- Setup environment variables from **Run/Debug Configurations** option.
  
  ```
  DATABASE_URL=postgres://username:password@host:port/db-name,
  HASH_KEY=your_hash_key
  JWT_SECRET=your_secret
  ```
- Don't change the structure of the URL. Only replace username, password, host, port, and db-name with your own.

### Step 3: Build & Run the project

- You can project by clicking **Run** button on IntelliJ IDEA
- To build the project using **Gradle**
  
  ```bash
  ./gradlew build
  ```
- Then run the API locally
  
  ```bash
  ./gradlew run
  ```
  
## Contributing

Contributions are welcome! If you'd like to contribute to this project, please fork the repository, create a new branch, and submit a pull request. For major changes, please open an issue first to discuss what you would like to change.

## Contact

For any questions, issues, or suggestions, feel free to reach out to me:

- GitHub: [rohit9625](https://github.com/rohit9625)
- Email: [rv17837@outlook.com](mailto:rv17837@outlook.com)

## Acknowledgements

- [Ktor](https://ktor.io/) – Framework for building the API.
- [JWT](https://jwt.io/) – Secure authentication and authorization.
- [Heroku](https://www.heroku.com/) – For hosting the API.

### Thank you for checking out the Pick Notes API :)

