/**
 * 
 */
package com.jtools.mappings.simple.exporters;

/*-
 * #%L
 * Java Tools - Mappings
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
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jtools.mappings.common.MappingException;
import com.jtools.mappings.common.MappingUtils;
import com.jtools.mappings.simple.SimpleMappingRow;
import com.jtools.utils.objects.ObjectInfoProvider;

/**
 * @author j4ckk0
 *
 */
public class SimpleMappingStdOutputExporter extends ASimpleMappingExporter  {

	private static SimpleMappingStdOutputExporter instance;

	public static SimpleMappingStdOutputExporter instance() {
		if(instance == null) {
			instance = new SimpleMappingStdOutputExporter();
		}
		return instance;
	}

	@Override
	public <T> void exportData(List<T> data, List<SimpleMappingRow> mappings) throws IOException, MappingException {
		Logger.getLogger(SimpleMappingStdOutputExporter.class.getName()).log(Level.INFO, "-------- Headers ----------");
		writeHeaderLine(mappings);
		Logger.getLogger(SimpleMappingStdOutputExporter.class.getName()).log(Level.INFO, "");
		Logger.getLogger(SimpleMappingStdOutputExporter.class.getName()).log(Level.INFO, "-------- Data lines ----------");
		writeDataLines(data, mappings);
	}

	private void writeHeaderLine(List<SimpleMappingRow> mappings) {
		for(SimpleMappingRow mapping : mappings) {
			int index = MappingUtils.possibleColumns.indexOf(mapping.getOutputColumn());
			Logger.getLogger(getClass().getName()).log(Level.INFO, "Column " + mapping.getOutputColumn() + " (Cell " + index + ") : " + mapping.getOutputColumnHeader());
		}
	}

	private <T extends Object> void writeDataLines(List<T> data, List<SimpleMappingRow> mappings) throws MappingException {

		for(T object : data) {
			for(SimpleMappingRow mapping : mappings) {
				try {
					Field field = mapping.getObjectField();
					if(field != null) {
						if(field.getDeclaringClass() != object.getClass()) {
							throw new MappingException("Loaded mappings do not match with the object to export. Objects are " + object.getClass() + ". Field is declared in " + field.getDeclaringClass());
						}


						Method getter = ObjectInfoProvider.getObjectInfo(object.getClass()).findGetter(field);
						if(getter != null) {

							int index = MappingUtils.possibleColumns.indexOf(mapping.getOutputColumn());

							Object valueObject = getter.invoke(object);
							Logger.getLogger(getClass().getName()).log(Level.INFO, "Column " + mapping.getOutputColumn() + " (Cell " + index + ") : " + (valueObject != null ? valueObject.toString() : "null") + " [value for field " + field.getName()+ "]");
						} else {
							Logger.getLogger(getClass().getName()).log(Level.FINE, "getter not found for field " + field.getName());
						}
					}
				} catch(IllegalAccessException | InvocationTargetException e) {
					Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage());
					Logger.getLogger(getClass().getName()).log(Level.FINE, e.getMessage(), e);
				}
			}
			Logger.getLogger(getClass().getName()).log(Level.INFO, "--");
		}
	}
}