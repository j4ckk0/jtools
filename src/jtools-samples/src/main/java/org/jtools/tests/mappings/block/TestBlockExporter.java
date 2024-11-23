package org.jtools.tests.mappings.block;

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

import java.awt.print.Book;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jtools.data.provider.IDataProvider;
import org.jtools.mappings.block.BlockMapping;
import org.jtools.mappings.common.MappingUtils;
import org.jtools.mappings.editors.block.BlockMappingEditor;
import org.jtools.tests.data.models.Person;
import org.jtools.utils.dates.DateFormatManager;
// TODO: Auto-generated Javadoc

/**
 * The Class TestBlockExporter.
 */
public class TestBlockExporter {
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		try {

			BlockMapping<Person> mapping = new BlockMapping<>(Person.class);
			
			BlockMappingEditor<Person> mappingEditor = new BlockMappingEditor<>(mapping, MappingUtils.getPossibleColumns(), Person.class, Book.class);

			mappingEditor.showEditorAsDialog(null, true);

		} catch (Exception e) {
			Logger.getLogger(TestBlockExporter.class.getName()).log(Level.SEVERE, e.getMessage());
			Logger.getLogger(TestBlockExporter.class.getName()).log(Level.FINE, e.getMessage(), e);
		}
	}

	/**
	 * Gets the data provider.
	 *
	 * @return the data provider
	 */
	public static IDataProvider getDataProvider() {
		return new IDataProvider() {

			@Override
			public Class<?> getDataClass() {
				return Person.class;
			}

			@Override
			public List<?> getDataList() {
				return generateTestData();
			}
			
			@Override
			public String getProviderName() {
				return "Person provider (test block mappings)";
			}
		};
	}

	/**
	 * Generate test data.
	 *
	 * @return the list
	 */
	public static List<Person> generateTestData() {

		List<Person> persons = new ArrayList<>();

		persons.add(new Person("Riri", "Canard", 12, DateFormatManager.instance().parse("06/08/2010")));
		persons.add(new Person("Fifi", "Canard", 11, DateFormatManager.instance().parse("06/08/2011")));
		persons.add(new Person("Loulou", "Canard", 11, DateFormatManager.instance().parse("06/08/2010")));

		return persons;
	}

}
