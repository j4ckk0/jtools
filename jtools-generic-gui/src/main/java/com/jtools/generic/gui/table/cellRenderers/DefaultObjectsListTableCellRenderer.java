/**
 * 
 */
package com.jtools.generic.gui.table.cellRenderers;

import java.awt.Component;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;

import com.jtools.generic.gui.table.tableModels.ITableModelWithCellsCustomAlignment;
import com.jtools.utils.objects.ObjectUtils;

/**
 * @author j4ckk0
 *
 */
public class DefaultObjectsListTableCellRenderer extends DefaultTableCellRenderer {

	private static final long serialVersionUID = -2847050927447501258L;

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

		TableModel model = table.getModel();
		if(model instanceof ITableModelWithCellsCustomAlignment) {
			((JLabel) component).setHorizontalAlignment(((ITableModelWithCellsCustomAlignment)model).getCellHorizontalAlignment(row, column));
		}
		
		if(component instanceof JLabel) {
			if(value instanceof List) {
				((JLabel)component).setText(ObjectUtils.toString(((List<?>)value), ", "));
			}
		}
		return component;
	}
}
