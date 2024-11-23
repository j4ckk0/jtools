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
import java.util.Date;

import javax.swing.DefaultCellEditor;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableModel;

import org.jtools.gui.table.tableModels.ITableModelWithCellsCustomAlignment;
import org.jtools.utils.dates.DateFormatManager;
// TODO: Auto-generated Javadoc

/**
 * The Class DefaultDateTableCellEditor.
 */
public class DefaultDateTableCellEditor extends DefaultCellEditor {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 4298535018758190505L;

	/**
	 * Instantiates a new default date table cell editor.
	 */
	public DefaultDateTableCellEditor() {
		super(new JTextField());
	}

	/**
	 * Gets the table cell editor component.
	 *
	 * @param table the table
	 * @param value the value
	 * @param isSelected the is selected
	 * @param row the row
	 * @param col the col
	 * @return the table cell editor component
	 */
	@Override
	public Component getTableCellEditorComponent(JTable table,  Object value, boolean isSelected, int row, int col) {   
		Component component = super.getTableCellEditorComponent(table, value, isSelected, row, col);
		if (component instanceof JTextField) {
			TableModel model = table.getModel();
			if(model instanceof ITableModelWithCellsCustomAlignment) {
				((JTextField) component).setHorizontalAlignment(((ITableModelWithCellsCustomAlignment)model).getCellHorizontalAlignment(row, col));
			}
			if(value instanceof Date) {
				((JTextField)component).setText(DateFormatManager.instance().format((Date)value));
			}
		}

		return component;
	}

	/**
	 * Gets the cell editor value.
	 *
	 * @return the cell editor value
	 */
	@Override
	public Date getCellEditorValue() {
		return DateFormatManager.instance().parse((String)super.getCellEditorValue());
	}

}
