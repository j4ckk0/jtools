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
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
// TODO: Auto-generated Javadoc

/**
 * The Class AutoStopEditingCellEditor.
 */
public class AutoStopEditingCellEditor extends AbstractCellEditor implements TableCellEditor, FocusListener {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 4720841926836417692L;

	/** The delegate. */
	private final transient TableCellEditor delegate;

	/** The is focus listener installed. */
	private boolean isFocusListenerInstalled = false;

	/**
	 * Instantiates a new auto stop editing cell editor.
	 *
	 * @param delegate the delegate
	 */
	public AutoStopEditingCellEditor(TableCellEditor delegate) {
		this.delegate = delegate;
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
		Component component = delegate.getTableCellEditorComponent(table, value, isSelected, row, column);
		if (!isFocusListenerInstalled) {
			component.addFocusListener(this);
			isFocusListenerInstalled = true;
		}
		return component;
	}

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
	 * Focus gained.
	 *
	 * @param arg0 the arg 0
	 */
	@Override
	public void focusGained(FocusEvent arg0) {
		// Nothing to do
	}

	/**
	 * Focus lost.
	 *
	 * @param arg0 the arg 0
	 */
	@Override
	public void focusLost(FocusEvent arg0) {
		fireEditingStopped();
	}

}
