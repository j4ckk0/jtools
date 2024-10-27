/**
 * 
 */
package com.jtools.gui.list.cellRenderers;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;

/**
 * @author j4ckk0
 *
 */
public class DefaultClassListCellRenderer extends DefaultListCellRenderer {

	private static final long serialVersionUID = -6782564778984334994L;

	@Override
	public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
			boolean cellHasFocus) {
		Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
		if (c instanceof JLabel && value instanceof Class<?>) {
			String className = ((Class<?>) value).getCanonicalName();
			((JLabel) c).setText(className);
		}
		return c;
	}

}
