package com.jtools.data.gui.desktop;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;

import com.jtools.data.provider.IDataProvider;

/**
 * @author j4ckk0
 *
 */
public class DataProviderListCellRenderer extends DefaultListCellRenderer {

	private static final long serialVersionUID = 5381004978356081557L;

	@Override
	public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
			boolean cellHasFocus) {
		Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
		if (c instanceof JLabel && value instanceof IDataProvider) {
			((JLabel) c).setText(((IDataProvider) value).getProviderName());
		}
		return c;
	}

}