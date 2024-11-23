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

import java.awt.Color;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.table.AbstractTableModel;

import org.jtools.gui.table.tableModels.ITableModelWithCellsCustomAlignment;
import org.jtools.gui.table.tableModels.ITableModelWithCellsCustomBackground;
import org.jtools.gui.table.tableModels.ITableModelWithMandatoryCells;
import org.jtools.mappings.editors.simple.SimpleMappingEditorRow.SimpleMappingEditorNewRow;
import org.jtools.mappings.editors.simple.SimpleMappingEditorRow.SimpleMappingRowType;
import org.jtools.mappings.simple.SimpleMapping;
import org.jtools.mappings.simple.SimpleMappingRow;
// TODO: Auto-generated Javadoc

/**
 * The Class SimpleMappingEditorTableModel.
 */
public class SimpleMappingEditorTableModel extends AbstractTableModel implements ITableModelWithMandatoryCells, ITableModelWithCellsCustomAlignment, ITableModelWithCellsCustomBackground {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 3270825863776856519L;

	/** The mapping. */
	private final SimpleMapping<?> mapping;
	
	/** The editor rows. */
	private final transient List<SimpleMappingEditorRow> editorRows;

	/**
	 * Instantiates a new simple mapping editor table model.
	 *
	 * @param mapping the mapping
	 */
	public SimpleMappingEditorTableModel(SimpleMapping<?> mapping) {
		this.mapping = mapping;
		List<SimpleMappingRow> rows = mapping.getRows();
		
		this.editorRows = new ArrayList<>();

		for(SimpleMappingRow row : rows) {
			editorRows.add(new SimpleMappingEditorRow(row));
		}

		editorRows.add(new SimpleMappingEditorNewRow());
	}

	/**
	 * Insert row.
	 *
	 * @param row the row
	 */
	public void insertRow(SimpleMappingEditorRow row) {
		editorRows.add(0, row);
		fireTableRowsInserted(0, 0);
	}

	/**
	 * Adds the row.
	 *
	 * @param row the row
	 */
	public void addRow(SimpleMappingEditorRow row) {
		editorRows.add(editorRows.size()-1, row);
		fireTableRowsInserted(editorRows.size() - 1, editorRows.size() - 1);
	}

	/**
	 * Removes the row.
	 *
	 * @param row the row
	 */
	public void removeRow(int row) {
		editorRows.remove(row);
		fireTableRowsDeleted(row, row);
	}

	/**
	 * Apply.
	 *
	 * @return the simple mapping
	 */
	public SimpleMapping<?> apply() {
		mapping.getRows().clear();
		
		for(SimpleMappingEditorRow edRow : editorRows) {
			if(edRow.getRowType() == SimpleMappingRowType.MAPPING_ROW) {
				mapping.getRows().add(edRow.getSimpleMappingRow());
			}
		}
		
		return mapping;
	}

	/**
	 * Gets the row count.
	 *
	 * @return the row count
	 */
	@Override
	public int getRowCount() {
		return editorRows.size();
	}

	/**
	 * Gets the value at.
	 *
	 * @param row the row
	 * @param column the column
	 * @return the value at
	 */
	@Override
	public Object getValueAt(int row, int column) {
		SimpleMappingEditorRow mappingEditorRow = editorRows.get(row);
		SimpleMappingEditorColumn mappingEditorColumn = SimpleMappingEditorColumn.forIndex(column);
		switch (mappingEditorColumn) {
		case ADD_REMOVE_BUTTON_COL: 
			return mappingEditorRow.getRowType();
		case OUTPUT_COLUMN_HEARDER_COL :
			return mappingEditorRow.getOutputColumnHeader();
		case OUTPUT_COLUMN_COL :
			return mappingEditorRow.getOutputColumn();
		case MAPPING_ARROW_COL :
			return mappingEditorRow.getMappingArrow();
		case OBJECT_FIELD_COL :
			return mappingEditorRow.getObjectField();
		default:
			throw new IllegalArgumentException("Unexpected value: " + column);
		}
	}

	/**
	 * Checks if is cell editable.
	 *
	 * @param row the row
	 * @param column the column
	 * @return true, if is cell editable
	 */
	@Override
	public boolean isCellEditable(int row, int column) {
		SimpleMappingEditorRow mappingEditorRow = editorRows.get(row);
		SimpleMappingEditorColumn mappingEditorColumn = SimpleMappingEditorColumn.forIndex(column);
		switch (mappingEditorColumn) {
		case ADD_REMOVE_BUTTON_COL: 
			return true;
		case OUTPUT_COLUMN_HEARDER_COL :
			return (mappingEditorRow.getRowType() == SimpleMappingRowType.MAPPING_ROW);
		case OUTPUT_COLUMN_COL :
			return (mappingEditorRow.getRowType() == SimpleMappingRowType.MAPPING_ROW);
		case MAPPING_ARROW_COL :
			return false;
		case OBJECT_FIELD_COL :
			return (mappingEditorRow.getRowType() == SimpleMappingRowType.MAPPING_ROW);
		default:
			throw new IllegalArgumentException("Unexpected value: " + column);
		}
	}

	/**
	 * Checks if is cell mandatory.
	 *
	 * @param row the row
	 * @param column the column
	 * @return true, if is cell mandatory
	 */
	@Override
	public boolean isCellMandatory(int row, int column) {
		SimpleMappingEditorRow mappingEditorRow = editorRows.get(row);

		if(mappingEditorRow.getRowType() != SimpleMappingRowType.MAPPING_ROW) {
			return false;
		}

		SimpleMappingEditorColumn mappingEditorColumn = SimpleMappingEditorColumn.forIndex(column);
		switch (mappingEditorColumn) {
		case ADD_REMOVE_BUTTON_COL: 
			return false;
		case OUTPUT_COLUMN_HEARDER_COL :
			return true;
		case OUTPUT_COLUMN_COL :
			return true;
		case MAPPING_ARROW_COL :
			return false;
		case OBJECT_FIELD_COL :
			return true;
		default:
			throw new IllegalArgumentException("Unexpected value: " + column);
		}
	}

	/**
	 * Gets the cell horizontal alignment.
	 *
	 * @param row the row
	 * @param column the column
	 * @return the cell horizontal alignment
	 */
	@Override
	public int getCellHorizontalAlignment(int row, int column) {
		return SwingConstants.CENTER;
	}

	/**
	 * Sets the value at.
	 *
	 * @param value the value
	 * @param row the row
	 * @param column the column
	 */
	@Override
	public void setValueAt(Object value, int row, int column) {
		SimpleMappingEditorRow mappingEditorRow = editorRows.get(row);
		SimpleMappingEditorColumn mappingEditorColumn = SimpleMappingEditorColumn.forIndex(column);
		switch (mappingEditorColumn) {
		case OUTPUT_COLUMN_HEARDER_COL :
			mappingEditorRow.setOutputColumnHeader((String)value);
			break;
		case OUTPUT_COLUMN_COL :
			mappingEditorRow.setOutputColumn((String)value);
			break;
		case OBJECT_FIELD_COL :
			mappingEditorRow.setObjectField((Field)value);
			break;
		default:
			// Nothing to do
			break;
		}
	}

	/**
	 * Gets the column class.
	 *
	 * @param columnIndex the column index
	 * @return the column class
	 */
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		SimpleMappingEditorColumn mappingEditorColumn = SimpleMappingEditorColumn.forIndex(columnIndex);
		return mappingEditorColumn.getColumnClass();
	}

	/**
	 * Gets the column count.
	 *
	 * @return the column count
	 */
	@Override
	public int getColumnCount() {
		return SimpleMappingEditorColumn.values().length;
	}

	/**
	 * Gets the column name.
	 *
	 * @param column the column
	 * @return the column name
	 */
	@Override
	public String getColumnName(int column) {
		SimpleMappingEditorColumn mappingEditorColumn = SimpleMappingEditorColumn.forIndex(column);
		return mappingEditorColumn.getLabel();
	}

	/**
	 * Gets the row.
	 *
	 * @param row the row
	 * @return the row
	 */
	public SimpleMappingEditorRow getRow(int row) {
		return editorRows.get(row);
	}

	/**
	 * Gets the cell background.
	 *
	 * @param row the row
	 * @param column the column
	 * @return the cell background
	 */
	@Override
	public Color getCellBackground(int row, int column) {
		SimpleMappingEditorRow mappingRow = getRow(row);
		if(mappingRow instanceof SimpleMappingEditorNewRow) {
			return UIManager.getColor("Panel.background");
		}
		return null;
	}
}
