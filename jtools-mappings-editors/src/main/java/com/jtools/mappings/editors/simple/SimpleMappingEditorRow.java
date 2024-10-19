package com.jtools.mappings.editors.simple;

import java.lang.reflect.Field;

import com.jtools.mappings.simple.SimpleMappingRow;
import com.jtools.utils.gui.GuiUtils;

/**
 * 
 * @author j4ckk0
 *
 */
public class SimpleMappingEditorRow {

	protected final SimpleMappingRow simpleMappingRow;
	

	public SimpleMappingEditorRow(SimpleMappingRow simpleMappingRow) {
		this.simpleMappingRow = simpleMappingRow;
	}
	
	public SimpleMappingRow getSimpleMappingRow() {
		return simpleMappingRow;
	}

	public SimpleMappingRowType getRowType() {
		return SimpleMappingRowType.MAPPING_ROW;
	}

	public String getMappingArrow() {
		return GuiUtils.ARROW_ICON_TXT;
	}

	public String getOutputColumnHeader() {
		if(simpleMappingRow != null) {
			return simpleMappingRow.getOutputColumnHeader();
		}
		return null;
	}

	public void setOutputColumnHeader(String value) {
		if(simpleMappingRow != null) {
			simpleMappingRow.setOutputColumnHeader(value);
		}
	}

	public String getOutputColumn() {
		if(simpleMappingRow != null) {
			return simpleMappingRow.getOutputColumn();
		}
		return null;
	}

	public void setOutputColumn(String value) {
		if(simpleMappingRow != null) {
			simpleMappingRow.setOutputColumn(value);
		}
	}

	public Field getObjectField() {
		if(simpleMappingRow != null) {
			return simpleMappingRow.getObjectField();
		}
		return null;
	}

	public void setObjectField(Field value) {
		if(simpleMappingRow != null) {
			simpleMappingRow.setObjectField(value);
		}
	}
	
	/**
	 * 
	 * @author j4ckk0
	 *
	 */
	public static class SimpleMappingEditorNewRow extends SimpleMappingEditorRow {

		public SimpleMappingEditorNewRow() {
			super(null);
		}

		@Override
		public SimpleMappingRowType getRowType() {
			return SimpleMappingRowType.NEW_ROW;
		}

		@Override
		public String getMappingArrow() {
			return null;
		}
	}
	
	/**
	 * 
	 * @author j4ckk0
	 *
	 */
	public enum SimpleMappingRowType {
		MAPPING_ROW(GuiUtils.MINUS_ICON_TXT), NEW_ROW(GuiUtils.PLUS_ICON_TXT);

		private final String label;

		private SimpleMappingRowType(String label) {
			this.label = label;
		}

		@Override
		public String toString() {
			return label;
		}
	}
}