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

import java.beans.PropertyChangeSupport;
import java.lang.reflect.Field;

import org.jtools.mappings.block.BlockMapping;
import org.jtools.mappings.block.BlockMappingRow;
import org.jtools.utils.gui.GuiUtils;
import org.jtools.utils.objects.SerializableField;
// TODO: Auto-generated Javadoc

/**
 * The Class BlockMappingEditorRow.
 */
public class BlockMappingEditorRow {

	/** The Constant MAPPING_TYPE_PROPERTY. */
	public static final String MAPPING_TYPE_PROPERTY = "MAPPING_TYPE_PROPERTY";

	/** The property change support. */
	private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
	
	/** The block mapping row. */
	protected BlockMappingRow blockMappingRow;

	/**
	 * Instantiates a new block mapping editor row.
	 */
	public BlockMappingEditorRow() {
		this.blockMappingRow = new BlockMappingRow();
	}
	
	/**
	 * Instantiates a new block mapping editor row.
	 *
	 * @param blockMappingRow the block mapping row
	 */
	public BlockMappingEditorRow(BlockMappingRow blockMappingRow) {
		this.blockMappingRow = blockMappingRow;
	}

	/**
	 * Gets the row type.
	 *
	 * @return the row type
	 */
	public BlockMappingRowType getRowType() {
		return BlockMappingRowType.MAPPING_ROW;
	}
	
	/**
	 * Gets the block mapping row.
	 *
	 * @return the block mapping row
	 */
	public BlockMappingRow getBlockMappingRow() {
		return blockMappingRow;
	}

	/**
	 * Gets the from column.
	 *
	 * @return the from column
	 */
	public String getFromColumn() {
		if(blockMappingRow != null) {
			return blockMappingRow.getFromColumn();
		}
		return null;
	}
	
	/**
	 * Sets the from column.
	 *
	 * @param fromColumn the new from column
	 */
	public void setFromColumn(String fromColumn) {
		if(blockMappingRow != null) {
			blockMappingRow.setFromColumn(fromColumn);
		}
	}

	/**
	 * Gets the to column.
	 *
	 * @return the to column
	 */
	public String getToColumn() {
		if(blockMappingRow != null) {
			return blockMappingRow.getToColumn();
		}
		return null;
	}

	/**
	 * Sets the to column.
	 *
	 * @param toColumn the new to column
	 */
	public void setToColumn(String toColumn) {
		if(blockMappingRow != null) {
			blockMappingRow.setToColumn(toColumn);
		}
	}

	/**
	 * Gets the header value.
	 *
	 * @return the header value
	 */
	public String getHeaderValue() {
		if(blockMappingRow != null) {
			return blockMappingRow.getHeaderValue();
		}
		return null;
	}

	/**
	 * Sets the header value.
	 *
	 * @param headerValue the new header value
	 */
	public void setHeaderValue(String headerValue) {
		if(blockMappingRow != null) {
			blockMappingRow.setHeaderValue(headerValue);
		}
	}

	/**
	 * Gets the object field.
	 *
	 * @return the object field
	 */
	public Field getObjectField() {
		if(blockMappingRow != null) {
			return blockMappingRow.getObjectField();
		}
		return null;
	}

	/**
	 * Sets the object field.
	 *
	 * @param objectField the new object field
	 */
	public void setObjectField(Field objectField) {
		if(blockMappingRow != null) {
			blockMappingRow.setObjectField(objectField != null ? new SerializableField(objectField) : null);
		}
	}

	/**
	 * Gets the sub block mapping.
	 *
	 * @return the sub block mapping
	 */
	public BlockMapping<?> getSubBlockMapping() {
		if(blockMappingRow != null) {
			return blockMappingRow.getSubBlockMapping();
		}
		return null;
	}

	/**
	 * Sets the sub block mapping.
	 *
	 * @param subBlockMapping the new sub block mapping
	 */
	public void setSubBlockMapping(BlockMapping<?> subBlockMapping) {
		if(blockMappingRow != null) {
			blockMappingRow.setSubBlockMapping(subBlockMapping);
		}
	}

	/**
	 * Gets the mapping arrow.
	 *
	 * @return the mapping arrow
	 */
	public String getMappingArrow() {
		return GuiUtils.ARROW_ICON_TXT;
	}

	/**
	 * Gets the property change support.
	 *
	 * @return the property change support
	 */
	public PropertyChangeSupport getPropertyChangeSupport() {
		return propertyChangeSupport;
	}
	
	/**
	 * The Class BlockMappingEditorNewRow.
	 */
	public static class BlockMappingEditorNewRow extends BlockMappingEditorRow {

		/**
		 * Instantiates a new block mapping editor new row.
		 */
		public BlockMappingEditorNewRow() {
			super(null);
		}

		/**
		 * Gets the row type.
		 *
		 * @return the row type
		 */
		@Override
		public BlockMappingRowType getRowType() {
			return BlockMappingRowType.NEW_ROW;
		}

	}
	
	/**
	 * The Enum BlockMappingRowType.
	 */
	public enum BlockMappingRowType {
		
		/** The mapping row. */
		MAPPING_ROW(GuiUtils.MINUS_ICON_TXT), 
 /** The new row. */
 NEW_ROW(GuiUtils.PLUS_ICON_TXT);

		/** The label. */
		private final String label;

		/**
		 * Instantiates a new block mapping row type.
		 *
		 * @param label the label
		 */
		private BlockMappingRowType(String label) {
			this.label = label;
		}

		/**
		 * To string.
		 *
		 * @return the string
		 */
		@Override
		public String toString() {
			return label;
		}
	}
}
