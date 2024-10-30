/**
 * 
 */
package com.jtools.tests.mappings.simple;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jtools.data.provider.IDataProvider;
import com.jtools.mappings.editors.simple.SimpleMappingEditor;
import com.jtools.mappings.simple.SimpleMapping;
import com.jtools.mappings.simple.exporters.SimpleMappingStdOutputExporter;
import com.jtools.tests.data.models.Person;
import com.jtools.utils.concurrent.NamedCallable;
import com.jtools.utils.dates.DateFormatManager;

/**
 * @author j4ckk0
 *
 */
public class TestSimpleExporter {

	/**
	 * @param args
	 */
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
		} catch (IOException e) {
			Logger.getLogger(TestSimpleExporter.class.getName()).log(Level.SEVERE, e.getMessage(), e);
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
