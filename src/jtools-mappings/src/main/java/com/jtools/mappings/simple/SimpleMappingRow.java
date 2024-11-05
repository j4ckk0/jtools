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
import java.lang.reflect.Field;

/**
 * 
 * @author j4ckk0
 *
 */
public class SimpleMappingRow implements Serializable {

	private static final long serialVersionUID = 3092423564227174529L;
	
	protected String outputColumnHeader;
	protected String outputColumn;
	protected Field objectField;

	public SimpleMappingRow() {
		this.outputColumnHeader = null;
		this.outputColumn = null;
		this.objectField = null;
	}

	public SimpleMappingRow(String outputColumnHeader, String outputColumn, Field objectField) {
		this.outputColumnHeader = outputColumnHeader;
		this.outputColumn = outputColumn;
		this.objectField = objectField;
	}

	/**
	 * @return the outputColumnHeader
	 */
	public String getOutputColumnHeader() {
		return outputColumnHeader;
	}

	/**
	 * @param outputColumnHeader the outputColumnHeader to set
	 */
	public void setOutputColumnHeader(String outputColumnHeader) {
		this.outputColumnHeader = outputColumnHeader;
	}

	/**
	 * @return the outputColumn
	 */
	public String getOutputColumn() {
		return outputColumn;
	}

	/**
	 * @param outputColumn the outputColumn to set
	 */
	public void setOutputColumn(String outputColumn) {
		this.outputColumn = outputColumn;
	}

	/**
	 * @return the objectField
	 */
	public Field getObjectField() {
		return objectField;
	}

	/**
	 * @param objectField the objectField to set
	 */
	public void setObjectField(Field objectField) {
		this.objectField = objectField;
	}
}