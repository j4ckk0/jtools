/**
 * 
 */
package com.jtools.tests.data.models;

/**
 * @author j4ckk0
 *
 */
public class Address {

	private int number;

	private String street;

	private int zipCode;

	private String city;

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public int getZipCode() {
		return zipCode;
	}

	public void setZipCode(int zipCode) {
		this.zipCode = zipCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	@Override
	public String toString() {
		return number + " - " + street + " - " + zipCode + " - " + city;
	}

}
