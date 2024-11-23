package org.jtools.gui.table.tableModels;

/*-
 * #%L
 * Java Tools - GUI
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
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.table.AbstractTableModel;

import org.jtools.gui.table.tableModels.IObjectRow.NewRow;
import org.jtools.gui.table.tableModels.IObjectRow.ObjectRow;
import org.jtools.utils.gui.GuiUtils;
import org.jtools.utils.objects.ObjectInfoProvider;
// TODO: Auto-generated Javadoc

/**
 * The Class ObjectsTableModel.
 *
 * @param <E> the element type
 */
public class ObjectsTableModel<E extends Object> extends AbstractTableModel implements ITableModelWithMandatoryCells, ITableModelWithCellsCustomAlignment, ITableModelWithCellsCustomBackground, ITableModelWithParameterizedTypes {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 3270825863776856519L;

	/** The Constant ADD_REMOVE_BUTTON_COL. */
	public static final int ADD_REMOVE_BUTTON_COL = 0;

	/** The object class. */
	private final Class<E> objectClass;

	/** The rows. */
	private final List<IObjectRow> rows;

	/**
	 * Instantiates a new objects table model.
	 *
	 * @param objectClass the object class
	 * @param rows the rows
	 */
	public ObjectsTableModel(Class<E> objectClass, List<IObjectRow> rows) {
		this.objectClass = objectClass;
		this.rows = rows;
	}

	/**
	 * Adds the row.
	 *
	 * @param row the row
	 */
	public void addRow(IObjectRow row) {
		rows.add(rows.size() - 1, row);
		fireTableRowsInserted(rows.size() - 1, rows.size() - 1);
	}

	/**
	 * Insert row.
	 *
	 * @param row the row
	 */
	public void insertRow(IObjectRow row) {
		rows.add(0, row);
		fireTableRowsInserted(0, 0);
	}

	/**
	 * Removes the row.
	 *
	 * @param row the row
	 */
	public void removeRow(int row) {
		rows.remove(row);
		fireTableRowsDeleted(row, row);
	}

	/**
	 * Gets the rows.
	 *
	 * @return the rows
	 */
	@SuppressWarnings("rawtypes")
	public List<ObjectRow> getRows() {
		return rows.stream().filter(ObjectRow.class::isInstance).map(ObjectRow.class::cast)
				.collect(Collectors.toUnmodifiableList());
	}

	/**
	 * Gets the row count.
	 *
	 * @return the row count
	 */
	@Override
	public int getRowCount() {
		return rows.size();
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
		IObjectRow objectRow = rows.get(row);
		switch (column) {
		case ADD_REMOVE_BUTTON_COL:
			return (objectRow instanceof NewRow) ? GuiUtils.PLUS_ICON_TXT : GuiUtils.MINUS_ICON_TXT;
		default:
			return objectRow.getValueAt(column);
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
		IObjectRow objectRow = rows.get(row);
		switch (column) {
		case ADD_REMOVE_BUTTON_COL:
			return true;
		default:
			return !(objectRow instanceof NewRow);
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
		//IObjectRow objectRow = rows.get(row);
		//return (!(objectRow instanceof NewRow));
		return false;
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
		switch (column) {
		case ADD_REMOVE_BUTTON_COL:
			return SwingConstants.CENTER;
		default:
			return SwingConstants.CENTER;
		}
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
		IObjectRow objectRow = rows.get(row);
		switch (column) {
		case ADD_REMOVE_BUTTON_COL:
			return;
		default:
			objectRow.setValueAt(column, value);
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
		switch (columnIndex) {
		case ADD_REMOVE_BUTTON_COL:
			return String.class;
		default:
			return ObjectInfoProvider.getObjectInfo(objectClass).getPossibleFields().get(columnIndex-1).getType();
		}
	}

	/**
	 * Gets the column parameterized class.
	 *
	 * @param columnIndex the column index
	 * @return the column parameterized class
	 */
	@Override
	public Class<?> getColumnParameterizedClass(int columnIndex) {
		Field field = ObjectInfoProvider.getObjectInfo(objectClass).getPossibleFields().get(columnIndex-1);
		if(field.getGenericType() instanceof ParameterizedType) {
			ParameterizedType parameterizedType = (ParameterizedType) field.getGenericType();
			return (Class<?>) parameterizedType.getActualTypeArguments()[0];
		}
		return null;
	}

	/**
	 * Gets the column count.
	 *
	 * @return the column count
	 */
	@Override
	public int getColumnCount() {
		return ObjectInfoProvider.getObjectInfo(objectClass).getPossibleFieldsCount() + 1;
	}

	/**
	 * Gets the column name.
	 *
	 * @param column the column
	 * @return the column name
	 */
	@Override
	public String getColumnName(int column) {
		switch (column) {
		case ADD_REMOVE_BUTTON_COL:
			return "";
		default:
			return ObjectInfoProvider.getObjectInfo(objectClass).getPossibleFields().get(column - 1).getName();
		}
	}

	/**
	 * Gets the row.
	 *
	 * @param row the row
	 * @return the row
	 */
	public IObjectRow getRow(int row) {
		return rows.get(row);
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
		IObjectRow objectRow = getRow(row);
		if(objectRow instanceof NewRow) {
			return UIManager.getColor("Panel.background");
		}
		return null;
	}
}
