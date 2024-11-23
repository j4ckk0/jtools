package org.jtools.mappings.editors.block;

/*-
 * #%L
 * Java Tools - Mappings Editors
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

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import org.jtools.mappings.block.BlockMapping;
// TODO: Auto-generated Javadoc

/**
 * The Class BlockMappingTableCellRenderer.
 */
class BlockMappingTableCellRenderer extends DefaultTableCellRenderer {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -4462402854446992691L;

	/**
	 * Gets the table cell renderer component.
	 *
	 * @param table the table
	 * @param value the value
	 * @param isSelected the is selected
	 * @param hasFocus the has focus
	 * @param row the row
	 * @param column the column
	 * @return the table cell renderer component
	 */
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
