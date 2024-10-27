package com.jtools.gui.table.cellEditors;

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