package com.jtools.generic.gui.table.cellEditors;

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

import com.jtools.generic.gui.table.tableModels.ITableModelWithObjectWrapper;
import com.jtools.utils.objects.ObjectInfoProvider;

public class DefaultFieldTableCellEditor extends DefaultCellEditor {

	private static final long serialVersionUID = -1485761980760991949L;

	private FieldComboboxRenderer fieldComboboxRenderer;

	private ItemListener fieldComboboxListener;

	private static final Map<Class<?>, ComboBoxModel<Field>> possibleFieldsComboBoxModels = new HashMap<>();

	public DefaultFieldTableCellEditor(Class<?> objectClass) {
		super(new JComboBox<Field>());

		@SuppressWarnings("unchecked")
		JComboBox<Field> comboBox = (JComboBox<Field>) getComponent();
		if (objectClass != null) {
			setComboBoxModel(comboBox, objectClass);
		}
	}

	public DefaultFieldTableCellEditor() {
		super(new JComboBox<>());
	}

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
	 * 
	 * @author j4ckk0
	 *
	 */
	private static class FieldComboboxRenderer extends DefaultListCellRenderer {

		private static final long serialVersionUID = 1042572086292515370L;

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

	private class FieldComboboxListener implements ItemListener {

		private final JTable table;

		public FieldComboboxListener(JTable table) {
			this.table = table;
		}

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