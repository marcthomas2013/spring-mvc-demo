package com.marcthomas.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.marcthomas.model.Person;

/**
 * This interface enables Person objects to be stored and retrieved from the in
 * memory database. Using the @RepositoryRestResource also allows any people
 * objects that are stored in the database to be retrieved via REST.
 * e.g. http://localhost:8080/people when running as a Java App or 
 * http://localhost:8080/spring-mvc-demo-0.0.1-SNAPSHOT/people when running on 
 * tomcat
 * 
 * @author marcth
 *
 */
@RepositoryRestResource(collectionResourceRel = "people", path = "people")
public interface PersonRepository extends CrudRepository<Person, Long> {
	List<Person> findByName(String name);
}
