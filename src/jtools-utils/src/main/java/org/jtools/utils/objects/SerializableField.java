package org.jtools.utils.objects;

/*-
 * #%L
 * Java Tools - Utils
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

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
// TODO: Auto-generated Javadoc

/**
 * The Class SerializableField.
 */
public class SerializableField implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -4378063640758447458L;

	/** The field cache. */
	private static transient Map<String, Field> fieldCache = new HashMap<>();

	/** The declaring class. */
	private final String declaringClass;

	/** The field name. */
	private final String fieldName;

	/**
	 * Instantiates a new serializable field.
	 *
	 * @param field the field
	 */
	public SerializableField(Field field) {
		this.declaringClass = field.getDeclaringClass().getCanonicalName();
		this.fieldName = field.getName();
	}

	/**
	 * Instantiates a new serializable field.
	 *
	 * @param declaringClass the declaring class
	 * @param fieldName the field name
	 */
	public SerializableField(String declaringClass, String fieldName) {
		this.declaringClass = declaringClass;
		this.fieldName = fieldName;
	}

	/**
	 * Gets the field.
	 *
	 * @return the field
	 */
	public Field getField() {
		try {
			String key = fieldName+"@"+declaringClass;
			Field field = fieldCache.get(key);
			if(field == null) {
				field = Class.forName(declaringClass).getDeclaredField(fieldName);
				fieldCache.put(key, field);
			}
			return field;
		} catch (NoSuchFieldException | ClassNotFoundException e) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage());
			Logger.getLogger(getClass().getName()).log(Level.FINE, e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}

}
