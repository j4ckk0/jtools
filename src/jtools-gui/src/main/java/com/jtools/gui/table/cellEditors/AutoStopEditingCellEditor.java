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
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

/**
 * 
 * @author j4ckk0
 *
 */
public class AutoStopEditingCellEditor extends AbstractCellEditor implements TableCellEditor, FocusListener {

	private static final long serialVersionUID = 4720841926836417692L;

	private final transient TableCellEditor delegate;

	private boolean isFocusListenerInstalled = false;

	public AutoStopEditingCellEditor(TableCellEditor delegate) {
		this.delegate = delegate;
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		Component component = delegate.getTableCellEditorComponent(table, value, isSelected, row, column);
		if (!isFocusListenerInstalled) {
			component.addFocusListener(this);
			isFocusListenerInstalled = true;
		}
		return component;
	}

	@Override
	public Object getCellEditorValue() {
		return delegate.getCellEditorValue();
	}

	@Override
	public void focusGained(FocusEvent arg0) {
		// Nothing to do
	}

	@Override
	public void focusLost(FocusEvent arg0) {
		fireEditingStopped();
	}

}
