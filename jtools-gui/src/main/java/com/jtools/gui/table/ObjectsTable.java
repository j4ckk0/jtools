/**
 * 
 */
package com.jtools.gui.table;

import java.awt.event.ActionEvent;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JTable;

import com.jtools.gui.table.tableModels.IObjectRow;
import com.jtools.gui.table.tableModels.ObjectsTableModel;
import com.jtools.gui.table.tableModels.IObjectRow.NewRow;
import com.jtools.gui.table.tableModels.IObjectRow.ObjectRow;
import com.jtools.gui.table.utils.TableUtils;
import com.jtools.utils.gui.components.ButtonColumn;

/**
 * @author j4ckk0
 *
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class ObjectsTable<E extends Object> extends JTable {

	private static final long serialVersionUID = -4774247812741796742L;

	protected final Class<E> objectClass;

	/**
	 * 
	 * @param mapper
	 * @param rows
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

	public List<IObjectRow> getRows() {
		return ((ObjectsTableModel) getModel()).getRows();
	}

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
	
	public Class<E> getDataClass() {
		return objectClass;
	}
	
	public void insertRow(E object) {
		ObjectsTableModel model = (ObjectsTableModel) getModel();
		model.insertRow(new ObjectRow<E>(object));
		model.fireTableRowsInserted(model.getRowCount() - 1 , model.getRowCount() - 1);
	}

	/**
	 * 
	 * @author j4ckk0
	 *
	 */
	private class AddRemoveButtonColumn extends ButtonColumn {

		private static final long serialVersionUID = -6435845128537729287L;

		private JTable table;

		public AddRemoveButtonColumn(JTable table, int column) {
			super(table, null, column);
			this.table = table;
		}

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
