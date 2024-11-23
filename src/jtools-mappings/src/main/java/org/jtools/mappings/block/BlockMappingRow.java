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
import java.lang.reflect.Field;

import org.jtools.utils.objects.SerializableField;
// TODO: Auto-generated Javadoc

/**
 * The Class BlockMappingRow.
 */
public class BlockMappingRow implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -9078066581764951630L;

	/** The from column. */
	protected String fromColumn;
	
	/** The to column. */
	protected String toColumn;

	/** The header value. */
	protected String headerValue;

	/** The object field. */
	protected SerializableField objectField;

	/** The sub block mapping. */
	protected BlockMapping<?> subBlockMapping;

	/**
	 * Instantiates a new block mapping row.
	 */
	public BlockMappingRow() {
		this(null, null, null,null);
	}
	
	/**
	 * Instantiates a new block mapping row.
	 *
	 * @param fromColumn the from column
	 * @param toColumn the to column
	 * @param objectField the object field
	 * @param headerValue the header value
	 */
	public BlockMappingRow(String fromColumn, String toColumn, Field objectField, String headerValue) {
		this(fromColumn, toColumn, objectField, headerValue, null);
	}

	/**
	 * Instantiates a new block mapping row.
	 *
	 * @param fromColumn the from column
	 * @param toColumn the to column
	 * @param objectField the object field
	 * @param headerValue the header value
	 * @param subBlockMapping the sub block mapping
	 */
	public BlockMappingRow(String fromColumn, String toColumn, Field objectField, String headerValue, BlockMapping<?> subBlockMapping) {
		this.fromColumn = fromColumn;
		this.toColumn = toColumn;
		this.headerValue = headerValue;
		this.objectField = objectField != null ? new SerializableField(objectField) : null;
		this.subBlockMapping = subBlockMapping;
	}

	/**
	 * Gets the from column.
	 *
	 * @return the from column
	 */
	public String getFromColumn() {
		return fromColumn;
	}

	/**
	 * Gets the to column.
	 *
	 * @return the to column
	 */
	public String getToColumn() {
		return toColumn;
	}

	/**
	 * Gets the header value.
	 *
	 * @return the header value
	 */
	public String getHeaderValue() {
		return headerValue;
	}
	
	/**
	 * Sets the from column.
	 *
	 * @param fromColumn the new from column
	 */
	public void setFromColumn(String fromColumn) {
		this.fromColumn = fromColumn;
	}
	
	/**
	 * Sets the to column.
	 *
	 * @param toColumn the new to column
	 */
	public void setToColumn(String toColumn) {
		this.toColumn = toColumn;
	}
	
	/**
	 * Sets the header value.
	 *
	 * @param headerValue the new header value
	 */
	public void setHeaderValue(String headerValue) {
		this.headerValue = headerValue;
	}
	
	/**
	 * Sets the object field.
	 *
	 * @param objectField the new object field
	 */
	public void setObjectField(SerializableField objectField) {
		this.objectField = objectField;
	}

	/**
	 * Sets the sub block mapping.
	 *
	 * @param subBlockMapping the subBlockMapping to set
	 */
	public void setSubBlockMapping(BlockMapping<?> subBlockMapping) {
		this.subBlockMapping = subBlockMapping;
	}

	/**
	 * Gets the serializable object field.
	 *
	 * @return the serializable object field
	 */
	public SerializableField getSerializableObjectField() {
		return objectField;
	}

	/**
	 * Gets the object field.
	 *
	 * @return the object field
	 */
	public Field getObjectField() {
		return objectField != null ? objectField.getField() : null;
	}

	/**
	 * Gets the sub block mapping.
	 *
	 * @return the sub block mapping
	 */
	public BlockMapping<?> getSubBlockMapping() {
		return subBlockMapping;
	}

}
