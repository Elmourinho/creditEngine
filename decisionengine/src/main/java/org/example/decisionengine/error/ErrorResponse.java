package org.example.decisionengine.error;

import org.springframework.http.HttpStatus;

public interface ErrorResponse {

	String getKey();

	String getMessage();

	HttpStatus getHttpStatus();
}
