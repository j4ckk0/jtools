/**
 * 
 */
package com.jtools.tests.mappings;

import com.jtools.tests.data.models.Address;
import com.jtools.tests.data.models.Book;
import com.jtools.tests.data.models.Person;

/**
 * @author j4ckk0
 *
 */
public class MappingsDemoPerson extends AMappingsDemo {

	private static final long serialVersionUID = -1509793458200691610L;

	public MappingsDemoPerson() {
		super(new Class[] { Person.class, Book.class, Address.class });
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		AMappingsDemo frame = new MappingsDemoPerson();
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

}
