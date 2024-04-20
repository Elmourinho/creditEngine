package org.example.decisionengine.error;

import org.springframework.http.HttpStatus;

public enum Errors implements ErrorResponse {

    CUSTOMER_NOT_FOUND("CUSTOMER_NOT_FOUND", HttpStatus.NOT_FOUND, "Customer with personal code {personalCode} not found");

    final String key;
    final HttpStatus httpStatus;
    final String message;

    Errors(String key, HttpStatus httpStatus, String message) {
        this.message = message;
        this.key = key;
        this.httpStatus = httpStatus;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
