package org.jtools.gui.table.utils;

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

import java.util.Date;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import org.jtools.gui.table.cellEditors.AutoStopEditingCellEditor;
import org.jtools.gui.table.cellEditors.DefaultDateTableCellEditor;
import org.jtools.gui.table.cellEditors.DefaultNumberTableCellEditor;
import org.jtools.gui.table.cellEditors.DefaultObjectTableCellEditor;
import org.jtools.gui.table.cellEditors.DefaultObjectsListTableCellEditor;
import org.jtools.gui.table.cellEditors.DefaultStringTableCellEditor;
import org.jtools.gui.table.cellEditors.ObjectWrapperCellEditor;
import org.jtools.gui.table.cellRenderers.CenteredLabelsTableCellRenderer;
import org.jtools.gui.table.cellRenderers.DefaultDateTableCellRenderer;
import org.jtools.gui.table.cellRenderers.DefaultObjectsListTableCellRenderer;
import org.jtools.gui.table.cellRenderers.ObjectWrapperCellRenderer;
import org.jtools.gui.table.tableModels.ITableModelWithObjectWrapper.ObjectWrapper;
// TODO: Auto-generated Javadoc

/**
 * The Class TableUtils.
 */
public class TableUtils {


	//////////////////////////////
	//
	// Constructors
	//
	//////////////////////////////

	/**
	 * Instantiates a new table utils.
	 */
	private TableUtils() {
	}

	//////////////////////////////
	//
	// Public methods
	//
	//////////////////////////////

	/**
	 * Install auto stop editing cell editors.
	 *
	 * @param table the table
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
	 * Install centered labels cell renderers.
	 *
	 * @param table the table
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
	 * Install default table cell editors.
	 *
	 * @param table the table
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
	 * Install default table cell renderers.
	 *
	 * @param table the table
	 */
	public static void installDefaultTableCellRenderers(JTable table) {

		// Objects
		table.setDefaultRenderer(Date.class, new DefaultDateTableCellRenderer());
		table.setDefaultRenderer(List.class, new DefaultObjectsListTableCellRenderer());
		table.setDefaultRenderer(ObjectWrapper.class, new ObjectWrapperCellRenderer());
	}

}
