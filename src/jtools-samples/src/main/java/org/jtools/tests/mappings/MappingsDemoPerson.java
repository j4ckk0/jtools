package org.jtools.tests.mappings;

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

import org.jtools.tests.data.models.Address;
import org.jtools.tests.data.models.Book;
import org.jtools.tests.data.models.Person;
public class MappingsDemoPerson extends AMappingsDemo {

	private static final long serialVersionUID = -1509793458200691610L;

	public MappingsDemoPerson() {
		super(new Class[] { Person.class, Book.class, Address.class });
	}
	public static void main(String[] args) {
		AMappingsDemo frame = new MappingsDemoPerson();
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

}
