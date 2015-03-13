package com.marcthomas.controllers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.marcthomas.dao.PersonRepository;
import com.marcthomas.model.Person;

/**
 * This class is responsible for handling Person requests, providing a POST
 * /person request mapping. It also validates the data and calls a service to
 * persist the person in the database.
 * 
 * Note. RestController is a new annotation that includes both @Controller and @ResponseBody
 * to simplify the code.
 * 
 * @author marcth
 */
@Controller
public class PersonController {
	private static final Logger log = Logger.getLogger(PersonController.class);

	public static final String NAME_REQUEST_PARAM = "name";
	public static final String DATE_OF_BIRTH_REQUEST_PARAM = "dateOfBirth";
	public static final String NUMBER_OF_CHILDREN_REQUEST_PARAM = "numberOfChildren";
	
	@Autowired
	private PersonRepository personRepository;

	@RequestMapping("/person") 
	public String greeting(Model model) {
        return "person";
    }
	
	@RequestMapping(value = "/person", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<PersonResponse> createPerson(
			@RequestParam(NAME_REQUEST_PARAM) String name,
			@RequestParam(DATE_OF_BIRTH_REQUEST_PARAM) String dateOfBirth,
			@RequestParam(NUMBER_OF_CHILDREN_REQUEST_PARAM) String numberOfChildren) {

		HttpStatus httpStatus;
		PersonResponse personResponse;

		List<String> validationResults = validatePerson(name, dateOfBirth,
				numberOfChildren);

		if (validationResults.isEmpty()) {
			httpStatus = HttpStatus.OK;
			personResponse = new PersonResponse(true);

			Person person;
			try {
				person = new Person(name, convertToDate(dateOfBirth), 2);
				log.info("Created person object: " + person);
				
				personRepository.save(person);
			} catch (ParseException e) {
				log.error("Failed to create person object because data of birth parsing failed.");
			}
		} else {
			httpStatus = HttpStatus.BAD_REQUEST;
			personResponse = new PersonResponse(false, validationResults);
			log.warn("Person validation failed: " + validationResults);
		}

		return new ResponseEntity<PersonResponse>(personResponse, httpStatus);
	}

	private List<String> validatePerson(String name, String dateOfBirth,
			String numberOfChildren) {
		List<String> validationErrors = new ArrayList<String>();

		if (!isNameValid(name)) {
			validationErrors.add(NAME_REQUEST_PARAM);
		}

		if (!isDateOfBirthValid(dateOfBirth)) {
			validationErrors.add(DATE_OF_BIRTH_REQUEST_PARAM);
		}

		if (!isNumberOfChildrenValid(numberOfChildren)) {
			validationErrors.add(NUMBER_OF_CHILDREN_REQUEST_PARAM);
		}

		return validationErrors;
	}

	private boolean isNameValid(String name) {
		return !name.isEmpty();
	}

	private boolean isDateOfBirthValid(String strDateOfBirth) {
		boolean dateValid = true;

		try {
			convertToDate(strDateOfBirth);
		} catch (ParseException e) {
			dateValid = false;
		}
		return dateValid;
	}

	private Date convertToDate(String strDateOfBirth) throws ParseException {
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
		return dateFormatter.parse(strDateOfBirth);
	}

	private boolean isNumberOfChildrenValid(String strNumberOfChildren) {
		boolean numberOfChildrenValid = true;
		Integer numberOfChildren;

		try {
			numberOfChildren = Integer.parseInt(strNumberOfChildren);
			if (numberOfChildren < 0) {
				numberOfChildrenValid = false;
			}
		} catch (NumberFormatException e) {
			numberOfChildrenValid = false;
		}

		return numberOfChildrenValid;
	}
}
