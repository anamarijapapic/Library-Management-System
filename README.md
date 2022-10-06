# Library Management System

## Table of Contents

* [General Info](#general-info)
* [Features](#features)
* [Technologies](#technologies)
* [Database Model](#database-model)
* [Authors](#authors)

## General Info

Library Management System is a web application that manages the library. It enables viewing the library catalog and taking actions on library materials and users. 📚👥

> Project created as a college seminar:  
> *SRC125 - Programming in Java*  
> *University of Split - University Department of Professional Studies*

## Features

### User Management
- Roles:
    - Member
    - Librarian
    - Admin
- Registration and login
- Edit and update user data

### Library Materials/Resources Management
- CRUD operations over library resources (add, display, edit/update, delete)

### View (& Search/Filter) Library Catalog

### Resource Borrowing - Loan
- Issuance and return of library resources
- Limitation: max `x` resources on loan at the same time
- Option: extend the loan for `x` days

### Resource Reservation - Hold
- Check the availability of the requested resource

### View Currently Borrowed Resources

### View Borrowed Resources History

### Billing
- Membership
    - New loans are possible only if the user has an active membership
    - Pricing by user age:
        - Reduced price (adolescent (<26), senior (>=60))
        - Regular price
- Late Fee
    - Prohibit future loans until the late fee is paid
    - Calculate late fee by days

## Technologies

* Java
* Spring Boot
* Thymeleaf
* Bootstrap
* DBMS `{{TO-DO}}`
* Docker

## Database Model

`{{TO-DO}}`

## Authors

 ✍️ **api-team** members: 
* [Ivana Mihanović](https://github.com/imihanovic)
* [Anamarija Papić](https://github.com/anamarijapapic)
* [Petar Vidović](https://github.com/Petar1107)
