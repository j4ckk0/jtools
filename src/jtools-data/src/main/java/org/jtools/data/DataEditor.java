package org.jtools.data;

/*-
 * #%L
 * Java Tools - Data
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

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.jtools.data.io.DataFileManager;
import org.jtools.data.provider.DataProviderRegistry;
import org.jtools.data.provider.IDataProvider;
import org.jtools.gui.table.ObjectsTable;
import org.jtools.utils.CommonUtils;
import org.jtools.utils.gui.editor.AEditor;
// TODO: Auto-generated Javadoc

/**
 * The Class DataEditor.
 */
public class DataEditor extends AEditor implements ItemListener, IDataProvider {

	// //////////////////////////////
	//
	// Attributes and constants
	//
	// //////////////////////////////

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -1428948320263663684L;


	/** The objects table map. */
	private final Map<Class<?>, ObjectsTable<?>> objectsTableMap;

	/** The objects table scroll pane. */
	private final JScrollPane objectsTableScrollPane;

	/** The object type combobox. */
	private final JComboBox<Class<?>> objectTypeCombobox;

	/** The object classes. */
	private final List<Class<?>> objectClasses;

	/** The selected object class. */
	private Class<?> selectedObjectClass;

	// //////////////////////////////
	//
	// Methods
	//
	// //////////////////////////////

	/**
	 * Instantiates a new data editor.
	 *
	 * @param objectClasses the object classes
	 */
	public DataEditor(Class<?>... objectClasses) {
		this(null, objectClasses);
	}

	/**
	 * Instantiates a new data editor.
	 *
	 * @param dataList the data list
	 * @param objectsClasses the objects classes
	 */
	public DataEditor(List<?> dataList, Class<?>... objectsClasses) {

		this.objectsTableMap = new HashMap<>();

		this.objectClasses = getObjectsClasses(dataList, objectsClasses);

		if (dataList != null) {
			// Reverse loop on dataList as we do an "insert row" (because of the "+" row)
			for(int i = dataList.size() - 1; i >= 0; i--) {
				insertData(dataList.get(i));
			}
		}

		//
		// Objects table
		//
		this.objectsTableScrollPane = new JScrollPane();

		//
		// Object type panel
		//

		// Build combo box
		this.objectTypeCombobox = new JComboBox<>();
		objectTypeCombobox.setModel(new DefaultComboBoxModel<>(CommonUtils.classListToArray(objectClasses)));
		objectTypeCombobox.setRenderer(new ObjectClassComboboxRenderer());
		objectTypeCombobox.addItemListener(this);
		objectTypeCombobox.setSelectedIndex(0);
		itemStateChanged(null);

		// build object type panel
		JLabel objectTypeLabel = new JLabel("Object type:");
		JPanel objectTypePanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
		objectTypePanel.add(objectTypeLabel);
		objectTypePanel.add(objectTypeCombobox);

		// Build panel
		setLayout(new BorderLayout(6, 6));
		add(objectTypePanel, BorderLayout.NORTH);
		add(objectsTableScrollPane, BorderLayout.CENTER);
	}

	/**
	 * Gets the objects table.
	 *
	 * @return the objects table
	 */
	public ObjectsTable<?> getObjectsTable() {
		return getObjectsTable(getDataClass());
	}

	// //////////////////////////////
	//
	// AEditor methods
	//
	// //////////////////////////////

	/**
	 * Gets the editor name.
	 *
	 * @return the editor name
	 */
	@Override
	protected String getEditorName() {
		return "Data table " + hashCode();
	}

	/**
	 * On window opened.
	 */
	@Override
	protected void onWindowOpened() {
		DataProviderRegistry.instance().register(DataEditor.this);
	}

	/**
	 * On window closed.
	 */
	@Override
	protected void onWindowClosed() {
		DataProviderRegistry.instance().unregister(DataEditor.this);
	}

	/**
	 * Save.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Override
	protected void save() throws IOException {
		Map<Class<?>, List<?>> dataMap = getDataMap();

		List<Object> dataList = new ArrayList<>();

		Collection<List<?>> values = dataMap.values();
		for (List<?> value : values) {
			dataList.addAll(value);
		}

		DataFileManager.instance().save(dataList);
	}

	// //////////////////////////////
	//
	// ItemListener methods
	//
	// //////////////////////////////

	/**
	 * Item state changed.
	 *
	 * @param e the e
	 */
	@Override
	public void itemStateChanged(ItemEvent e) {
		Object item = objectTypeCombobox.getSelectedItem();
		if (item instanceof Class) {
			this.selectedObjectClass = (Class<?>) item;

			ObjectsTable<?> objectTable = getObjectsTable(selectedObjectClass);
			objectsTableScrollPane.setViewportView(objectTable);
		}
	}

	// //////////////////////////////
	//
	// IDataProvider methods
	//
	// //////////////////////////////

	/**
	 * Gets the provider name.
	 *
	 * @return the provider name
	 */
	@Override
	public String getProviderName() {
		return getEditorName();
	}

	/**
	 * Gets the data class.
	 *
	 * @return the data class
	 */
	@Override
	public Class<?> getDataClass() {
		return selectedObjectClass;
	}

	/**
	 * Gets the data map.
	 *
	 * @return the data map
	 */
	@Override
	public Map<Class<?>, List<?>> getDataMap() {
		Map<Class<?>, List<?>> data = new HashMap<>();
		for (Entry<Class<?>, ObjectsTable<?>> dataEntry : objectsTableMap.entrySet()) {
			data.put(dataEntry.getKey(), dataEntry.getValue().getDataList());
		}
		return data;
	}

	/**
	 * Gets the data list.
	 *
	 * @return the data list
	 */
	@Override
	public List<?> getDataList() {
		return getDataList(getDataClass());
	}

	/**
	 * Gets the possible data classes.
	 *
	 * @return the possible data classes
	 */
	@Override
	public List<Class<?>> getPossibleDataClasses() {
		return Collections.unmodifiableList(objectClasses);
	}

	// //////////////////////////////
	//
	// Private methods
	//
	// //////////////////////////////

	/**
	 * Gets the objects table.
	 *
	 * @param <T> the generic type
	 * @param objectClass the object class
	 * @return the objects table
	 */
	@SuppressWarnings("unchecked")
	private <T> ObjectsTable<T> getObjectsTable(Class<T> objectClass) {
		ObjectsTable<T> objectsTable = (ObjectsTable<T>) objectsTableMap.get(objectClass);
		if (objectsTable == null) {
			objectsTable = new ObjectsTable<>(objectClass);
			objectsTableMap.put(objectClass, objectsTable);
		}
		return objectsTable;
	}

	/**
	 * Gets the objects classes.
	 *
	 * @param dataList the data list
	 * @param additionalObjectsClasses the additional objects classes
	 * @return the objects classes
	 */
	private List<Class<?>> getObjectsClasses(List<?> dataList, Class<?>[] additionalObjectsClasses) {
		List<Class<?>> objectsClasses = new ArrayList<>();

		// From data list
		if (dataList != null) {
			for (Object data : dataList) {
				if (!objectsClasses.contains(data.getClass())) {
					objectsClasses.add(data.getClass());
				}
			}
		}

		// From additional objects classes
		for (Class<?> objectClass : additionalObjectsClasses) {
			if (!objectsClasses.contains(objectClass)) {
				objectsClasses.add(objectClass);
			}
		}

		return objectsClasses;
	}

	/**
	 * Insert data.
	 *
	 * @param <T> the generic type
	 * @param data the data
	 */
	@SuppressWarnings("unchecked")
	private <T> void insertData(T data) {
		ObjectsTable<T> objectsTable = getObjectsTable((Class<T>) data.getClass());
		objectsTable.insertRow(data);
	}

	// //////////////////////////////
	//
	// Nested classes
	//
	// //////////////////////////////

	/**
	 * The Class ObjectClassComboboxRenderer.
	 */
	protected static class ObjectClassComboboxRenderer extends DefaultListCellRenderer {

		/** The Constant serialVersionUID. */
		private static final long serialVersionUID = -6462779953123291622L;

		/**
		 * Instantiates a new object class combobox renderer.
		 */
		public ObjectClassComboboxRenderer() {
			super();
		}

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
			Component component = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
			if (component instanceof JLabel && value instanceof Class) {
				((JLabel) component).setText(((Class<?>) value).getSimpleName());
			}
			return component;
		}
	}

}
