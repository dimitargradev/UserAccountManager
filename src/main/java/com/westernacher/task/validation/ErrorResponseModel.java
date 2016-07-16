package com.westernacher.task.validation;

public class ErrorResponseModel {

	private final String message;

	public ErrorResponseModel(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
