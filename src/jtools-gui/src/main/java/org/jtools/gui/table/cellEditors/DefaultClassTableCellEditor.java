package org.jtools.gui.table.cellEditors;

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

import java.awt.Component;
import java.util.List;

import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTable;

// TODO: Auto-generated Javadoc
/**
 * The Class DefaultClassTableCellEditor.
 */
public class DefaultClassTableCellEditor extends DefaultCellEditor {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -1485761980760991949L;

	/** The class combobox renderer. */
	private ClassComboboxRenderer classComboboxRenderer;

	/**
	 * Instantiates a new default class table cell editor.
	 *
	 * @param possibleObjectsClasses the possible objects classes
	 */
	public DefaultClassTableCellEditor(List<Class<?>> possibleObjectsClasses) {
		super(new JComboBox<>(new DefaultComboBoxModel<>(possibleObjectsClasses.toArray())));
	}

	/**
	 * Gets the table cell editor component.
	 *
	 * @param table the table
	 * @param value the value
	 * @param isSelected the is selected
	 * @param row the row
	 * @param column the column
	 * @return the table cell editor component
	 */
	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		JComboBox<?> comboBox = (JComboBox<?>) super.getTableCellEditorComponent(table, value, isSelected, row, column);

		if (classComboboxRenderer == null) {
			classComboboxRenderer = new ClassComboboxRenderer();
			comboBox.setRenderer(classComboboxRenderer);
		}

		return comboBox;
	}
	
	/**
	 * The Class ClassComboboxRenderer.
	 */
	private static class ClassComboboxRenderer extends DefaultListCellRenderer {

		/** The Constant serialVersionUID. */
		private static final long serialVersionUID = 1042572086292515370L;

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
			if (c instanceof JLabel && value instanceof Class<?>) {
				((JLabel) c).setText(((Class<?>) value).getSimpleName());
			}
			return c;
		}
	}
}
