package com.jtools.mappings.editors.block;

/*-
 * #%L
 * Java Tools - Mappings Editors
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

import com.jtools.mappings.block.BlockMapping;
import com.jtools.mappings.editors.block.BlockMappingEditorRow.BlockMappingRowType;

public enum BlockMappingEditorColumn {

	ADD_REMOVE_BUTTON_COL(0, BlockMappingRowType.class, ""),
	FROM_COLUMN_COL(1, String.class, "From column"),
	TO_COLUMN_COL(2, String.class, "To column"),
	MAPPING_ARROW_COL(3, String.class, ""),
	OBJECT_FIELD_COL(4, String.class, "Object field"),
	HEADER_VALUE_COL(5, String.class, "Header"),
	SUB_BLOCK_MAPPING_COL(6, BlockMapping.class, "Block");

	private final int columnIndex;
	private final Class<?> columnClass;
	private final String label;

	private BlockMappingEditorColumn(int columnIndex, Class<?> columnClass, String label) {
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
	
	public static BlockMappingEditorColumn forIndex(int index) {
		for(BlockMappingEditorColumn value : values()) {
			if(value.getColumnIndex() == index) {
				return value;
			}
		}
		throw new IllegalArgumentException("Invalid BlockMappingEditorColumn index : " + index);
	}
}
