package com.jtools.generic.gui.table.cellRenderers;

import java.awt.Component;
import java.lang.reflect.Field;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class DefaultFieldTableCellRenderer extends DefaultTableCellRenderer {

	private static final long serialVersionUID = 439493272034511576L;

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		if(c instanceof JLabel && value instanceof Field) {
			((JLabel)c).setText(((Field)value).getName());
		}
		return c;
	}
}