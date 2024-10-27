package com.jtools.gui.table.cellEditors;

import java.awt.Component;

import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableModel;

import com.jtools.gui.table.tableModels.ITableModelWithObjectWrapper;

/**
 * 
 */
public class ObjectWrapperCellEditor extends AbstractCellEditor implements TableCellEditor {

	private static final long serialVersionUID = 2558770580026849153L;

	private transient TableCellEditor delegate;

	@Override
	public Object getCellEditorValue() {
		return delegate.getCellEditorValue();
	}

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