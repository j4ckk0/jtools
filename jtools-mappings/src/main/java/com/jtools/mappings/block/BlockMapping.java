/**
 * 
 */
package com.jtools.mappings.block;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import com.jtools.mappings.common.IMapping;

/**
 * @author j4ckk0
 *
 */
public class BlockMapping<E extends Object> implements IMapping, Serializable {

	private static final long serialVersionUID = -388490711678154083L;

	private final UUID id = UUID.randomUUID();

	private final Class<E> objectClass;

	private final List<BlockMappingRow> mappings;

	public BlockMapping(Class<E> objectClass) {
		this(objectClass, Collections.emptyList());
	}

	public BlockMapping(Class<E> objectClass, List<BlockMappingRow> mappings) {
		super();
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

	public List<BlockMappingRow> getMappingRows() {
		return mappings;
	}

}
