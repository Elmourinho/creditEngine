
# Credit Decision Engine Application

Java21, Spring Boot 3.2, Vue js

## Description

This application streamlines the credit request decision process for users across different segments.
## Installation

Change directory to the backend app

```bash
  cd decisionengine
```
Start Spring Boot application
```bash
  ./gradlew bootRun &
```
Change directory to the frontend app
```bash
  cd creditapp
```
Install dependencies if needed
```bash
  npm install
```
Start Vue.js application
```bash
  npm run serve
```
App will be available at:
```bash
http://localhost:5173
```
Swagger for backend:
```bash
  http://localhost:8080/swagger-ui/index.html#/
```

## General thoughts:
I began by focusing on the backend development, prioritizing the core logic, particularly regarding the credit score calculation formula. One aspect that required careful consideration was determining the appropriate time period for new customers. While the main description outlined the need to provide decisions and approved amounts, there was also mention of investigating new time periods.To bring this to life, I opted for Spring Boot, my go-to framework, and proceeded to implement the necessary services, along with thorough testing and validation. For the frontend, I opted for Vue.js and created a straightforward application to interact with the backend API seamlessly.