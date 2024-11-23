package org.jtools.gui.table;

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

import java.awt.event.ActionEvent;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JTable;

import org.jtools.gui.table.tableModels.IObjectRow;
import org.jtools.gui.table.tableModels.IObjectRow.NewRow;
import org.jtools.gui.table.tableModels.IObjectRow.ObjectRow;
import org.jtools.gui.table.tableModels.ObjectsTableModel;
import org.jtools.gui.table.utils.TableUtils;
import org.jtools.utils.gui.components.ButtonColumn;
// TODO: Auto-generated Javadoc

/**
 * The Class ObjectsTable.
 *
 * @param <E> the element type
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class ObjectsTable<E extends Object> extends JTable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -4774247812741796742L;

	/** The object class. */
	protected final Class<E> objectClass;
	
	/**
	 * Instantiates a new objects table.
	 *
	 * @param objectClass the object class
	 */
	public ObjectsTable(Class<E> objectClass) {
		this.objectClass = objectClass;

		List<IObjectRow> rows = new ArrayList<>();
		rows.add(new NewRow());

		ObjectsTableModel model = new ObjectsTableModel(objectClass, rows);
		setModel(model);

		setShowGrid(false);
		setRowSelectionAllowed(false);
		setCellSelectionEnabled(false);
		setRowHeight(20);

		new AddRemoveButtonColumn(this, ObjectsTableModel.ADD_REMOVE_BUTTON_COL);
		getColumnModel().getColumn(ObjectsTableModel.ADD_REMOVE_BUTTON_COL).setPreferredWidth(100);
		
		TableUtils.installDefaultTableCellRenderers(this);
		TableUtils.installCenteredLabelsCellRenderers(this);
		
		TableUtils.installDefaultTableCellEditors(this);
		TableUtils.installAutoStopEditingCellEditors(this);
	}

	/**
	 * Gets the rows.
	 *
	 * @return the rows
	 */
	public List<IObjectRow> getRows() {
		return ((ObjectsTableModel) getModel()).getRows();
	}

	/**
	 * Gets the data list.
	 *
	 * @return the data list
	 */
	public List<E> getDataList() {
		List<IObjectRow> rows = getRows();

		List<E> data = new ArrayList<>();
		for (IObjectRow row : rows) {
			if (row instanceof ObjectRow) {
				data.add(((ObjectRow<E>)row).getObject());
			}
		}

		return data;
	}
	
	/**
	 * Gets the data class.
	 *
	 * @return the data class
	 */
	public Class<E> getDataClass() {
		return objectClass;
	}
	
	/**
	 * Insert row.
	 *
	 * @param object the object
	 */
	public void insertRow(E object) {
		ObjectsTableModel model = (ObjectsTableModel) getModel();
		model.insertRow(new ObjectRow<E>(object));
		model.fireTableRowsInserted(model.getRowCount() - 1 , model.getRowCount() - 1);
	}
	
	/**
	 * The Class AddRemoveButtonColumn.
	 */
	private class AddRemoveButtonColumn extends ButtonColumn {

		/** The Constant serialVersionUID. */
		private static final long serialVersionUID = -6435845128537729287L;

		/** The table. */
		private JTable table;

		/**
		 * Instantiates a new adds the remove button column.
		 *
		 * @param table the table
		 * @param column the column
		 */
		public AddRemoveButtonColumn(JTable table, int column) {
			super(table, null, column);
			this.table = table;
		}

		/**
		 * Action performed.
		 *
		 * @param event the event
		 */
		@Override
		public void actionPerformed(ActionEvent event) {
			ObjectsTableModel model = (ObjectsTableModel) table.getModel();
			int row = table.convertRowIndexToModel(table.getEditingRow());

			IObjectRow objectRow = model.getRow(row);
			if (objectRow instanceof NewRow) {
				try {
					E newInstance = objectClass.getDeclaredConstructor().newInstance();
					model.addRow(new ObjectRow<E>(newInstance));
				} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
						| InvocationTargetException | NoSuchMethodException | SecurityException e) {
					Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage());
					Logger.getLogger(getClass().getName()).log(Level.FINE, e.getMessage(), e);
				}
			} else {
				model.removeRow(row);
			}
			
			// Lose focus and stop editing
			table.requestFocusInWindow();
			fireEditingStopped();
		}

	}
}
