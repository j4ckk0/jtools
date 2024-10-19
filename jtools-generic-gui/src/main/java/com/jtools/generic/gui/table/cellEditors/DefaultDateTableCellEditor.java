package com.jtools.generic.gui.table.cellEditors;

import java.awt.Component;
import java.util.Date;

import javax.swing.DefaultCellEditor;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableModel;

import com.jtools.generic.gui.table.tableModels.ITableModelWithCellsCustomAlignment;
import com.jtools.utils.dates.DateFormatManager;

/**
 * 
 * @author j4ckk0
 *
 */
public class DefaultDateTableCellEditor extends DefaultCellEditor {

	private static final long serialVersionUID = 4298535018758190505L;

	public DefaultDateTableCellEditor() {
		super(new JTextField());
	}

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

	@Override
	public Date getCellEditorValue() {
		return DateFormatManager.instance().parse((String)super.getCellEditorValue());
	}

}