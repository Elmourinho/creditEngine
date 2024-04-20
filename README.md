
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
I began with the backend, focusing initially on the core logic, particularly the credit score formula provided. Upon deeper consideration, I found that the requested amount didn't influence this logic significantly and could potentially be removed from the request actually. Nevertheless, we aim to offer the highest feasible amount to our customers. To bring this to life, I opted for Spring Boot, my go-to framework, and proceeded to implement the necessary services, along with thorough testing and validation. For the frontend, Vue.js was my choice, and I crafted a straightforward application to interface with the backend's APIs.
