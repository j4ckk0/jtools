/**
 * 
 */
package com.jtools.generic.data;

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

import com.jtools.generic.data.io.DataFileManager;
import com.jtools.generic.data.provider.DataProviderPubSubTopics;
import com.jtools.generic.data.provider.DataProviderRegistry;
import com.jtools.generic.data.provider.IDataProvider;
import com.jtools.generic.gui.table.ObjectsTable;
import com.jtools.utils.CommonUtils;
import com.jtools.utils.gui.editor.AEditor;
import com.jtools.utils.messages.pubsub.DefaultPubSubBus;

/**
 * @author j4ckk0
 *
 */
public class DataEditor extends AEditor implements ItemListener, IDataProvider {

	// //////////////////////////////
	//
	// Attributes and constants
	//
	// //////////////////////////////

	private static final long serialVersionUID = -1428948320263663684L;


	private final Map<Class<?>, ObjectsTable<?>> objectsTableMap;

	private final JScrollPane objectsTableScrollPane;

	private final JComboBox<Class<?>> objectTypeCombobox;

	private final List<Class<?>> objectClasses;

	private Class<?> selectedObjectClass;

	// //////////////////////////////
	//
	// Methods
	//
	// //////////////////////////////

	public DataEditor(Class<?>... objectClasses) {
		this(null, objectClasses);
	}

	/**
	 * 
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
		
		// Register the DataEditor to the DataProvider registry
		DataProviderRegistry.instance().register(this);
	}

	public ObjectsTable<?> getObjectsTable() {
		return getObjectsTable(getDataClass());
	}

	// //////////////////////////////
	//
	// AEditor methods
	//
	// //////////////////////////////

	@Override
	protected String getEditorName() {
		return "Data table " + hashCode();
	}

	@Override
	protected void onWindowOpened() {
		DefaultPubSubBus.instance().sendTextMessage(DataProviderPubSubTopics.DATA_PROVIDER_ADDED, DataEditor.this.getProviderName());
	}

	@Override
	protected void onWindowClosed() {
		DefaultPubSubBus.instance().sendTextMessage(DataProviderPubSubTopics.DATA_PROVIDER_REMOVED, DataEditor.this.getProviderName());
	}

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
	
	@Override
	public String getProviderName() {
		return getEditorName();
	}

	@Override
	public Class<?> getDataClass() {
		return selectedObjectClass;
	}

	@Override
	public Map<Class<?>, List<?>> getDataMap() {
		Map<Class<?>, List<?>> data = new HashMap<>();
		for (Entry<Class<?>, ObjectsTable<?>> dataEntry : objectsTableMap.entrySet()) {
			data.put(dataEntry.getKey(), dataEntry.getValue().getDataList());
		}
		return data;
	}

	@Override
	public List<?> getDataList() {
		return getDataList(getDataClass());
	}

	@Override
	public List<Class<?>> getPossibleDataClasses() {
		return Collections.unmodifiableList(objectClasses);
	}

	// //////////////////////////////
	//
	// Private methods
	//
	// //////////////////////////////

	@SuppressWarnings("unchecked")
	private <T> ObjectsTable<T> getObjectsTable(Class<T> objectClass) {
		ObjectsTable<T> objectsTable = (ObjectsTable<T>) objectsTableMap.get(objectClass);
		if (objectsTable == null) {
			objectsTable = new ObjectsTable<>(objectClass);
			objectsTableMap.put(objectClass, objectsTable);
		}
		return objectsTable;
	}

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

	protected static class ObjectClassComboboxRenderer extends DefaultListCellRenderer {

		private static final long serialVersionUID = -6462779953123291622L;

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
