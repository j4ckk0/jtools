package com.jtools.generic.gui.table.cellRenderers;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class DefaultClassTableCellRenderer extends DefaultTableCellRenderer {

	private static final long serialVersionUID = 439493272034511576L;

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		if(c instanceof JLabel && value instanceof Class<?>) {
			((JLabel)c).setText(((Class<?>)value).getSimpleName());
		}
		return c;
	}
}