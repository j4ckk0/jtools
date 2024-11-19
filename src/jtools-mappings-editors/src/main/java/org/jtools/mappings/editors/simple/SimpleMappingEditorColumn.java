package org.jtools.mappings.editors.simple;

/*-
 * #%L
 * Java Tools - Mappings Editors
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

import org.jtools.mappings.editors.simple.SimpleMappingEditorRow.SimpleMappingRowType;

public enum SimpleMappingEditorColumn {

	ADD_REMOVE_BUTTON_COL(0, SimpleMappingRowType.class, ""),
	OUTPUT_COLUMN_COL(1, String.class, "Column"),
	MAPPING_ARROW_COL(2, String.class, ""),
	OBJECT_FIELD_COL(3, String.class, "Object field"),
	OUTPUT_COLUMN_HEARDER_COL(4, String.class, "Header");

	private final int columnIndex;
	private final Class<?> columnClass;
	private final String label;

	private SimpleMappingEditorColumn(int columnIndex, Class<?> columnClass, String label) {
		this.columnIndex = columnIndex;
		this.columnClass = columnClass;
		this.label = label;
	}

	public int getColumnIndex() {
		return columnIndex;
	}

	public Class<?> getColumnClass() {
		return columnClass;
	}
	
	public String getLabel() {
		return label;
	}
	
	public static SimpleMappingEditorColumn forIndex(int index) {
		for(SimpleMappingEditorColumn value : values()) {
			if(value.getColumnIndex() == index) {
				return value;
			}
		}
		throw new IllegalArgumentException("Invalid SimpleMappingEditorColumn index : " + index);
	}
}
