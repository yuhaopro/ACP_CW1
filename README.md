# Spring Boot Application
## Description
Hi all, this is a working springboot application which I have done as part of my applied cloud programming coursework at UOE.
<br>
This application uses an in memory database (H2) to store data, and a REST controller for managing the endpoints.
It is a simple starting template for anyone that wants to build a REST service in spring boot.
## Pre-requisite
This project assumes you have maven installed.
## Getting Started
1. clone the project
```
git clone https://github.com/yuhaopro/SpringBootApplication.git
```
2. clean build with maven
```
mvn clean install -Dmaven.test.skip=true	
```
3. Start the application locally. mvnw can be found in the root directory.
```
./mvnw spring-boot:run
```
