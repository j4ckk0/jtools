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
import java.lang.reflect.Field;
// TODO: Auto-generated Javadoc

/**
 * The Class SimpleMappingRow.
 */
public class SimpleMappingRow implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 3092423564227174529L;
	
	/** The output column header. */
	protected String outputColumnHeader;
	
	/** The output column. */
	protected String outputColumn;
	
	/** The object field. */
	protected Field objectField;

	/**
	 * Instantiates a new simple mapping row.
	 */
	public SimpleMappingRow() {
		this.outputColumnHeader = null;
		this.outputColumn = null;
		this.objectField = null;
	}

	/**
	 * Instantiates a new simple mapping row.
	 *
	 * @param outputColumnHeader the output column header
	 * @param outputColumn the output column
	 * @param objectField the object field
	 */
	public SimpleMappingRow(String outputColumnHeader, String outputColumn, Field objectField) {
		this.outputColumnHeader = outputColumnHeader;
		this.outputColumn = outputColumn;
		this.objectField = objectField;
	}
	
	/**
	 * Gets the output column header.
	 *
	 * @return the output column header
	 */
	public String getOutputColumnHeader() {
		return outputColumnHeader;
	}
	
	/**
	 * Sets the output column header.
	 *
	 * @param outputColumnHeader the new output column header
	 */
	public void setOutputColumnHeader(String outputColumnHeader) {
		this.outputColumnHeader = outputColumnHeader;
	}
	
	/**
	 * Gets the output column.
	 *
	 * @return the output column
	 */
	public String getOutputColumn() {
		return outputColumn;
	}
	
	/**
	 * Sets the output column.
	 *
	 * @param outputColumn the new output column
	 */
	public void setOutputColumn(String outputColumn) {
		this.outputColumn = outputColumn;
	}

	/**
	 * Gets the object field.
	 *
	 * @return the objectField
	 */
	public Field getObjectField() {
		return objectField;
	}

	/**
	 * Sets the object field.
	 *
	 * @param objectField the objectField to set
	 */
	public void setObjectField(Field objectField) {
		this.objectField = objectField;
	}
}
