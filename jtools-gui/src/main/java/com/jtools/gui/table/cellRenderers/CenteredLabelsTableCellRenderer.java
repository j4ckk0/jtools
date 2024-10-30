package com.jtools.gui.table.cellRenderers;

/*-
 * #%L
 * Java Tools - GUI
 * %%
 * Copyright (C) 2024 j4ckk0
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

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;

import com.jtools.gui.table.tableModels.ITableModelWithCellsCustomAlignment;
import com.jtools.gui.table.tableModels.ITableModelWithCellsCustomBackground;
import com.jtools.gui.table.tableModels.ITableModelWithMandatoryCells;

/**
 * 
 * @author j4ckk0
 *
 */
public class CenteredLabelsTableCellRenderer extends DefaultTableCellRenderer {

	private static final long serialVersionUID = 6089580429216896821L;

	private static Border redBorder = new LineBorder(Color.RED);

	private final DefaultTableCellRenderer delegate;

	public CenteredLabelsTableCellRenderer(DefaultTableCellRenderer delegate) {
		this.delegate = delegate;
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		Component c = delegate.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

		TableModel model = table.getModel();

		// Center label
		if (c instanceof JLabel) {
			if(model instanceof ITableModelWithCellsCustomAlignment) {
				((JLabel) c).setHorizontalAlignment(((ITableModelWithCellsCustomAlignment)model).getCellHorizontalAlignment(row, column));
			}
		}

		// Red border for mandatory fields
		if (c instanceof JComponent) {

			// If Cell is mandatory
			if(model instanceof ITableModelWithMandatoryCells) {
				if(((ITableModelWithMandatoryCells)model).isCellMandatory(row, column)) {
					if(value == null) {
						((JComponent)c).setBorder(redBorder);
					} else if (value instanceof String && ((String)value).length() == 0) {
						((JComponent)c).setBorder(redBorder);
					} else {
						if(table.getCellSelectionEnabled() || model.isCellEditable(row, column)) {
							((JComponent)c).setBorder(noFocusBorder);
						} else {
							((JComponent)c).setBorder(null);
						}
					}
				}
			}

			// Customized cell background
			if(model instanceof ITableModelWithCellsCustomBackground) {
				if(((ITableModelWithCellsCustomBackground)model).getCellBackground(row, column) != null) {
					((JComponent)c).setBackground(((ITableModelWithCellsCustomBackground)model).getCellBackground(row, column));
				} else {
					if(!model.isCellEditable(row, column)) {
						((JComponent)c).setBackground(UIManager.getColor("Panel.background"));
					} else {
						((JComponent)c).setBackground(null);
					}
				}
			}
		}

		return c;
	}
}
