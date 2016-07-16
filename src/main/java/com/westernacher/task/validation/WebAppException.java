package com.westernacher.task.validation;

public class WebAppException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private final String message;

	public WebAppException(String message) {
		this.message = message;
	}

	@Override
	public String getMessage() {
		return message;
	}

}
