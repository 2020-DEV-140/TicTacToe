# TicTacToe Game

This project is an implementation of the traditional TicTacToe game written in Java and Angular


## Tech Stack

Java 8\
Spring Boot 2.2.9 (Spring 5, Spring Security, Spring Data JPA)\
Hibernate 5.4\
Angular 10\
Maven\
PostgreSQL/H2


## Usage

To install the game, run mvn spring-boot:run\
To execute the tests, run mvn test\
To simplify both application and test execution, the H2 embedded database has been configured with two user accounts, namely *george* and *bnptest* (same user/pass)


## How to play
Registered users are asked to login before playing the game against the computer. Once logged-in, they can start by choosing their piece of preference and then pressing the Create Game button. 
X always plays first, which means that if the user chooses X, she/he initiates the game, otherwise she/he has to wait for the computer move provided by the system. Piece choice is only available at login.


## Notes
This enterprise application consists of the following components:\
Presentation — implemented by an Angular-based front-end and REST controllers at the back-end exchanging information through DTOs.\
Business logic — the application’s business logic consisting of transaction-aware services.\
Persistence layer — data access objects (DAOs) and JPA entities responsible for accessing the database.\
Database layer — development was done in PostgreSQL.\
Authentication — only registered users can access the system. User passwords are hashed usign Argon2, as this is considered a better candidate than the other popular options, such as MD5, BCrypt and SCrypt.

