package org.jtools.gui.form;

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

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import org.jtools.gui.table.tableModels.ITableModelWithCellsCustomAlignment;
import org.jtools.gui.table.tableModels.ITableModelWithParameterizedObjectWrapper;
import org.jtools.utils.objects.ObjectInfoProvider.ObjectInfo;

/**
 * 
 * @author j4ckk0
 *
 */
class ObjectFormTableModel extends DefaultTableModel
implements ITableModelWithCellsCustomAlignment, ITableModelWithParameterizedObjectWrapper {

	private static final long serialVersionUID = -5429348199335029848L;

	private static final int FIELD_LABEL_COLUMN_INDEX = 0;
	private static final int FIELD_VALUE_COLUMN_INDEX = 1;

	private static final int[] columns = new int[] { FIELD_LABEL_COLUMN_INDEX, FIELD_VALUE_COLUMN_INDEX };

	protected final transient Object object;
	protected final transient ObjectInfo objectInfo;

	public ObjectFormTableModel(Object object, ObjectInfo objectInfo) {
		this.object = object;
		this.objectInfo = objectInfo;
	}

	public Field getField(int row) {
		return objectInfo.getPossibleFields().get(row);
	}

	@Override
	public int getRowCount() {
		if (objectInfo != null) {
			return objectInfo.getPossibleFieldsCount();
		} else {
			return 0;
		}
	}

	@Override
	public Object getValueAt(int row, int column) {
		Field field = getField(row);
		switch (column) {
		case FIELD_LABEL_COLUMN_INDEX:
			return field.getName() + ": ";
		case FIELD_VALUE_COLUMN_INDEX:
			try {
				Method getter = objectInfo.findGetter(field);
				if (getter != null) {
					return getter.invoke(object);
				} else {
					Logger.getLogger(getClass().getName()).log(Level.FINE,
							"getter not found for field " + field.getName());
					return null;
				}
			} catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
				Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage());
				Logger.getLogger(getClass().getName()).log(Level.FINE, e.getMessage(), e);
				return null;
			}
		default:
			throw new IllegalArgumentException("Unexpected value: " + column);
		}
	}

	@Override
	public void setValueAt(Object value, int row, int column) {
		Field field = getField(row);
		switch (column) {
		case FIELD_LABEL_COLUMN_INDEX:
			return;
		case FIELD_VALUE_COLUMN_INDEX:
			try {
				Method setter = objectInfo.findSetter(field);
				if (setter != null) {
					setter.invoke(object, value);
					return;
				} else {
					Logger.getLogger(getClass().getName()).log(Level.FINE,
							"setter not found for field " + field.getName());
				}
			} catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
				Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage());
				Logger.getLogger(getClass().getName()).log(Level.FINE, e.getMessage(), e);
				return;
			}
		default:
			throw new IllegalArgumentException("Unexpected value: " + column);
		}
	}

	@Override
	public Class<?> getColumnClass(int column) {
		switch (column) {
		case FIELD_LABEL_COLUMN_INDEX:
			return String.class;
		case FIELD_VALUE_COLUMN_INDEX:
			return ObjectWrapper.class;
		default:
			throw new IllegalArgumentException("Unexpected value: " + column);
		}
	}

	@Override
	public int getColumnCount() {
		return columns.length;
	}

	@Override
	public String getColumnName(int column) {
		switch (column) {
		case FIELD_LABEL_COLUMN_INDEX:
			return "Name";
		case FIELD_VALUE_COLUMN_INDEX:
			return "Value";
		default:
			throw new IllegalArgumentException("Unexpected value: " + column);
		}
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		switch (column) {
		case FIELD_LABEL_COLUMN_INDEX:
			return false;
		case FIELD_VALUE_COLUMN_INDEX:
			return true;
		default:
			throw new IllegalArgumentException("Unexpected value: " + column);
		}
	}

	@Override
	public int getCellHorizontalAlignment(int row, int column) {
		switch (column) {
		case FIELD_LABEL_COLUMN_INDEX:
			return SwingConstants.RIGHT;
		case FIELD_VALUE_COLUMN_INDEX:
			return SwingConstants.CENTER;
		default:
			throw new IllegalArgumentException("Unexpected value: " + column);
		}
	}

	@Override
	public Class<?> getWrappedClassAt(int row, int column) {
		Field field = getField(row);
		return field.getType();
	}

	@Override
	public Object getWrappedValueAt(int row, int column) {
		return getValueAt(row, column);
	}

	@Override
	public Class<?> getWrappedParameterizedClass(int row, int column) {
		Field field = getField(row);
		if(field.getGenericType() instanceof ParameterizedType) {
			ParameterizedType parameterizedType = (ParameterizedType) field.getGenericType();
			return (Class<?>) parameterizedType.getActualTypeArguments()[0];
		}
		return null;
	}
}
