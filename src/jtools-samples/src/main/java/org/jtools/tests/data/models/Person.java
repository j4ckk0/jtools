package org.jtools.tests.data.models;

/*-
 * #%L
 * Java Tools - Samples
 * %%
 * Copyright (C) 2024 jtools.org
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import java.util.Date;
import java.util.List;
// TODO: Auto-generated Javadoc

/**
 * The Class Person.
 */
public class Person {

	/** The first name. */
	private String firstName;
	
	/** The last name. */
	private String lastName;
	
	/** The age. */
	private int age;
	
	/** The date of birth. */
	private Date dateOfBirth;
	
	/** The address. */
	private Address address;
	
	/** The weight. */
	private double weight;
	
	/** The height. */
	private float height;

	/** The preferred books. */
	private List<Book> preferredBooks;

	/**
	 * Instantiates a new person.
	 */
	public Person() {
		super();
	}

	/**
	 * Instantiates a new person.
	 *
	 * @param firstName the first name
	 * @param lastName the last name
	 */
	public Person(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}

	/**
	 * Instantiates a new person.
	 *
	 * @param firstName the first name
	 * @param lastName the last name
	 * @param age the age
	 * @param dateOfBirth the date of birth
	 */
	public Person(String firstName, String lastName, int age, Date dateOfBirth) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
		this.dateOfBirth = dateOfBirth;
	}

	/**
	 * Gets the first name.
	 *
	 * @return the first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Sets the first name.
	 *
	 * @param firstName the new first name
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Gets the last name.
	 *
	 * @return the last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets the last name.
	 *
	 * @param lastName the new last name
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Gets the age.
	 *
	 * @return the age
	 */
	public int getAge() {
		return age;
	}

	/**
	 * Sets the age.
	 *
	 * @param age the new age
	 */
	public void setAge(int age) {
		this.age = age;
	}

	/**
	 * Gets the date of birth.
	 *
	 * @return the date of birth
	 */
	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	/**
	 * Sets the date of birth.
	 *
	 * @param dateOfBirth the new date of birth
	 */
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	/**
	 * Gets the address.
	 *
	 * @return the address
	 */
	public Address getAddress() {
		return address;
	}

	/**
	 * Sets the address.
	 *
	 * @param address the new address
	 */
	public void setAddress(Address address) {
		this.address = address;
	}

	/**
	 * Gets the weight.
	 *
	 * @return the weight
	 */
	public double getWeight() {
		return weight;
	}

	/**
	 * Sets the weight.
	 *
	 * @param weight the new weight
	 */
	public void setWeight(double weight) {
		this.weight = weight;
	}

	/**
	 * Gets the height.
	 *
	 * @return the height
	 */
	public float getHeight() {
		return height;
	}

	/**
	 * Sets the height.
	 *
	 * @param height the new height
	 */
	public void setHeight(float height) {
		this.height = height;
	}

	/**
	 * Gets the preferred books.
	 *
	 * @return the preferred books
	 */
	public List<Book> getPreferredBooks() {
		return preferredBooks;
	}

	/**
	 * Sets the preferred books.
	 *
	 * @param preferredBooks the new preferred books
	 */
	public void setPreferredBooks(List<Book> preferredBooks) {
		this.preferredBooks = preferredBooks;
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return firstName + " - " + lastName + " - " + age + " - " + dateOfBirth + " - " + address + " - " + height + " - " + weight;
	}
}
