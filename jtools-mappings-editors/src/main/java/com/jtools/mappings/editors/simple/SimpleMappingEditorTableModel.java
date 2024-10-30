/**
 * 
 */
package com.jtools.mappings.editors.simple;

import java.awt.Color;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.table.AbstractTableModel;

import com.jtools.gui.table.tableModels.ITableModelWithCellsCustomAlignment;
import com.jtools.gui.table.tableModels.ITableModelWithCellsCustomBackground;
import com.jtools.gui.table.tableModels.ITableModelWithMandatoryCells;
import com.jtools.mappings.editors.simple.SimpleMappingEditorRow.SimpleMappingEditorNewRow;
import com.jtools.mappings.editors.simple.SimpleMappingEditorRow.SimpleMappingRowType;
import com.jtools.mappings.simple.SimpleMapping;
import com.jtools.mappings.simple.SimpleMappingRow;

/**
 * @author j4ckk0
 *
 */
public class SimpleMappingEditorTableModel extends AbstractTableModel implements ITableModelWithMandatoryCells, ITableModelWithCellsCustomAlignment, ITableModelWithCellsCustomBackground {

	private static final long serialVersionUID = 3270825863776856519L;

	private final SimpleMapping<?> mapping;
	private final List<SimpleMappingEditorRow> editorRows;

	public SimpleMappingEditorTableModel(SimpleMapping<?> mapping) {
		this.mapping = mapping;
		List<SimpleMappingRow> rows = mapping.getRows();
		
		this.editorRows = new ArrayList<>();

		for(SimpleMappingRow row : rows) {
			editorRows.add(new SimpleMappingEditorRow(row));
		}

		editorRows.add(new SimpleMappingEditorNewRow());
	}

	public void insertRow(SimpleMappingEditorRow row) {
		editorRows.add(0, row);
		fireTableRowsInserted(0, 0);
	}

	public void addRow(SimpleMappingEditorRow row) {
		editorRows.add(editorRows.size()-1, row);
		fireTableRowsInserted(editorRows.size() - 1, editorRows.size() - 1);
	}

	public void removeRow(int row) {
		editorRows.remove(row);
		fireTableRowsDeleted(row, row);
	}

	public SimpleMapping<?> apply() {
		mapping.getRows().clear();
		
		for(SimpleMappingEditorRow edRow : editorRows) {
			if(edRow.getRowType() == SimpleMappingRowType.MAPPING_ROW) {
				mapping.getRows().add(edRow.getSimpleMappingRow());
			}
		}
		
		return mapping;
	}

	@Override
	public int getRowCount() {
		return editorRows.size();
	}

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

	@Override
	public int getCellHorizontalAlignment(int row, int column) {
		return SwingConstants.CENTER;
	}

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

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		SimpleMappingEditorColumn mappingEditorColumn = SimpleMappingEditorColumn.forIndex(columnIndex);
		return mappingEditorColumn.getColumnClass();
	}

	@Override
	public int getColumnCount() {
		return SimpleMappingEditorColumn.values().length;
	}

	@Override
	public String getColumnName(int column) {
		SimpleMappingEditorColumn mappingEditorColumn = SimpleMappingEditorColumn.forIndex(column);
		return mappingEditorColumn.getLabel();
	}

	public SimpleMappingEditorRow getRow(int row) {
		return editorRows.get(row);
	}

	@Override
	public Color getCellBackground(int row, int column) {
		SimpleMappingEditorRow mappingRow = getRow(row);
		if(mappingRow instanceof SimpleMappingEditorNewRow) {
			return UIManager.getColor("Panel.background");
		}
		return null;
	}
}
