/**
 * 
 */
package com.jtools.mappings.simple;

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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.jtools.mappings.common.IMapping;

/**
 * @author j4ckk0
 *
 */
public class SimpleMapping<E extends Object> implements IMapping, Serializable {

	private static final long serialVersionUID = -388490711678154083L;

	private final UUID id = UUID.randomUUID();

	private final Class<E> objectClass;

	private final List<SimpleMappingRow> mappings;
	
	public SimpleMapping(Class<E> objectClass) {
		this(objectClass, new ArrayList<>());
	}

	public SimpleMapping(Class<E> objectClass, List<SimpleMappingRow> mappings) {
		this.objectClass = objectClass;
		this.mappings = mappings;
	}
	
	@Override
	public UUID getId() {
		return id;
	}

	@Override
	public Class<E> getObjectClass() {
		return objectClass;
	}

	public List<SimpleMappingRow> getRows() {
		return mappings;
	}

}
