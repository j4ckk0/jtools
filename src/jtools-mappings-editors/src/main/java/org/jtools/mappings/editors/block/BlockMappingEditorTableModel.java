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

import java.awt.Color;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.table.AbstractTableModel;

import org.jtools.gui.table.tableModels.ITableModelWithCellsCustomAlignment;
import org.jtools.gui.table.tableModels.ITableModelWithCellsCustomBackground;
import org.jtools.gui.table.tableModels.ITableModelWithMandatoryCells;
import org.jtools.gui.table.tableModels.ITableModelWithObjectWrapper;
import org.jtools.mappings.block.BlockMapping;
import org.jtools.mappings.editors.block.BlockMappingEditorRow.BlockMappingEditorNewRow;
import org.jtools.mappings.editors.block.BlockMappingEditorRow.BlockMappingRowType;
import org.jtools.utils.CommonUtils;
// TODO: Auto-generated Javadoc

/**
 * The Class BlockMappingEditorTableModel.
 *
 * @param <E> the element type
 */
public class BlockMappingEditorTableModel<E extends Object> extends AbstractTableModel
implements ITableModelWithMandatoryCells, ITableModelWithCellsCustomAlignment,
ITableModelWithCellsCustomBackground, ITableModelWithObjectWrapper, PropertyChangeListener {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 3270825863776856519L;

	/** The object class. */
	private final Class<E> objectClass;

	/** The mapping. */
	private final BlockMapping<E> mapping;
	
	/** The editor rows. */
	private final transient List<BlockMappingEditorRow> editorRows;

	/** The possible classes. */
	private final List<Class<?>> possibleClasses;

	/**
	 * Instantiates a new block mapping editor table model.
	 *
	 * @param mapping the mapping
	 * @param possibleClasses the possible classes
	 */
	public BlockMappingEditorTableModel(BlockMapping<E> mapping, Class<?>[] possibleClasses) {
		this.mapping = mapping;
		this.objectClass = mapping.getObjectClass();
		this.possibleClasses = Arrays.stream(possibleClasses).toList();
		this.editorRows = new ArrayList<>();

		editorRows.add(new BlockMappingEditorNewRow());
	}

	/**
	 * Inits the rows.
	 *
	 * @param rows the rows
	 */
	protected void initRows(List<BlockMappingEditorRow> rows) {
		this.editorRows.clear();

		if (rows != null) {
			for (BlockMappingEditorRow row : rows) {
				this.editorRows.add(row);
				addPropertyChangeListener(row);
			}
		}

		this.editorRows.add(new BlockMappingEditorNewRow());
	}

	/**
	 * Gets the object class.
	 *
	 * @return the object class
	 */
	public Class<E> getObjectClass() {
		return objectClass;
	}

	/**
	 * Insert row.
	 *
	 * @param row the row
	 */
	public void insertRow(BlockMappingEditorRow row) {
		editorRows.add(0, row);
		fireTableRowsInserted(0, 0);
		addPropertyChangeListener(row);
	}

	/**
	 * Adds the row.
	 *
	 * @param row the row
	 */
	public void addRow(BlockMappingEditorRow row) {
		editorRows.add(editorRows.size() - 1, row);
		fireTableRowsInserted(editorRows.size() - 1, editorRows.size() - 1);
		addPropertyChangeListener(row);
	}

	/**
	 * Removes the row.
	 *
	 * @param row the row
	 */
	public void removeRow(int row) {
		removePropertyChangeListener(editorRows.get(row));
		editorRows.remove(row);
		fireTableRowsDeleted(row, row);
	}

	/**
	 * Apply.
	 *
	 * @return the block mapping
	 */
	public BlockMapping<E> apply() {
		mapping.getRows().clear();
		
		for(BlockMappingEditorRow edRow : editorRows) {
			if(edRow.getRowType() == BlockMappingRowType.MAPPING_ROW) {
				mapping.getRows().add(edRow.getBlockMappingRow());
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

	/**
	 * Checks if is cell editable.
	 *
	 * @param row the row
	 * @param column the column
	 * @return true, if is cell editable
	 */
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

	/**
	 * Checks if is cell mandatory.
	 *
	 * @param row the row
	 * @param column the column
	 * @return true, if is cell mandatory
	 */
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

	/**
	 * Gets the column class.
	 *
	 * @param columnIndex the column index
	 * @return the column class
	 */
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		BlockMappingEditorColumn mappingEditorColumn = BlockMappingEditorColumn.forIndex(columnIndex);
		return mappingEditorColumn.getColumnClass();
	}

	/**
	 * Gets the column count.
	 *
	 * @return the column count
	 */
	@Override
	public int getColumnCount() {
		return BlockMappingEditorColumn.values().length;
	}

	/**
	 * Gets the column name.
	 *
	 * @param column the column
	 * @return the column name
	 */
	@Override
	public String getColumnName(int column) {
		BlockMappingEditorColumn mappingEditorColumn = BlockMappingEditorColumn.forIndex(column);
		return mappingEditorColumn.getLabel();
	}

	/**
	 * Gets the row.
	 *
	 * @param row the row
	 * @return the row
	 */
	public BlockMappingEditorRow getRow(int row) {
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
		BlockMappingEditorRow mappingRow = getRow(row);
		if (mappingRow instanceof BlockMappingEditorNewRow) {
			return UIManager.getColor("Panel.background");
		}
		return null;
	}

	/**
	 * Gets the wrapped class at.
	 *
	 * @param row the row
	 * @param column the column
	 * @return the wrapped class at
	 */
	@Override
	public Class<?> getWrappedClassAt(int row, int column) {
		return getObjectClass();
	}

	/**
	 * Gets the wrapped value at.
	 *
	 * @param row the row
	 * @param column the column
	 * @return the wrapped value at
	 */
	@Override
	public Object getWrappedValueAt(int row, int column) {
		throw new UnsupportedOperationException("Method getWrappedValueAt is unused");
	}

	/**
	 * Adds the property change listener.
	 *
	 * @param rows the rows
	 */
	private void addPropertyChangeListener(BlockMappingEditorRow... rows) {
		for (BlockMappingEditorRow row : rows) {
			PropertyChangeSupport propertyChangeSupport = row.getPropertyChangeSupport();
			propertyChangeSupport.addPropertyChangeListener(this);
		}
	}

	/**
	 * Removes the property change listener.
	 *
	 * @param rows the rows
	 */
	private void removePropertyChangeListener(BlockMappingEditorRow... rows) {
		for (BlockMappingEditorRow row : rows) {
			PropertyChangeSupport propertyChangeSupport = row.getPropertyChangeSupport();
			propertyChangeSupport.removePropertyChangeListener(this);
		}
	}

	/**
	 * Property change.
	 *
	 * @param evt the evt
	 */
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals(BlockMappingEditorRow.MAPPING_TYPE_PROPERTY)) {
			BlockMappingEditorRow row = (BlockMappingEditorRow) evt.getSource();
			int rowIndex = editorRows.indexOf(row);
			fireTableRowsUpdated(rowIndex, rowIndex);
		}
	}
}
