package com.marcthomas.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.marcthomas.controllers.PersonController;
import com.marcthomas.controllers.PersonResponse;
import com.marcthomas.dao.PersonRepository;
import com.marcthomas.model.Person;

public class PersonControllerTest {
	@Mock
	private PersonRepository personRepository;

	@InjectMocks
	private PersonController personController = new PersonController();

	@Captor
	ArgumentCaptor<Person> captor;

	@Before
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testWhenValidDataProvidedSuccessfulIsTrue() {
		ResponseEntity<PersonResponse> response = personController
				.createPerson("Marc Thomas", "1976-11-03", "2");

		assertTrue(response.getBody().isSuccessful());
	}

	@Test
	public void testWhenAllFieldsValidThePersonObjectIsSaved() throws ParseException {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		String expectedName = "Marc Thomas";
		String expectedDateString = "1976-11-03";
		int expectedNumberOfChildren = 2;
		Date expectedDate = dateFormat.parse(expectedDateString);
		personController.createPerson(expectedName, expectedDateString, Integer.toString(expectedNumberOfChildren));

		verify(personRepository).save(captor.capture());
		Person actualPerson = captor.getValue();

		assertEquals("Persisted name is incorrect", expectedName, actualPerson.getName());
		assertEquals("Persisted number of children is incorrect", expectedNumberOfChildren, actualPerson.getNumberOfChildren());
		assertEquals("Persisted date of birth is incorrect", expectedDate, actualPerson.getDateOfBirth());
	}

	@Test
	public void testWhenEmptyDateIsProvidedSuccessfulIsFalse() {
		ResponseEntity<PersonResponse> response = personController
				.createPerson("Marc Thomas", "", "2");

		assertFalse(response.getBody().isSuccessful());
	}

	@Test
	public void testWhenEmptyDateIsProvidedOnlyValidationErrorIsDateOfBirth() {
		ResponseEntity<PersonResponse> response = personController
				.createPerson("Marc Thomas", "", "2");

		assertEquals(1, response.getBody().getValidationErrors().size());
		assertEquals(PersonController.DATE_OF_BIRTH_REQUEST_PARAM, response
				.getBody().getValidationErrors().get(0));
	}

	@Test
	public void testWhenEmptyNumberOfChildrenIsProvidedSuccessfulIsFalse() {
		ResponseEntity<PersonResponse> response = personController
				.createPerson("Marc Thomas", "1976-11-03", "");

		assertFalse(response.getBody().isSuccessful());
	}

	@Test
	public void testWhenEmptyNumberOfChildrenIsProvidedOnlyValidationErrorIsNumberOfChildren() {
		ResponseEntity<PersonResponse> response = personController
				.createPerson("Marc Thomas", "1976-11-03", "");

		assertEquals(1, response.getBody().getValidationErrors().size());
		assertEquals(PersonController.NUMBER_OF_CHILDREN_REQUEST_PARAM,
				response.getBody().getValidationErrors().get(0));
	}

	@Test
	public void testWhenInvalidNumberOfChildrenIsProvidedSuccessfulIsFalse() {
		ResponseEntity<PersonResponse> response = personController
				.createPerson("Marc Thomas", "1976-11-03", "-1");

		assertFalse(response.getBody().isSuccessful());
	}

	@Test
	public void testWhenInvalidNumberOfChildrenIsProvidedOnlyValidationErrorIsNumberOfChildren() {
		ResponseEntity<PersonResponse> response = personController
				.createPerson("Marc Thomas", "1976-11-03", "-1");

		assertEquals(1, response.getBody().getValidationErrors().size());
		assertEquals(PersonController.NUMBER_OF_CHILDREN_REQUEST_PARAM,
				response.getBody().getValidationErrors().get(0));
	}

	@Test
	public void testWhenAStringIsProvidedForNumberOfChildrenSuccessfulIsFalse() {
		ResponseEntity<PersonResponse> response = personController
				.createPerson("Marc Thomas", "1976-11-03", "one");

		assertFalse(response.getBody().isSuccessful());
	}

	@Test
	public void testWhenAStringIsProvidedForNumberOfChildrenOnlyValidationErrorIsNumberOfChildren() {
		ResponseEntity<PersonResponse> response = personController
				.createPerson("Marc Thomas", "1976-11-03", "one");

		assertEquals(1, response.getBody().getValidationErrors().size());
		assertEquals(PersonController.NUMBER_OF_CHILDREN_REQUEST_PARAM,
				response.getBody().getValidationErrors().get(0));
	}

	@Test
	public void testWhenEmptyNameIsProvidedSuccessfulIsFalse() {
		ResponseEntity<PersonResponse> response = personController
				.createPerson("", "1976-11-03", "2");

		assertFalse(response.getBody().isSuccessful());
	}

	@Test
	public void testWhenEmptyNameIsProvidedOnlyValidationErrorIsName() {
		ResponseEntity<PersonResponse> response = personController
				.createPerson("", "1976-11-03", "2");

		assertEquals(1, response.getBody().getValidationErrors().size());
		assertEquals(PersonController.NAME_REQUEST_PARAM, response.getBody()
				.getValidationErrors().get(0));
	}
}
