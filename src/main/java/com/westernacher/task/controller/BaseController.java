package com.westernacher.task.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public abstract class BaseController {

	protected <T> ResponseEntity<T> buildResponse(T returnValue) {
		return new ResponseEntity<T>(returnValue, HttpStatus.OK);
	}
}
