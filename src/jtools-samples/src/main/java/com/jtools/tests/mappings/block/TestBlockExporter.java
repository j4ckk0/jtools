/**
 * 
 */
package com.jtools.tests.mappings.block;

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

import java.awt.print.Book;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jtools.data.provider.IDataProvider;
import com.jtools.mappings.block.BlockMapping;
import com.jtools.mappings.common.MappingUtils;
import com.jtools.mappings.editors.block.BlockMappingEditor;
import com.jtools.tests.data.models.Person;
import com.jtools.utils.dates.DateFormatManager;

/**
 * @author j4ckk0
 *
 */
public class TestBlockExporter {

	/**
	 * @param args
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

	public static List<Person> generateTestData() {

		List<Person> persons = new ArrayList<>();

		persons.add(new Person("Riri", "Canard", 12, DateFormatManager.instance().parse("06/08/2010")));
		persons.add(new Person("Fifi", "Canard", 11, DateFormatManager.instance().parse("06/08/2011")));
		persons.add(new Person("Loulou", "Canard", 11, DateFormatManager.instance().parse("06/08/2010")));

		return persons;
	}

}
