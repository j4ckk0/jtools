/**
 * 
 */
package com.jtools.tests.mappings.simple;

/*-
 * #%L
 * Java Tools - Samples
 * %%
 * Copyright (C) 2024 j4ckk0
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

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jtools.mappings.editors.simple.SimpleMappingEditor;
import com.jtools.mappings.simple.SimpleMapping;
import com.jtools.mappings.simple.importers.SimpleMappingExcelImporter;
import com.jtools.tests.data.models.Person;

/**
 * @author j4ckk0
 *
 */
public class TestSimpleImporter {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			SimpleMapping<Person> mapping = new SimpleMapping<>(Person.class);

			SimpleMappingEditor<Person> personMappingEditor = new SimpleMappingEditor<>(mapping);

			List<Person> data = SimpleMappingExcelImporter.instance().importData(Person.class, personMappingEditor.apply().getRows());
			if(data != null) {
				for(Person p : data) {
					System.out.println(p);
				}
			}

		} catch (IOException e) {
			Logger.getLogger(TestSimpleImporter.class.getName()).log(Level.SEVERE, e.getMessage(), e);
		}

	}

}
