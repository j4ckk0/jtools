package org.jtools.gui.table.cellEditors;

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

import java.awt.Component;

import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableModel;

import org.jtools.gui.table.tableModels.ITableModelWithObjectWrapper;
// TODO: Auto-generated Javadoc

/**
 * The Class ObjectWrapperCellEditor.
 */
public class ObjectWrapperCellEditor extends AbstractCellEditor implements TableCellEditor {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 2558770580026849153L;

	/** The delegate. */
	private transient TableCellEditor delegate;

	/**
	 * Gets the cell editor value.
	 *
	 * @return the cell editor value
	 */
	@Override
	public Object getCellEditorValue() {
		return delegate.getCellEditorValue();
	}

	/**
	 * Gets the table cell editor component.
	 *
	 * @param table the table
	 * @param value the value
	 * @param isSelected the is selected
	 * @param row the row
	 * @param column the column
	 * @return the table cell editor component
	 */
	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
			int column) {
		TableModel model = table.getModel();
		if (model instanceof ITableModelWithObjectWrapper) {
			Class<?> type = ((ITableModelWithObjectWrapper) model).getWrappedClassAt(row, column);
			if (DefaultNumberTableCellEditor.isAssignableFrom(type)) {
				delegate = DefaultNumberTableCellEditor.getEditorForColumnClass(type);
			} else {
				delegate = table.getDefaultEditor(type);
			}

			if (delegate != null) {
				return delegate.getTableCellEditorComponent(table, value, isSelected, row, column);
			}
		}
		return null;
	}

}
