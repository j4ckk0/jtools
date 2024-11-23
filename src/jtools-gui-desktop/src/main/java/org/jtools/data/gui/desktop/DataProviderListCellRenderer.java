package org.jtools.data.gui.desktop;

/*-
 * #%L
 * Java Tools - GUI - Desktop
 * %%
 * Copyright (C) 2024 jtools.org
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;

import org.jtools.data.provider.IDataProvider;
// TODO: Auto-generated Javadoc

/**
 * The Class DataProviderListCellRenderer.
 */
public class DataProviderListCellRenderer extends DefaultListCellRenderer {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 5381004978356081557L;

	/**
	 * Gets the list cell renderer component.
	 *
	 * @param list the list
	 * @param value the value
	 * @param index the index
	 * @param isSelected the is selected
	 * @param cellHasFocus the cell has focus
	 * @return the list cell renderer component
	 */
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
