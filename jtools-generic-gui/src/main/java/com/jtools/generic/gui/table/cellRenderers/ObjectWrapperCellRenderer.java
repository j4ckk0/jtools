/**
 * 
 */
package com.jtools.generic.gui.table.cellRenderers;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;

import com.jtools.generic.gui.table.tableModels.ITableModelWithObjectWrapper;
import com.jtools.generic.gui.table.tableModels.ITableModelWithObjectWrapper.ObjectWrapper;

/**
 * @author j4ckk0
 *
 */
public class ObjectWrapperCellRenderer extends DefaultTableCellRenderer {

	private static final long serialVersionUID = 693240225989571061L;

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

		if(c instanceof JLabel) {
			TableModel model = table.getModel();
			if(value instanceof ObjectWrapper && model instanceof ITableModelWithObjectWrapper) {
				Object wrappedValue = ((ITableModelWithObjectWrapper)model).getWrappedValueAt(row, column);
				((JLabel)c).setText(wrappedValue != null ? wrappedValue.toString() : null);
			} else {
				((JLabel)c).setText(value != null ? value.toString() : null);
			}
		}
		return c;
	}
}
