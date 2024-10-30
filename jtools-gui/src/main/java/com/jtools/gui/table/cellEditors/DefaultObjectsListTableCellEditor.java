/**
 * 
 */
package com.jtools.gui.table.cellEditors;

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

import java.awt.Component;
import java.util.List;

import javax.swing.AbstractCellEditor;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableModel;

import com.jtools.gui.list.ObjectsListPanel;
import com.jtools.gui.table.tableModels.ITableModelWithCellsCustomAlignment;
import com.jtools.gui.table.tableModels.ITableModelWithObjectWrapper.ObjectWrapper;
import com.jtools.gui.table.tableModels.ITableModelWithParameterizedObjectWrapper;
import com.jtools.gui.table.tableModels.ITableModelWithParameterizedTypes;
import com.jtools.utils.objects.ObjectUtils;

/**
 * @author j4ckk0
 *
 */
public class DefaultObjectsListTableCellEditor extends AbstractCellEditor implements TableCellEditor {

	private static final long serialVersionUID = 7601888500573169907L;

	private final JLabel component = new JLabel();

	private transient Object value;

	@Override
	public Object getCellEditorValue() {
		return value;
	}

	public void setCellEditorValue(Object value) {
		this.value = value;
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		setCellEditorValue(value);

		TableModel model = table.getModel();

		Class<?> parameterizedType = getParameterizedType(model, value, row, column);

		if (parameterizedType != null) {
			showEditorAsynch(table, parameterizedType, (List<?>) value);
		}

		if (model instanceof ITableModelWithCellsCustomAlignment) {
			((JLabel) component).setHorizontalAlignment(
					((ITableModelWithCellsCustomAlignment) model).getCellHorizontalAlignment(row, column));
		}

		component.setText(
				getCellEditorValue() != null ? ObjectUtils.toString(((List<?>) getCellEditorValue()), ", ") : null);
		return component;
	}

	private Class<?> getParameterizedType(TableModel model, Object value2, int row, int column) {
		if (value instanceof ObjectWrapper) {
			if (model instanceof ITableModelWithParameterizedObjectWrapper) {
				return ((ITableModelWithParameterizedObjectWrapper) model).getWrappedParameterizedClass(row, column);
			}
		}

		if (value instanceof List) {

			if (!((List<?>) value).isEmpty()) {
				return ((List<?>) value).get(0).getClass();
			} else if (model instanceof ITableModelWithParameterizedTypes) {
				return ((ITableModelWithParameterizedTypes) model).getColumnParameterizedClass(column);
			}

			component.setText(ObjectUtils.toString(((List<?>) value), ", "));
		}

		else {
			Class<?> columnClass = model.getColumnClass(column);

			if (ObjectWrapper.class.isAssignableFrom(columnClass)) {
				if (model instanceof ITableModelWithParameterizedObjectWrapper) {
					return ((ITableModelWithParameterizedObjectWrapper) model).getWrappedParameterizedClass(row,
							column);
				}
			}

			if (List.class.isAssignableFrom(columnClass)) {
				if (model instanceof ITableModelWithParameterizedTypes) {
					return ((ITableModelWithParameterizedTypes) model).getColumnParameterizedClass(column);
				}
			}
		}

		return null;
	}

	private void showEditorAsynch(JTable table, Class<?> parameterizedType, List<?> value) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				@SuppressWarnings({ "unchecked", "rawtypes" })
				ObjectsListPanel<?> objectsListPanel = new ObjectsListPanel<>(parameterizedType, (List) value);
				int result = objectsListPanel.showDialog();
				if (result == JOptionPane.OK_OPTION) {
					setCellEditorValue(objectsListPanel.getElements());
					table.editingStopped(new ChangeEvent(DefaultObjectsListTableCellEditor.this));
				}
			}
		});
	}

}
