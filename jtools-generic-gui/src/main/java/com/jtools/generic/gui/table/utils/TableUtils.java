/**
 * 
 */
package com.jtools.generic.gui.table.utils;

import java.util.Date;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import com.jtools.generic.gui.table.cellEditors.AutoStopEditingCellEditor;
import com.jtools.generic.gui.table.cellEditors.DefaultDateTableCellEditor;
import com.jtools.generic.gui.table.cellEditors.DefaultNumberTableCellEditor;
import com.jtools.generic.gui.table.cellEditors.DefaultObjectTableCellEditor;
import com.jtools.generic.gui.table.cellEditors.DefaultObjectsListTableCellEditor;
import com.jtools.generic.gui.table.cellEditors.DefaultStringTableCellEditor;
import com.jtools.generic.gui.table.cellEditors.ObjectWrapperCellEditor;
import com.jtools.generic.gui.table.cellRenderers.CenteredLabelsTableCellRenderer;
import com.jtools.generic.gui.table.cellRenderers.DefaultDateTableCellRenderer;
import com.jtools.generic.gui.table.cellRenderers.DefaultObjectsListTableCellRenderer;
import com.jtools.generic.gui.table.cellRenderers.ObjectWrapperCellRenderer;
import com.jtools.generic.gui.table.tableModels.ITableModelWithObjectWrapper.ObjectWrapper;

/**
 * @author j4ckk0
 *
 */
public class TableUtils {
	
	//////////////////////////////
	//
	// Public methods
	//
	//////////////////////////////

	/**
	 * 
	 * @param table
	 */
	public static void installAutoStopEditingCellEditors(JTable table) {

		TableColumnModel columnModel = table.getColumnModel();

		for (int i = 0; i < columnModel.getColumnCount(); i++) {
			TableColumn column = columnModel.getColumn(i);

			TableCellEditor cellEditor = column.getCellEditor();
			if (cellEditor == null) {
				TableModel model = table.getModel();
				cellEditor = table.getDefaultEditor(model.getColumnClass(i));
			}

			if (cellEditor instanceof TableCellEditor) {
				column.setCellEditor(new AutoStopEditingCellEditor((TableCellEditor) cellEditor));
			}
		}

	}

	/**
	 * 
	 * @param table
	 */
	public static void installCenteredLabelsCellRenderers(JTable table) {

		TableColumnModel columnModel = table.getColumnModel();

		for (int i = 0; i < columnModel.getColumnCount(); i++) {
			TableColumn column = columnModel.getColumn(i);

			TableCellRenderer cellRenderer = column.getCellRenderer();
			if (cellRenderer == null) {
				TableModel model = table.getModel();
				cellRenderer = table.getDefaultRenderer(model.getColumnClass(i));
			}

			if (cellRenderer instanceof DefaultTableCellRenderer) {
				column.setCellRenderer(new CenteredLabelsTableCellRenderer((DefaultTableCellRenderer) cellRenderer));
			}
		}

	}

	/**
	 * 
	 * @param table
	 */
	public static void installDefaultTableCellEditors(JTable table) {

		// Objects
		table.setDefaultEditor(String.class, new DefaultStringTableCellEditor());
		table.setDefaultEditor(Date.class, new DefaultDateTableCellEditor());
		table.setDefaultEditor(Object.class, new DefaultObjectTableCellEditor());
		table.setDefaultEditor(List.class, new DefaultObjectsListTableCellEditor());
		table.setDefaultEditor(ObjectWrapper.class, new ObjectWrapperCellEditor());

		// Primitive types
		TableColumnModel columnModel = table.getColumnModel();

		for (int i = 0; i < columnModel.getColumnCount(); i++) {
			TableColumn column = columnModel.getColumn(i);

			TableCellEditor cellEditor = column.getCellEditor();
			if (cellEditor == null) {
				TableModel model = table.getModel();

				// Cases ... to be completed
				Class<?> columnClass = model.getColumnClass(i);
				if (DefaultNumberTableCellEditor.isAssignableFrom(columnClass)) {
					cellEditor = DefaultNumberTableCellEditor.getEditorForColumnClass(columnClass);
				}

				// Default
				if (cellEditor == null) {
					cellEditor = table.getDefaultEditor(columnClass);
				}

				// Set cell editor
				column.setCellEditor(cellEditor);
			}
		}
	}

	/**
	 * 
	 * @param table
	 */
	public static void installDefaultTableCellRenderers(JTable table) {

		// Objects
		table.setDefaultRenderer(Date.class, new DefaultDateTableCellRenderer());
		table.setDefaultRenderer(List.class, new DefaultObjectsListTableCellRenderer());
		table.setDefaultRenderer(ObjectWrapper.class, new ObjectWrapperCellRenderer());
	}

}
