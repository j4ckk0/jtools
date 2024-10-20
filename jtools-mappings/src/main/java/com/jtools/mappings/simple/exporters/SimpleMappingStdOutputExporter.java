/**
 * 
 */
package com.jtools.mappings.simple.exporters;

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

	private static SimpleMappingStdOutputExporter INSTANCE;

	public static SimpleMappingStdOutputExporter getInstance() {
		if(INSTANCE == null) {
			INSTANCE = new SimpleMappingStdOutputExporter();
		}
		return INSTANCE;
	}

	@Override
	public <T> void exportData(List<T> data, List<SimpleMappingRow> mappings) throws IOException, MappingException {
		System.out.println( "-------- Headers ----------");
		writeHeaderLine(mappings);
		System.out.println( "");
		System.out.println( "-------- Data lines ----------");
		writeDataLines(data, mappings);
	}

	private void writeHeaderLine(List<SimpleMappingRow> mappings) {
		for(SimpleMappingRow mapping : mappings) {
			int index = MappingUtils.possibleColumns.indexOf(mapping.getOutputColumn());
			System.out.println("Column " + mapping.getOutputColumn() + " (Cell " + index + ") : " + mapping.getOutputColumnHeader());
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
							System.out.println("Column " + mapping.getOutputColumn() + " (Cell " + index + ") : " + (valueObject != null ? valueObject.toString() : "null") + " [value for field " + field.getName()+ "]");
						} else {
							Logger.getLogger(getClass().getName()).log(Level.FINE, "getter not found for field " + field.getName());
						}
					}
				} catch(IllegalAccessException | InvocationTargetException e) {
					Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
				}
			}
			System.out.println("--");
		}
	}
}
