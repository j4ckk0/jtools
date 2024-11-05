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
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.AbstractCellEditor;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableModel;

import com.jtools.gui.form.ObjectForm;
import com.jtools.gui.table.tableModels.ITableModelWithCellsCustomAlignment;
import com.jtools.gui.table.tableModels.ITableModelWithObjectWrapper;
import com.jtools.gui.table.tableModels.ITableModelWithObjectWrapper.ObjectWrapper;

/**
 * @author j4ckk0
 *
 */
public class DefaultObjectTableCellEditor extends AbstractCellEditor implements TableCellEditor {

	private static final long serialVersionUID = 2356764750987524290L;

	private final JLabel component =  new JLabel();

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
