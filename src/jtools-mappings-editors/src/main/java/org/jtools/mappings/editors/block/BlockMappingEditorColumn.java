package org.jtools.mappings.editors.block;

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

import org.jtools.mappings.block.BlockMapping;
import org.jtools.mappings.editors.block.BlockMappingEditorRow.BlockMappingRowType;

// TODO: Auto-generated Javadoc
/**
 * The Enum BlockMappingEditorColumn.
 */
public enum BlockMappingEditorColumn {

	/** The add remove button col. */
	ADD_REMOVE_BUTTON_COL(0, BlockMappingRowType.class, ""),
	
	/** The from column col. */
	FROM_COLUMN_COL(1, String.class, "From column"),
	
	/** The to column col. */
	TO_COLUMN_COL(2, String.class, "To column"),
	
	/** The mapping arrow col. */
	MAPPING_ARROW_COL(3, String.class, ""),
	
	/** The object field col. */
	OBJECT_FIELD_COL(4, String.class, "Object field"),
	
	/** The header value col. */
	HEADER_VALUE_COL(5, String.class, "Header"),
	
	/** The sub block mapping col. */
	SUB_BLOCK_MAPPING_COL(6, BlockMapping.class, "Block");

	/** The column index. */
	private final int columnIndex;
	
	/** The column class. */
	private final Class<?> columnClass;
	
	/** The label. */
	private final String label;

	/**
	 * Instantiates a new block mapping editor column.
	 *
	 * @param columnIndex the column index
	 * @param columnClass the column class
	 * @param label the label
	 */
	private BlockMappingEditorColumn(int columnIndex, Class<?> columnClass, String label) {
		this.columnIndex = columnIndex;
		this.columnClass = columnClass;
		this.label = label;
	}

	/**
	 * Gets the column index.
	 *
	 * @return the column index
	 */
	public int getColumnIndex() {
		return columnIndex;
	}

	/**
	 * Gets the column class.
	 *
	 * @return the column class
	 */
	public Class<?> getColumnClass() {
		return columnClass;
	}
	
	/**
	 * Gets the label.
	 *
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}
	
	/**
	 * For index.
	 *
	 * @param index the index
	 * @return the block mapping editor column
	 */
	public static BlockMappingEditorColumn forIndex(int index) {
		for(BlockMappingEditorColumn value : values()) {
			if(value.getColumnIndex() == index) {
				return value;
			}
		}
		throw new IllegalArgumentException("Invalid BlockMappingEditorColumn index : " + index);
	}
}
