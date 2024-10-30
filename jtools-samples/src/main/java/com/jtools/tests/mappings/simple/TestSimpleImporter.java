/**
 * 
 */
package com.jtools.tests.mappings.simple;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jtools.mappings.editors.simple.SimpleMappingEditor;
import com.jtools.mappings.simple.SimpleMapping;
import com.jtools.mappings.simple.importers.SimpleMappingExcelImporter;
import com.jtools.tests.data.models.Person;
import com.jtools.utils.concurrent.NamedCallable;

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
			personMappingEditor.showEditorAsFrame(null, new NamedCallable<Void>() {

				@Override
				public Void call() throws Exception {
					List<Person> data = SimpleMappingExcelImporter.instance().importData(Person.class, personMappingEditor.apply().getRows());
					if(data != null) {
						for(Person p : data) {
							System.out.println(p);
						}
					}

					return null;
				}

				@Override
				public String getName() {
					return "Export";
				}
			});

		} catch (Exception e) {
			Logger.getLogger(TestSimpleImporter.class.getName()).log(Level.SEVERE, e.getMessage());
			Logger.getLogger(TestSimpleImporter.class.getName()).log(Level.FINE, e.getMessage(), e);
		}

	}

}
