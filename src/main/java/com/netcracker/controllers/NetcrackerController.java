package com.netcracker.controllers;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.netcracker.error.AgreementNotFoundException;
import com.netcracker.utilities.Agreement;
import com.netcracker.utilities.AgreementFile;
import com.netcracker.utilities.GenericResponse;

/*
 * Controller to recieve the petitions
 * Jesús Rodríguez Salazar jesrs@yahoo.com
 * v1.0
 * Date: 27/07/2021
 */
@RestController
@RequestMapping("/netcracker")
public class NetcrackerController {
	

	// Creates the file according to the agreement received
	@PostMapping(value = "/createAgreement")
	public ResponseEntity<GenericResponse> createAgreement(@RequestBody Agreement agreement) throws IOException{
		
		// Creates the generic response and sets the path
		GenericResponse genericResponse = new GenericResponse();
		genericResponse.setPath("/netcracker/createAgreement");
		
		// Creates the file with the agreement
		 AgreementFile agreementFile = new AgreementFile();
		 agreementFile.createAgreementFile(agreement);
		
		// Returns the response with status ok
		return new ResponseEntity<>(genericResponse, HttpStatus.OK);
		
	}
	
	// Reads the agreement file 
	@GetMapping(value = "/getAgreement")
	public Agreement getAgreement (@RequestParam("agreementName") String agreementName) throws IOException {
		
		Agreement agreement = null;
		AgreementFile agreementFile = new AgreementFile();
		
		// Validates if the agreement's file exists
		if (agreementFile.validateAgreementExist(agreementName)) {
			// if exists reads the file to generate the agreement
			agreement = agreementFile.readAgreement(agreementName);
		} else {
			// If not exists sends the exception
			throw new AgreementNotFoundException();
		}
		
		//returns the agreement
		return agreement;
		
	}

}
