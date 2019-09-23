package com.animals.care.animalscare.exception;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.animals.care.animalscare.exception.CustomException.ErrorCode;
import com.animals.care.animalscare.exception.CustomException.ResourceNotFoundException;

import lombok.AllArgsConstructor;
import lombok.Data;

@RestControllerAdvice
public class CustomExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ErrorResponse resourceNotFoundExceptionHandler(CustomException exception) {
		return new ErrorResponse(exception.getCode()
				, exception.getTimestamp()
				, exception.getMessage()
				, exception.getDescription()
				, Collections.emptySet());
	}
		
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ErrorResponse handleValidationExceptions(MethodArgumentNotValidException ex) {
		FieldError error = ex.getBindingResult().getFieldError();
		return new ErrorResponse(ErrorCode.BAD_REQUEST.getCode()
				, LocalDateTime.now()
				, error.getDefaultMessage()
				, String.format("The value %s to the field %s is not valid", error.getRejectedValue(), error.getField()) 
				, Collections.emptySet());
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(IllegalStateException.class)
	public ErrorResponse handleIllegalStateException(IllegalStateException ex) {
		return new ErrorResponse(ErrorCode.BAD_REQUEST.getCode()
				, LocalDateTime.now()
				, ex.getMessage()
				, ""
				, Collections.emptySet());
	}
	
	@ExceptionHandler(RuntimeException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ErrorResponse exceptionHandler(RuntimeException exception) {
		return new ErrorResponse(Long.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value())
				, LocalDateTime.now()
				, exception.getMessage()
				, exception.getMessage()
				, Collections.emptySet());
	}
	
	@Data
	@AllArgsConstructor	
	private static class ErrorResponse {
		
		private Long code;
		private LocalDateTime timestamp;
		private String message;
		private String description;
		private Set<ErrorResponse> errors;
	}
}
