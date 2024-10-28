package com.jtools.mappings.editors.block;

import java.beans.PropertyChangeSupport;
import java.lang.reflect.Field;

import com.jtools.mappings.block.BlockMapping;
import com.jtools.mappings.block.BlockMappingRow;
import com.jtools.utils.gui.GuiUtils;
import com.jtools.utils.objects.SerializableField;

/**
 * 
 * @author j4ckk0
 *
 */
public class BlockMappingEditorRow {

	public static final String MAPPING_TYPE_PROPERTY = "MAPPING_TYPE_PROPERTY";

	private transient PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
	
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

	/**
	 * 
	 * @author j4ckk0
	 *
	 */
	public static class BlockMappingEditorNewRow extends BlockMappingEditorRow {

		public BlockMappingEditorNewRow() {
			super(null);
		}

		@Override
		public BlockMappingRowType getRowType() {
			return BlockMappingRowType.NEW_ROW;
		}

	}

	/**
	 * 
	 * @author j4ckk0
	 *
	 */
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