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
public class BlockMappingEditorRow {

	public static final String MAPPING_TYPE_PROPERTY = "MAPPING_TYPE_PROPERTY";

	private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
	
	protected BlockMappingRow blockMappingRow;

	public BlockMappingEditorRow() {
		this.blockMappingRow = new BlockMappingRow();
	}
	
	public BlockMappingEditorRow(BlockMappingRow blockMappingRow) {
		this.blockMappingRow = blockMappingRow;
	}

	public BlockMappingRowType getRowType() {
		return BlockMappingRowType.MAPPING_ROW;
	}
	
	public BlockMappingRow getBlockMappingRow() {
		return blockMappingRow;
	}

	public String getFromColumn() {
		if(blockMappingRow != null) {
			return blockMappingRow.getFromColumn();
		}
		return null;
	}
	
	public void setFromColumn(String fromColumn) {
		if(blockMappingRow != null) {
			blockMappingRow.setFromColumn(fromColumn);
		}
	}

	public String getToColumn() {
		if(blockMappingRow != null) {
			return blockMappingRow.getToColumn();
		}
		return null;
	}

	public void setToColumn(String toColumn) {
		if(blockMappingRow != null) {
			blockMappingRow.setToColumn(toColumn);
		}
	}

	public String getHeaderValue() {
		if(blockMappingRow != null) {
			return blockMappingRow.getHeaderValue();
		}
		return null;
	}

	public void setHeaderValue(String headerValue) {
		if(blockMappingRow != null) {
			blockMappingRow.setHeaderValue(headerValue);
		}
	}

	public Field getObjectField() {
		if(blockMappingRow != null) {
			return blockMappingRow.getObjectField();
		}
		return null;
	}

	public void setObjectField(Field objectField) {
		if(blockMappingRow != null) {
			blockMappingRow.setObjectField(objectField != null ? new SerializableField(objectField) : null);
		}
	}

	public BlockMapping<?> getSubBlockMapping() {
		if(blockMappingRow != null) {
			return blockMappingRow.getSubBlockMapping();
		}
		return null;
	}

	public void setSubBlockMapping(BlockMapping<?> subBlockMapping) {
		if(blockMappingRow != null) {
			blockMappingRow.setSubBlockMapping(subBlockMapping);
		}
	}

	public String getMappingArrow() {
		return GuiUtils.ARROW_ICON_TXT;
	}

	public PropertyChangeSupport getPropertyChangeSupport() {
		return propertyChangeSupport;
	}
	public static class BlockMappingEditorNewRow extends BlockMappingEditorRow {

		public BlockMappingEditorNewRow() {
			super(null);
		}

		@Override
		public BlockMappingRowType getRowType() {
			return BlockMappingRowType.NEW_ROW;
		}

	}
	public enum BlockMappingRowType {
		MAPPING_ROW(GuiUtils.MINUS_ICON_TXT), NEW_ROW(GuiUtils.PLUS_ICON_TXT);

		private final String label;

		private BlockMappingRowType(String label) {
			this.label = label;
		}

		@Override
		public String toString() {
			return label;
		}
	}
}
