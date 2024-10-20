/**
 * 
 */
package com.jtools.tests.data.models;

import java.util.Date;
import java.util.List;

/**
 * @author j4ckk0
 *
 */
public class Person {

	private String firstName;
	private String lastName;
	private int age;
	private Date dateOfBirth;
	private Address address;
	private double weight;
	private float height;

	private List<Book> preferredBooks;

	public Person() {
		super();
	}

	public Person(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public Person(String firstName, String lastName, int age, Date dateOfBirth) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
		this.dateOfBirth = dateOfBirth;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public List<Book> getPreferredBooks() {
		return preferredBooks;
	}

	public void setPreferredBooks(List<Book> preferredBooks) {
		this.preferredBooks = preferredBooks;
	}

	@Override
	public String toString() {
		return firstName + " - " + lastName + " - " + age + " - " + dateOfBirth + " - " + address + " - " + height + " - " + weight;
	}
}
