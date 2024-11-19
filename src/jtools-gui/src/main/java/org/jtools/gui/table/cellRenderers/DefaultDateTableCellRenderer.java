package org.jtools.gui.table.cellRenderers;

/*-
 * #%L
 * Java Tools - GUI
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

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;

import org.jtools.gui.table.tableModels.ITableModelWithCellsCustomAlignment;
import org.jtools.utils.dates.DateFormatManager;
public class DefaultDateTableCellRenderer extends DefaultTableCellRenderer {

	private Font defaultFont = null;
	private Color defaultForeground = null;
	private Font italicFont = null;

	private static final long serialVersionUID = 3612223582479646873L;

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {

		Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		if (component instanceof JLabel) {
			TableModel model = table.getModel();
			if(model instanceof ITableModelWithCellsCustomAlignment) {
				((JLabel) component).setHorizontalAlignment(((ITableModelWithCellsCustomAlignment)model).getCellHorizontalAlignment(row, column));
			}

			if(defaultForeground == null) {
				defaultForeground = ((JLabel)component).getForeground();
			}

			if(value instanceof Date) {
				((JLabel)component).setText(DateFormatManager.instance().format((Date)value));
				((JLabel)component).setForeground(defaultForeground);

				if(defaultFont == null) {
					defaultFont = new Font(((JLabel)component).getFont().getName(),Font.PLAIN,((JLabel)component).getFont().getSize());
				}
				((JLabel)component).setFont(defaultFont);
			}
			if(value == null && model.isCellEditable(row, column)) {
				((JLabel)component).setText(DateFormatManager.instance().getPattern());
				((JLabel)component).setForeground(Color.GRAY);

				if(italicFont == null) {
					italicFont = new Font(((JLabel)component).getFont().getName(),Font.ITALIC,((JLabel)component).getFont().getSize());
				}
				((JLabel)component).setFont(italicFont);
			}
		}

		return component;
	}
}
