/**
 * 
 */
package com.jtools.tests.mappings.simple;

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
			SimpleMapping<Person> simpleMapping = new SimpleMapping<>(Person.class);

			SimpleMappingEditor<Person> personMappingEditor = new SimpleMappingEditor<>(simpleMapping);

			List<Person> data = SimpleMappingExcelImporter.getInstance().importData(Person.class, personMappingEditor.getRows());
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
