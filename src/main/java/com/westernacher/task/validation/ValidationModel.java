package com.westernacher.task.validation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ValidationModel implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<FieldErrorModel> fieldErrors;

	public void add(String fieldName, String message) {
		if (fieldErrors == null) {
			fieldErrors = new ArrayList<>();
		}
		fieldErrors.add(new FieldErrorModel(fieldName, message));
	}

	public List<FieldErrorModel> getFieldErrors() {
		return fieldErrors;
	}

	public void setFieldErrors(List<FieldErrorModel> fieldErrors) {
		this.fieldErrors = fieldErrors;
	}
}
