package org.jtools.mappings.block;

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
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jtools.mappings.common.IMapping;
// TODO: Auto-generated Javadoc

/**
 * The Class BlockMapping.
 *
 * @param <E> the element type
 */
public class BlockMapping<E extends Object> implements IMapping, Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -388490711678154083L;

	/** The id. */
	private final UUID id = UUID.randomUUID();

	/** The object class. */
	private transient Class<E> objectClass;
	
	/** The object class name. */
	private final String objectClassName;

	/** The mappings. */
	private final List<BlockMappingRow> mappings;

	/**
	 * Instantiates a new block mapping.
	 *
	 * @param objectClass the object class
	 */
	public BlockMapping(Class<E> objectClass) {
		this(objectClass, new ArrayList<>());
	}

	/**
	 * Instantiates a new block mapping.
	 *
	 * @param objectClass the object class
	 * @param mappings the mappings
	 */
	public BlockMapping(Class<E> objectClass, List<BlockMappingRow> mappings) {
		super();
		this.objectClass = objectClass;
		this.objectClassName = objectClass.getCanonicalName();
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
	@SuppressWarnings("unchecked")
	@Override
	public Class<E> getObjectClass() {
		if(objectClass == null) {
			try {
				objectClass = (Class<E>) Class.forName(objectClassName);
			} catch (ClassNotFoundException e) {
				Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage());
				Logger.getLogger(getClass().getName()).log(Level.FINE, e.getMessage(), e);
			}
		}
		return objectClass;
	}

	/**
	 * Gets the object class name.
	 *
	 * @return the object class name
	 */
	public String getObjectClassName() {
		return objectClassName;
	}

	/**
	 * Gets the rows.
	 *
	 * @return the rows
	 */
	public List<BlockMappingRow> getRows() {
		return mappings;
	}

}
