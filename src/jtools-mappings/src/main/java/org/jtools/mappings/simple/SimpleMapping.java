package org.jtools.mappings.simple;

/*-
 * #%L
 * Java Tools - Mappings
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
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.jtools.mappings.common.IMapping;
// TODO: Auto-generated Javadoc

/**
 * The Class SimpleMapping.
 *
 * @param <E> the element type
 */
public class SimpleMapping<E extends Object> implements IMapping, Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -388490711678154083L;

	/** The id. */
	private final UUID id = UUID.randomUUID();

	/** The object class. */
	private final Class<E> objectClass;

	/** The mappings. */
	private final List<SimpleMappingRow> mappings;
	
	/**
	 * Instantiates a new simple mapping.
	 *
	 * @param objectClass the object class
	 */
	public SimpleMapping(Class<E> objectClass) {
		this(objectClass, new ArrayList<>());
	}

	/**
	 * Instantiates a new simple mapping.
	 *
	 * @param objectClass the object class
	 * @param mappings the mappings
	 */
	public SimpleMapping(Class<E> objectClass, List<SimpleMappingRow> mappings) {
		this.objectClass = objectClass;
		this.mappings = mappings;
	}
	
	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	@Override
	public UUID getId() {
		return id;
	}

	/**
	 * Gets the object class.
	 *
	 * @return the object class
	 */
	@Override
	public Class<E> getObjectClass() {
		return objectClass;
	}

	/**
	 * Gets the rows.
	 *
	 * @return the rows
	 */
	public List<SimpleMappingRow> getRows() {
		return mappings;
	}

}
