package com.bank.exception;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.PropertyValueException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.bank.dto.ResponseDto;

@ControllerAdvice
public class ValidationExceptionHandler {

	@ExceptionHandler(PropertyValueException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public ResponseEntity<ResponseDto> badPropertyValue(PropertyValueException ex) {
		ResponseDto response = new ResponseDto();
		response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		response.setMensaje(ex.getLocalizedMessage());
		if (ex.getLocalizedMessage().startsWith("not-null property")) {
			response.setMensaje(String.format("La propiedad '%s' no puede ser nula", ex.getPropertyName()));
		}
		if (ex.getLocalizedMessage().startsWith("not-blank property")) {
			response.setMensaje(String.format("La propiedad '%s' debe tener texto", ex.getPropertyName()));
		}
		return ResponseEntity.badRequest().body(response);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ResponseEntity<ResponseDto> badRequest(MethodArgumentNotValidException ex, HttpServletRequest request) {
		ResponseDto response = new ResponseDto(HttpStatus.BAD_REQUEST.value(), "Petición incorrecta");
		response.setPath(request.getRequestURI());
		Map<String, Object> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach(error -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		response.setError(errors);
		return ResponseEntity.badRequest().body(response);
	}
}
