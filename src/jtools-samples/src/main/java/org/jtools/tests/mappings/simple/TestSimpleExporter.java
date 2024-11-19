package org.jtools.tests.mappings.simple;

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

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jtools.data.provider.IDataProvider;
import org.jtools.mappings.editors.simple.SimpleMappingEditor;
import org.jtools.mappings.simple.SimpleMapping;
import org.jtools.mappings.simple.exporters.SimpleMappingStdOutputExporter;
import org.jtools.tests.data.models.Person;
import org.jtools.utils.concurrent.NamedCallable;
import org.jtools.utils.dates.DateFormatManager;
public class TestSimpleExporter {
	public static void main(String[] args) {
		try {

			SimpleMapping<Person> mapping = new SimpleMapping<>(Person.class);

			SimpleMappingEditor<Person> personMappingEditor = new SimpleMappingEditor<>(mapping);
			personMappingEditor.showEditorAsFrame(null, new NamedCallable<Void>() {

				@Override
				public Void call() throws Exception {
					List<Person> persons = generateTestData();

					SimpleMappingStdOutputExporter.instance().exportData(persons, personMappingEditor.apply().getRows());
					//ExcelExporter.getInstance().exportData(persons, rows);

					return null;
				}

				@Override
				public String getName() {
					return "Export";
				}
			});
		} catch (Exception e) {
			Logger.getLogger(TestSimpleExporter.class.getName()).log(Level.SEVERE, e.getMessage());
			Logger.getLogger(TestSimpleExporter.class.getName()).log(Level.FINE, e.getMessage(), e);
		}
	}

	public static IDataProvider getDataProvider() {
		return new IDataProvider() {

			@Override
			public List<Person> getDataList() {
				return generateTestData();
			}

			@Override
			public Class<Person> getDataClass() {
				return Person.class;
			}

			@Override
			public String getProviderName() {
				return "Person provider (test simple mappings)";
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
