package com.greatLearning.employeeManagement.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.AllArgsConstructor;
import lombok.Getter;

@RestControllerAdvice
@Component
public class GlobalExceptionHandler {

	public Map<Integer, Object> handleInvalidRequest(IllegalArgumentException exception) {
		Map<Integer, Object> response = new HashMap<>();
		response.put(200, new Error(100, exception.getMessage()));
		return response;
	}

}

@AllArgsConstructor
@Getter
class Error {

	private int id;
	private String message;

}