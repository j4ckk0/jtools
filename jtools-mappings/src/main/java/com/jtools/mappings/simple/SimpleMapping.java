/**
 * 
 */
package com.jtools.mappings.simple;

import java.io.Serializable;
import java.util.Collections;
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

	private final transient List<SimpleMappingRow> mappings;
	
	public SimpleMapping(Class<E> objectClass) {
		this(objectClass, Collections.emptyList());
	}

	public SimpleMapping(Class<E> objectClass, List<SimpleMappingRow> mappings) {
		super();
		this.objectClass = objectClass;
		this.mappings = mappings;
	}
	
	@Override
	public UUID getId() {
		return id;
	}

	public Class<E> getObjectClass() {
		return objectClass;
	}

	public List<SimpleMappingRow> getMappingRows() {
		return mappings;
	}

}
