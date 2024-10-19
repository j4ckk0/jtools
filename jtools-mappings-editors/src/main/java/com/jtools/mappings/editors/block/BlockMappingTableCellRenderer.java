package com.jtools.mappings.editors.block;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import com.jtools.mappings.block.BlockMapping;

/**
 * @author j4ckk0
 *
 */
class BlockMappingTableCellRenderer extends DefaultTableCellRenderer {

	private static final long serialVersionUID = -4462402854446992691L;

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		if (c instanceof JLabel) {
			if(value instanceof BlockMapping) {
				((JLabel) c).setText(((BlockMapping<?>) value).getObjectClass().getSimpleName());
			}
		}
		return c;
	}

}