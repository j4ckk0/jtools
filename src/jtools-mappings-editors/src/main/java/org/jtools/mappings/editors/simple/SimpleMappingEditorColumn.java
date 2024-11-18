package org.jtools.mappings.editors.simple;

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
