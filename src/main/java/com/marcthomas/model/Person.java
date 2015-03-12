package com.marcthomas.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Person {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String name;
	private Date dateOfBirth;
	private int numberOfChildren;

	protected Person() {
	}

	public Person(String name, Date dateOfBirth, int numberOfChildren) {
		this.name = name;
		this.dateOfBirth = dateOfBirth;
		this.numberOfChildren = numberOfChildren;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public int getNumberOfChildren() {
		return numberOfChildren;
	}

	public void setNumberOfChildren(int numberOfChildren) {
		this.numberOfChildren = numberOfChildren;
	}

	@Override
	public String toString() {
		return String
				.format("Person[id=%d, Name='%s', Date of Birth='%s', Number of children='%s']",
						id, name, dateOfBirth, numberOfChildren);
	}
}
