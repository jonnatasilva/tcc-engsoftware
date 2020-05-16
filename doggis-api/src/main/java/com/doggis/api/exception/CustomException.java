package com.doggis.api.exception;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Set;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class CustomException extends RuntimeException {
	
	private static final long serialVersionUID = 6292068144267705145L;

	private final Long code;
	private final LocalDateTime timestamp;
	private final String message;
	private final String description;
	private final Set<CustomException> errors;

	private CustomException(Long code, LocalDateTime timestamp, String message, String description, Set<CustomException> errors) {
		this.code = code;
		this.timestamp = timestamp;
		this.message = message;
		this.description = description;
		this.errors = errors;
	}
	
	public static CustomException resourceNotFoundException(Long resourceId) {
		return new ResourceNotFoundException(resourceId);
	}
	
	static class ResourceNotFoundException extends CustomException {
		
		private static final long serialVersionUID = -2272113709764383269L;

		public ResourceNotFoundException(Long resourceId) {
			super(ErrorCode.RESOURCE_NOT_FOUND.getCode()
					, LocalDateTime.now()
					, "Resource not found."
					, String.format("Resource with id %s is not found.", resourceId)
					, Collections.emptySet());
		}
	}
	
	public static enum ErrorCode {
		
		RESOURCE_NOT_FOUND(1L),
		BAD_REQUEST(2L);
		
		private final Long code;
		
		private ErrorCode(Long code) {
			this.code = code;
		}
		
		public Long getCode() {
			return code;
		}
	}
}
