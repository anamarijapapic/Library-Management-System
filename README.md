# Library Management System

## Table of Contents

* [General Info](#general-info)
* [Features](#features)
* [Technologies](#technologies)
* [Database Model](#database-model)
* [Getting Started](#getting-started)
* [Credits](#credits)

## General Info

Library Management System is a web application that manages the library. It enables viewing the library catalog and taking actions on library materials and users. 📚👥

> Project created as a college seminar:  
> *SRC125 - Programming in Java*  
> *University of Split - University Department of Professional Studies*

![Library Management System - Home](https://user-images.githubusercontent.com/92815435/218094062-352f46a9-68e5-4524-a2a6-4dd2f96ceb25.png "Library Management System - Home")

## Features

### User Management

- Roles (base for authorization):
    - Admin
    - Librarian
    - Member
- Available operations:
    - User creation (adding new user)
    - Edit and update user data
    - Delete user
- Authentication (login/logout)
- Show all users (with search, filtering, sorting and pagination)
- Show user details for specific user
- Show current user details (My Details)

### Library Materials/Resources & Catalog Management

#### Authors

- Available operations:
    - Add new author
    - Edit and update author
    - Delete author
- View all authors (with search, sorting and pagination)

#### Categories

- Available operations:
    - Add new category
    - Edit and update category
    - Delete category
- View all categories (with search, sorting and pagination)

#### Works

- Available operations:
    - Add new work
    - Edit and update work
    - Delete work
- View all works (with search, filtering, sorting and pagination)
- View all book copies of specific work (with search, filtering and pagination)

#### Books

- Available operations:
    - Add new book
    - Edit and update book
    - Delete book
- View all books (with search, filtering and pagination)

#### Resource Borrowing - Loans

- Issuance and return of library resources
    - Start loan
        - Limitation: max `5` books per member borrowed at the same time
    - End loan
- View all loans (with sorting and pagination)
- View all loans of specific book (with sorting and pagination)
- View loans by member - current loans and previous loans (with sorting and pagination)

### Mail Notification Sending

- Send "Welcome" mail to user when his account is created
- Send "Loan Started" mail to member when he borrows a book
- Send "Loan Ended" mail to member when he returns a book 

## Technologies

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=java&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F.svg?style=for-the-badge&logo=Spring-Boot&logoColor=white)
![Spring Security](https://img.shields.io/badge/Spring%20Security-6DB33F.svg?style=for-the-badge&logo=Spring-Security&logoColor=white)  
![Thymeleaf](https://img.shields.io/badge/Thymeleaf-%23005C0F.svg?style=for-the-badge&logo=Thymeleaf&logoColor=white)
![Bootstrap](https://img.shields.io/badge/bootstrap-%23563D7C.svg?style=for-the-badge&logo=bootstrap&logoColor=white)
![HTML5](https://img.shields.io/badge/html5-%23E34F26.svg?style=for-the-badge&logo=html5&logoColor=white)
![CSS3](https://img.shields.io/badge/css3-%231572B6.svg?style=for-the-badge&logo=css3&logoColor=white)  
![JavaScript](https://img.shields.io/badge/javascript-%23323330.svg?style=for-the-badge&logo=javascript&logoColor=%23F7DF1E)
![jQuery](https://img.shields.io/badge/jquery-%230769AD.svg?style=for-the-badge&logo=jquery&logoColor=white)  
![Flyway](https://img.shields.io/badge/Flyway-CC0200.svg?style=for-the-badge&logo=Flyway&logoColor=white)  
![Postgres](https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white)  
![Docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)  

![IntelliJ IDEA](https://img.shields.io/badge/IntelliJIDEA-000000.svg?style=for-the-badge&logo=intellij-idea&logoColor=white)

## Database Model

![IntelliJ IDEA - Database Diagram](https://user-images.githubusercontent.com/92815435/218085760-eda35b13-4193-4f88-a39c-9d8345a842e0.png "IntelliJ IDEA - Database Diagram")  

## Getting Started

### Requirements

You should have the following installed:

- [Docker](https://www.docker.com/community-edition) bundled with docker-compose

### Running the Application

Run the following commands in terminal:  

1. Position in the project folder (run from repo root directory):  
`cd library-management-system/`

2. Build project .jar file in target folder:  
`./mvnw clean package -DskipTests`

3. Build and start the docker environment and local web server:  
`docker-compose up` (you can stop it with a single `cmd/ctrl+c`)

4. Open http://localhost:8080/ in your browser.

If for any reason you need to modify the code and apply changes, you should:

1. Stop and remove Docker containers and their volumes:  
`docker-compose down`

2. Remove `library-management-system.jar` Docker image:  
`docker rmi library-management-system.jar`

3. Repeat previous steps 2.-4.

#### Managing the Database

You can see the PostgreSQL database inside IntelliJ IDEA Database window, just do the following:  

`View` -> `Tool Windows` -> `Database` -> `+` -> `Data Source from URL` and enter  

- URL: `jdbc:postgresql:///postgres`
- Driver: `PostgreSQL`

Then configure properties:  

- Username: `postgres`
- Password: `postgres`
- Database: `postgres`
- Host: `localhost`
- Port: `5432`

#### Testing Mail Sending

[MailHog](https://github.com/mailhog/MailHog) Web UI, an email testing tool for developers, is available at http://localhost:8025/.

## Credits

 ✍️ **api-team** members: 

* [Ivana Mihanović](https://github.com/imihanovic)
* [Anamarija Papić](https://github.com/anamarijapapic)
* [Petar Vidović](https://github.com/Petar1107)
