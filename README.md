# TicTacToe Game

This project is an implementation of the traditional TicTacToe game written in Java and Angular


## Tech Stack

Java 8\
Spring Boot 2.2.9 (Spring Security, Spring Data JPA)
Hibernate 5.4
Angular 10
Maven 
PostgreSQL/H2


## Usage

To install the game, run mvn spring-boot:run
To execute the tests, run mvn test
To simplify both application and test execution, the H2 embedded database has been configured with two user accounts, namely george, bnp-test (same user/pass)


## How to play
Registered users are asked to login before playing the game against the computer. Once logged-in, they can start by choosing their piece of preference and then pressing the Create Game button. 
X always plays first, which means that if the user chooses X, she/he initiates the game, otherwise she/he has to wait for the computer move provided by the system. Piece choice is only available at login.





