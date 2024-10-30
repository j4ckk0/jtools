/**
 * 
 */
package com.jtools.tests.mappings.block;

import java.awt.print.Book;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jtools.mappings.block.BlockMapping;
import com.jtools.mappings.block.importers.BlockMappingExcelImporter;
import com.jtools.mappings.common.MappingUtils;
import com.jtools.mappings.editors.block.BlockMappingEditor;
import com.jtools.tests.data.models.Person;

/**
 * @author j4ckk0
 *
 */
public class TestBlockImporter {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			BlockMapping<Person> mapping = new BlockMapping<>(Person.class);

			BlockMappingEditor<Person> mappingEditor = new BlockMappingEditor<>(mapping, MappingUtils.getPossibleColumns(), Person.class, Book.class);

			List<Person> data = BlockMappingExcelImporter.instance().importData(Person.class, mapping);
			if(data != null) {
				for(Person p : data) {
					Logger.getLogger(TestBlockImporter.class.getName()).log(Level.INFO, p.toString());
				}
			}

		} catch (IOException e) {
			Logger.getLogger(TestBlockImporter.class.getName()).log(Level.SEVERE, e.getMessage(), e);
		}

	}

}
