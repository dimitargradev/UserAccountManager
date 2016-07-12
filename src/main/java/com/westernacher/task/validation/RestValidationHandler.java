package com.westernacher.task.validation;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class RestValidationHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ValidationModel getValidationModel(MethodArgumentNotValidException exc) {
		final BindingResult result = exc.getBindingResult();
		final List<FieldError> fieldErrors = result.getFieldErrors();
		return processFieldErrors(fieldErrors);
	}

	private ValidationModel processFieldErrors(List<FieldError> fieldErrors) {
		final ValidationModel validationModel = new ValidationModel();
		fieldErrors.forEach(err -> {
			validationModel.add(err.getField(), err.getDefaultMessage());
		});
		return validationModel;
	}

}
