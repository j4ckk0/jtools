/**
 * 
 */
package com.jtools.gui.table.tableModels;

/*-
 * #%L
 * Java Tools - GUI
 * %%
 * Copyright (C) 2024 j4ckk0
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

import com.jtools.gui.table.tableModels.IObjectRow.NewRow;
import com.jtools.gui.table.tableModels.IObjectRow.ObjectRow;
import com.jtools.utils.gui.GuiUtils;
import com.jtools.utils.objects.ObjectInfoProvider;

/**
 * @author j4ckk0
 *
 */
public class ObjectsTableModel<E extends Object> extends AbstractTableModel implements ITableModelWithMandatoryCells, ITableModelWithCellsCustomAlignment, ITableModelWithCellsCustomBackground, ITableModelWithParameterizedTypes {

	private static final long serialVersionUID = 3270825863776856519L;

	public static final int ADD_REMOVE_BUTTON_COL = 0;

	private final Class<E> objectClass;

	private final List<IObjectRow> rows;

	public ObjectsTableModel(Class<E> objectClass, List<IObjectRow> rows) {
		this.objectClass = objectClass;
		this.rows = rows;
	}

	public void addRow(IObjectRow row) {
		rows.add(rows.size() - 1, row);
		fireTableRowsInserted(rows.size() - 1, rows.size() - 1);
	}

	public void insertRow(IObjectRow row) {
		rows.add(0, row);
		fireTableRowsInserted(0, 0);
	}

	public void removeRow(int row) {
		rows.remove(row);
		fireTableRowsDeleted(row, row);
	}

	@SuppressWarnings("rawtypes")
	public List<ObjectRow> getRows() {
		return rows.stream().filter(ObjectRow.class::isInstance).map(ObjectRow.class::cast)
				.collect(Collectors.toUnmodifiableList());
	}

	@Override
	public int getRowCount() {
		return rows.size();
	}

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

	@Override
	public boolean isCellMandatory(int row, int column) {
		//IObjectRow objectRow = rows.get(row);
		//return (!(objectRow instanceof NewRow));
		return false;
	}

	@Override
	public int getCellHorizontalAlignment(int row, int column) {
		switch (column) {
		case ADD_REMOVE_BUTTON_COL:
			return SwingConstants.CENTER;
		default:
			return SwingConstants.CENTER;
		}
	}

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

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		switch (columnIndex) {
		case ADD_REMOVE_BUTTON_COL:
			return String.class;
		default:
			return ObjectInfoProvider.getObjectInfo(objectClass).getPossibleFields().get(columnIndex-1).getType();
		}
	}

	@Override
	public Class<?> getColumnParameterizedClass(int columnIndex) {
		Field field = ObjectInfoProvider.getObjectInfo(objectClass).getPossibleFields().get(columnIndex-1);
		if(field.getGenericType() instanceof ParameterizedType) {
			ParameterizedType parameterizedType = (ParameterizedType) field.getGenericType();
			return (Class<?>) parameterizedType.getActualTypeArguments()[0];
		}
		return null;
	}

	@Override
	public int getColumnCount() {
		return ObjectInfoProvider.getObjectInfo(objectClass).getPossibleFieldsCount() + 1;
	}

	@Override
	public String getColumnName(int column) {
		switch (column) {
		case ADD_REMOVE_BUTTON_COL:
			return "";
		default:
			return ObjectInfoProvider.getObjectInfo(objectClass).getPossibleFields().get(column - 1).getName();
		}
	}

	public IObjectRow getRow(int row) {
		return rows.get(row);
	}

	@Override
	public Color getCellBackground(int row, int column) {
		IObjectRow objectRow = getRow(row);
		if(objectRow instanceof NewRow) {
			return UIManager.getColor("Panel.background");
		}
		return null;
	}
}
