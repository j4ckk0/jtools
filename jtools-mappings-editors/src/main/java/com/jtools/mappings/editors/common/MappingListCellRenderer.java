package com.jtools.mappings.editors.common;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;

import com.jtools.mappings.common.IMapping;

public class MappingListCellRenderer extends DefaultListCellRenderer {

	private static final long serialVersionUID = -7027193234153254198L;

	@Override
	public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
			boolean cellHasFocus) {
		Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
		if (c instanceof JLabel && value instanceof IMapping) {
			((JLabel) c).setText(((IMapping) value).getMappingName());
		}
		return c;
	}

}