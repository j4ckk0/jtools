/**
 * 
 */
package com.jtools.mappings.block;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jtools.mappings.common.IMapping;

/**
 * @author j4ckk0
 *
 */
public class BlockMapping<E extends Object> implements IMapping, Serializable {

	private static final long serialVersionUID = -388490711678154083L;

	private final UUID id = UUID.randomUUID();

	private transient Class<E> objectClass;
	private final String objectClassName;

	private final List<BlockMappingRow> mappings;

	public BlockMapping(Class<E> objectClass) {
		this(objectClass, new ArrayList<>());
	}

	public BlockMapping(Class<E> objectClass, List<BlockMappingRow> mappings) {
		super();
		this.objectClass = objectClass;
		this.objectClassName = objectClass.getCanonicalName();
		this.mappings = mappings;
	}

	@Override
	public UUID getId() {
		return id;
	}

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

	public String getObjectClassName() {
		return objectClassName;
	}

	public List<BlockMappingRow> getRows() {
		return mappings;
	}

}
