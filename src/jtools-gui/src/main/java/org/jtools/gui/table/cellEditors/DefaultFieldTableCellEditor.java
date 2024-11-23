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
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.table.TableModel;

import org.jtools.gui.table.tableModels.ITableModelWithObjectWrapper;
import org.jtools.utils.objects.ObjectInfoProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class DefaultFieldTableCellEditor.
 */
public class DefaultFieldTableCellEditor extends DefaultCellEditor {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -1485761980760991949L;

	/** The field combobox renderer. */
	private FieldComboboxRenderer fieldComboboxRenderer;

	/** The field combobox listener. */
	private ItemListener fieldComboboxListener;

	/** The Constant possibleFieldsComboBoxModels. */
	private static final Map<Class<?>, ComboBoxModel<Field>> possibleFieldsComboBoxModels = new HashMap<>();

	/**
	 * Instantiates a new default field table cell editor.
	 *
	 * @param objectClass the object class
	 */
	public DefaultFieldTableCellEditor(Class<?> objectClass) {
		super(new JComboBox<Field>());

		@SuppressWarnings("unchecked")
		JComboBox<Field> comboBox = (JComboBox<Field>) getComponent();
		if (objectClass != null) {
			setComboBoxModel(comboBox, objectClass);
		}
	}

	/**
	 * Instantiates a new default field table cell editor.
	 */
	public DefaultFieldTableCellEditor() {
		super(new JComboBox<>());
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
		@SuppressWarnings("unchecked")
		JComboBox<Field> comboBox = (JComboBox<Field>) super.getTableCellEditorComponent(table, value, isSelected, row, column);

		if(fieldComboboxListener == null) {
			fieldComboboxListener = new FieldComboboxListener(table);
			comboBox.addItemListener(fieldComboboxListener);
		}

		if (fieldComboboxRenderer == null) {
			fieldComboboxRenderer = new FieldComboboxRenderer();
			comboBox.setRenderer(fieldComboboxRenderer);
		}

		TableModel model = table.getModel();
		if (model instanceof ITableModelWithObjectWrapper) {
			Class<?> objectClass = ((ITableModelWithObjectWrapper) model).getWrappedClassAt(row, column);

			if (objectClass != null) {
				setComboBoxModel(comboBox, objectClass);
			}
		}

		return comboBox;
	}

	/**
	 * Sets the combo box model.
	 *
	 * @param comboBox the combo box
	 * @param objectClass the object class
	 */
	private void setComboBoxModel(JComboBox<Field> comboBox, Class<?> objectClass) {
		ComboBoxModel<Field> comboBoxModel = possibleFieldsComboBoxModels.get(objectClass);
		if (comboBoxModel == null) {
			List<Field> possibleFields = ObjectInfoProvider.getObjectInfo(objectClass).getPossibleFields();
			Field[] possibleFieldsArray = new Field[possibleFields.size()];
			possibleFields.toArray(possibleFieldsArray);
			comboBoxModel = new DefaultComboBoxModel<Field>(possibleFieldsArray);
			possibleFieldsComboBoxModels.put(objectClass, comboBoxModel);
		}
		comboBox.setModel(comboBoxModel);
	}
	
	/**
	 * The Class FieldComboboxRenderer.
	 */
	private static class FieldComboboxRenderer extends DefaultListCellRenderer {

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
			if (c instanceof JLabel && value instanceof Field) {
				((JLabel) c).setText(((Field) value).getName());
			}
			return c;
		}
	}

	/**
	 * The listener interface for receiving fieldCombobox events.
	 * The class that is interested in processing a fieldCombobox
	 * event implements this interface, and the object created
	 * with that class is registered with a component using the
	 * component's <code>addFieldComboboxListener</code> method. When
	 * the fieldCombobox event occurs, that object's appropriate
	 * method is invoked.
	 *
	 * @see FieldComboboxEvent
	 */
	private class FieldComboboxListener implements ItemListener {

		/** The table. */
		private final JTable table;

		/**
		 * Instantiates a new field combobox listener.
		 *
		 * @param table the table
		 */
		public FieldComboboxListener(JTable table) {
			this.table = table;
		}

		/**
		 * Item state changed.
		 *
		 * @param e the e
		 */
		@Override
		public void itemStateChanged(ItemEvent e) {
			table.editingStopped(new ChangeEvent(DefaultFieldTableCellEditor.this));
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					table.updateUI();
				}
			});
		}


	}
}
