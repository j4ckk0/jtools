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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jtools.mappings.block.BlockMapping;
import com.jtools.mappings.block.importers.BlockMappingExcelImporter;
import com.jtools.mappings.common.MappingUtils;
import com.jtools.mappings.editors.block.BlockMappingEditor;
import com.jtools.tests.data.models.Person;
import com.jtools.utils.concurrent.NamedCallable;

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
			mappingEditor.showEditorAsFrame(null, new NamedCallable<Void>() {

				@Override
				public Void call() throws Exception {
					List<Person> data = BlockMappingExcelImporter.instance().importData(Person.class, mapping);
					if(data != null) {
						for(Person p : data) {
							Logger.getLogger(TestBlockImporter.class.getName()).log(Level.INFO, p.toString());
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
			Logger.getLogger(TestBlockImporter.class.getName()).log(Level.SEVERE, e.getMessage());
			Logger.getLogger(TestBlockImporter.class.getName()).log(Level.FINE, e.getMessage(), e);
		}

	}

}
