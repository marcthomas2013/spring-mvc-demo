package com.marcthomas.controllers;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is responsible for the data that is returned by the
 * PersonController when a Person is being created. It contains 
 * whether it was successful and any form names that failed validation.
 * 
 * @author marcth
 */
public class PersonResponse {
	private boolean successful;
	private List<String> validationErrors = new ArrayList<String>();

	public PersonResponse(boolean successful) {
		this.successful = successful;
	}

	public PersonResponse(boolean successful, List<String> validationErrors) {
		this.successful = successful;
		this.validationErrors = validationErrors;
	}

	public boolean isSuccessful() {
		return successful;
	}

	public void setSuccessful(boolean successful) {
		this.successful = successful;
	}

	public List<String> getValidationErrors() {
		return validationErrors;
	}

	public void setValidationErrors(List<String> validationErrors) {
		this.validationErrors = validationErrors;
	}
}
