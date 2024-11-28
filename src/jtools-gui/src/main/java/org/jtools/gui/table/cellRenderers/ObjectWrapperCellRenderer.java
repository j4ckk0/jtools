package org.jtools.gui.table.cellRenderers;

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

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;

import org.jtools.gui.table.tableModels.ITableModelWithObjectWrapper;
import org.jtools.gui.table.tableModels.ITableModelWithObjectWrapper.ObjectWrapper;
// TODO: Auto-generated Javadoc

/**
 * The Class ObjectWrapperCellRenderer.
 */
public class ObjectWrapperCellRenderer extends DefaultTableCellRenderer {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 693240225989571061L;

	/**
	 * Instantiates a new object wrapper cell renderer.
	 */
	public ObjectWrapperCellRenderer() {
	}

	/**
	 * Gets the table cell renderer component.
	 *
	 * @param table      the table
	 * @param value      the value
	 * @param isSelected the is selected
	 * @param hasFocus   the has focus
	 * @param row        the row
	 * @param column     the column
	 * @return the table cell renderer component
	 */
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

		if (c instanceof JLabel) {
			TableModel model = table.getModel();
			if (value instanceof ObjectWrapper && model instanceof ITableModelWithObjectWrapper) {
				Object wrappedValue = ((ITableModelWithObjectWrapper) model).getWrappedValueAt(row, column);
				((JLabel) c).setText(wrappedValue != null ? wrappedValue.toString() : null);
			} else {
				((JLabel) c).setText(value != null ? value.toString() : null);
			}
		}
		return c;
	}
}
