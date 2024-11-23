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
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.AbstractCellEditor;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableModel;

import org.jtools.gui.form.ObjectForm;
import org.jtools.gui.table.tableModels.ITableModelWithCellsCustomAlignment;
import org.jtools.gui.table.tableModels.ITableModelWithObjectWrapper;
import org.jtools.gui.table.tableModels.ITableModelWithObjectWrapper.ObjectWrapper;
// TODO: Auto-generated Javadoc

/**
 * The Class DefaultObjectTableCellEditor.
 */
public class DefaultObjectTableCellEditor extends AbstractCellEditor implements TableCellEditor {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 2356764750987524290L;

	/** The component. */
	private final JLabel component =  new JLabel();

	/** The value. */
	private transient Object value;

	/**
	 * Gets the cell editor value.
	 *
	 * @return the cell editor value
	 */
	@Override
	public Object getCellEditorValue() {
		return value;
	}
	
	/**
	 * Sets the cell editor value.
	 *
	 * @param value the new cell editor value
	 */
	public void setCellEditorValue(Object value) {
		this.value = value;
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
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		TableModel model = table.getModel();
		
		ObjectForm form = null;
		if (value != null) {
			// Build form
			form = new ObjectForm(value);
		} else {
			// Get object class
			Class<?> objectClass = model.getColumnClass(column);

			if(ObjectWrapper.class.isAssignableFrom(objectClass)) {
				if(model instanceof ITableModelWithObjectWrapper) {
					objectClass = ((ITableModelWithObjectWrapper)model).getWrappedClassAt(row, column);
				} else {
					Logger.getLogger(getClass().getName()).log(Level.SEVERE, "ObjectWrapper type found but table model does not implement com.jtools.utils.objects.gui.form.ITableModelWithObjectWraper");
					return component;
				}
			} 

			// Build form
			try {
				form = new ObjectForm(objectClass);
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException | SecurityException e) {
				Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage());
				Logger.getLogger(getClass().getName()).log(Level.FINE, e.getMessage(), e);
			}
		}

		if(form == null) {
			return component;
		}

		int result = form.showDialog();
		if (result == JOptionPane.OK_OPTION) {
			setCellEditorValue(form.getObject());
		}

		if(model instanceof ITableModelWithCellsCustomAlignment) {
			((JLabel) component).setHorizontalAlignment(((ITableModelWithCellsCustomAlignment)model).getCellHorizontalAlignment(row, column));
		}
		
		component.setText(getCellEditorValue() != null ? getCellEditorValue().toString() : null);
		return component;
	}

}
