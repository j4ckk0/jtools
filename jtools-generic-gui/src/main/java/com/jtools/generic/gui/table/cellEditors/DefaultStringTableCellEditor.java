package com.jtools.generic.gui.table.cellEditors;

import java.awt.Component;

import javax.swing.DefaultCellEditor;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableModel;

import com.jtools.generic.gui.table.tableModels.ITableModelWithCellsCustomAlignment;

/**
 * 
 * @author j4ckk0
 *
 */
public class DefaultStringTableCellEditor extends DefaultCellEditor {

	private static final long serialVersionUID = 9059530541068417901L;

	public DefaultStringTableCellEditor() {
		super(new JTextField());
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		Component component = super.getTableCellEditorComponent(table, value, isSelected, row, column);
		if(component instanceof JTextField) {
			TableModel model = table.getModel();
			if(model instanceof ITableModelWithCellsCustomAlignment) {
				((JTextField) component).setHorizontalAlignment(((ITableModelWithCellsCustomAlignment)model).getCellHorizontalAlignment(row, column));
			}
		}
		return component;
	}
}