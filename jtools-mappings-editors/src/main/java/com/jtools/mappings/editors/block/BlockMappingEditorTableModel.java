/**
 * 
 */
package com.jtools.mappings.editors.block;

import java.awt.Color;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.table.AbstractTableModel;

import com.jtools.generic.gui.table.tableModels.ITableModelWithCellsCustomAlignment;
import com.jtools.generic.gui.table.tableModels.ITableModelWithCellsCustomBackground;
import com.jtools.generic.gui.table.tableModels.ITableModelWithMandatoryCells;
import com.jtools.generic.gui.table.tableModels.ITableModelWithObjectWrapper;
import com.jtools.mappings.block.BlockMapping;
import com.jtools.mappings.block.BlockMappingRow;
import com.jtools.mappings.editors.block.BlockMappingEditorRow.BlockMappingEditorNewRow;
import com.jtools.mappings.editors.block.BlockMappingEditorRow.BlockMappingRowType;
import com.jtools.utils.CommonUtils;

/**
 * @author j4ckk0
 *
 */
public class BlockMappingEditorTableModel<E extends Object> extends AbstractTableModel
implements ITableModelWithMandatoryCells, ITableModelWithCellsCustomAlignment,
ITableModelWithCellsCustomBackground, ITableModelWithObjectWrapper, PropertyChangeListener {

	private static final long serialVersionUID = 3270825863776856519L;

	private final Class<E> objectClass;

	private final List<BlockMappingEditorRow> editorRows;

	private final List<Class<?>> possibleClasses;

	public BlockMappingEditorTableModel(Class<E> objectClass, Class<?>[] possibleClasses) {
		this.objectClass = objectClass;
		this.possibleClasses = Arrays.stream(possibleClasses).toList();
		this.editorRows = new ArrayList<>();

		editorRows.add(new BlockMappingEditorNewRow());
	}

	protected void initRows(List<BlockMappingEditorRow> rows) {
		this.editorRows.clear();

		if (rows != null) {
			for (BlockMappingEditorRow row : rows) {
				this.editorRows.add(row);
				addPropertyChangeListener(this, row);
			}
		}

		this.editorRows.add(new BlockMappingEditorNewRow());
	}

	public Class<E> getObjectClass() {
		return objectClass;
	}

	public void insertRow(BlockMappingEditorRow row) {
		editorRows.add(0, row);
		fireTableRowsInserted(0, 0);
		addPropertyChangeListener(this, row);
	}

	public void addRow(BlockMappingEditorRow row) {
		editorRows.add(editorRows.size() - 1, row);
		fireTableRowsInserted(editorRows.size() - 1, editorRows.size() - 1);
		addPropertyChangeListener(this, row);
	}

	public void removeRow(int row) {
		removePropertyChangeListener(this, editorRows.get(row));
		editorRows.remove(row);
		fireTableRowsDeleted(row, row);
	}

	public List<BlockMappingRow> getRows() {
		
		List<BlockMappingRow> rows = new ArrayList<>();
		editorRows.stream().filter(m -> m.getRowType() == BlockMappingRowType.MAPPING_ROW).map(m -> rows.add(m.getBlockMappingRow()));
		
		return Collections.unmodifiableList(rows);
	}

	@Override
	public int getRowCount() {
		return editorRows.size();
	}

	@Override
	public Object getValueAt(int row, int column) {
		BlockMappingEditorRow mappingEditorRow = editorRows.get(row);
		BlockMappingEditorColumn mappingEditorColumn = BlockMappingEditorColumn.forIndex(column);
		switch (mappingEditorColumn) {
		case ADD_REMOVE_BUTTON_COL:
			return mappingEditorRow.getRowType();
		case FROM_COLUMN_COL:
			return mappingEditorRow.getFromColumn();
		case TO_COLUMN_COL:
			return mappingEditorRow.getToColumn();
		case MAPPING_ARROW_COL:
			return mappingEditorRow.getMappingArrow();
		case OBJECT_FIELD_COL:
			return mappingEditorRow.getObjectField();
		case HEADER_VALUE_COL:
			return mappingEditorRow.getHeaderValue();
		case SUB_BLOCK_MAPPING_COL:
			return mappingEditorRow.getSubBlockMapping();
		default:
			throw new IllegalArgumentException("Unexpected value: " + column);
		}
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		BlockMappingEditorRow mappingEditorRow = editorRows.get(row);
		BlockMappingEditorColumn mappingEditorColumn = BlockMappingEditorColumn.forIndex(column);
		switch (mappingEditorColumn) {
		case ADD_REMOVE_BUTTON_COL:
			return true;
		case FROM_COLUMN_COL:
			return (mappingEditorRow.getRowType() == BlockMappingRowType.MAPPING_ROW);
		case TO_COLUMN_COL:
			return (mappingEditorRow.getRowType() == BlockMappingRowType.MAPPING_ROW);
		case MAPPING_ARROW_COL:
			return false;
		case OBJECT_FIELD_COL:
			return (mappingEditorRow.getRowType() == BlockMappingRowType.MAPPING_ROW);
		case HEADER_VALUE_COL:
			return (mappingEditorRow.getRowType() == BlockMappingRowType.MAPPING_ROW);
		case SUB_BLOCK_MAPPING_COL:

			if (mappingEditorRow.getRowType() != BlockMappingRowType.MAPPING_ROW) {
				return false;
			}

			Class<?> objectClass = BlockMappingTableCellEditor.getObjectClass(
					mappingEditorRow.getObjectField(), CommonUtils.classListToArray(possibleClasses));
			return (objectClass != null);

		default:
			throw new IllegalArgumentException("Unexpected value: " + column);
		}
	}

	@Override
	public boolean isCellMandatory(int row, int column) {
		BlockMappingEditorRow mappingEditorRow = editorRows.get(row);

		if (mappingEditorRow.getRowType() != BlockMappingRowType.MAPPING_ROW) {
			return false;
		}

		BlockMappingEditorColumn mappingEditorColumn = BlockMappingEditorColumn.forIndex(column);
		switch (mappingEditorColumn) {
		case FROM_COLUMN_COL:
			return true;
		case TO_COLUMN_COL:
			return true;
		case MAPPING_ARROW_COL:
			return false;
		case OBJECT_FIELD_COL:
			return true;
		case HEADER_VALUE_COL:
			return true;
		case SUB_BLOCK_MAPPING_COL:
			Field objectField = mappingEditorRow.getObjectField();
			if (objectField != null) {
				return possibleClasses.contains(objectField.getType());
			}
			return false;
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
		BlockMappingEditorRow mappingEditorRow = editorRows.get(row);
		BlockMappingEditorColumn mappingEditorColumn = BlockMappingEditorColumn.forIndex(column);
		switch (mappingEditorColumn) {
		case FROM_COLUMN_COL:
			mappingEditorRow.setFromColumn((String) value);
			break;
		case TO_COLUMN_COL:
			mappingEditorRow.setToColumn((String) value);
			break;
		case OBJECT_FIELD_COL:
			mappingEditorRow.setObjectField((Field) value);
			break;
		case HEADER_VALUE_COL:
			mappingEditorRow.setHeaderValue((String) value);
			break;
		case SUB_BLOCK_MAPPING_COL:
			mappingEditorRow.setSubBlockMapping((BlockMapping<?>) value);
			break;
		default:
			// Nothing to do
			break;
		}
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		BlockMappingEditorColumn mappingEditorColumn = BlockMappingEditorColumn.forIndex(columnIndex);
		return mappingEditorColumn.getColumnClass();
	}

	@Override
	public int getColumnCount() {
		return BlockMappingEditorColumn.values().length;
	}

	@Override
	public String getColumnName(int column) {
		BlockMappingEditorColumn mappingEditorColumn = BlockMappingEditorColumn.forIndex(column);
		return mappingEditorColumn.getLabel();
	}

	public BlockMappingEditorRow getRow(int row) {
		return editorRows.get(row);
	}

	@Override
	public Color getCellBackground(int row, int column) {
		BlockMappingEditorRow mappingRow = getRow(row);
		if (mappingRow instanceof BlockMappingEditorNewRow) {
			return UIManager.getColor("Panel.background");
		}
		return null;
	}

	@Override
	public Class<?> getWrappedClassAt(int row, int column) {
		return getObjectClass();
	}

	@Override
	public Object getWrappedValueAt(int row, int column) {
		throw new UnsupportedOperationException("Method getWrappedValueAt is unused");
	}

	private void addPropertyChangeListener(BlockMappingEditorTableModel<E> blockMappingEditorTableModel,
			BlockMappingEditorRow... rows) {
		for (BlockMappingEditorRow row : rows) {
			PropertyChangeSupport propertyChangeSupport = row.getPropertyChangeSupport();
			propertyChangeSupport.addPropertyChangeListener(this);
		}
	}

	private void removePropertyChangeListener(BlockMappingEditorTableModel<E> blockMappingEditorTableModel,
			BlockMappingEditorRow... rows) {
		for (BlockMappingEditorRow row : rows) {
			PropertyChangeSupport propertyChangeSupport = row.getPropertyChangeSupport();
			propertyChangeSupport.removePropertyChangeListener(this);
		}
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals(BlockMappingEditorRow.MAPPING_TYPE_PROPERTY)) {
			BlockMappingEditorRow row = (BlockMappingEditorRow) evt.getSource();
			int rowIndex = editorRows.indexOf(row);
			fireTableRowsUpdated(rowIndex, rowIndex);
		}
	}
}
