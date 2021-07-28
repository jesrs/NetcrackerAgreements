package com.netcracker.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.netcracker.error.AgreementNotFoundException;

/*
 * Controller to configure the response of the agreement not found exception
 * Jesús Rodríguez Salazar jesrs@yahoo.com
 * v1.0
 * Date: 27/07/2021
 */
@ControllerAdvice
public class AgreementExceptionController {

	@ExceptionHandler(value = AgreementNotFoundException.class)
	public ResponseEntity<Object> exception(AgreementNotFoundException exception) {
		
		// The message to show is Agreement not found and uses the 404 status
		return new ResponseEntity<>("Agreement not found", HttpStatus.NOT_FOUND);
	}

}
