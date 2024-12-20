package org.jtools.mappings.editors.simple;

/*-
 * #%L
 * Java Tools - Mappings Editors
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

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JTextField;

import org.jtools.gui.table.cellEditors.DefaultFieldTableCellEditor;
import org.jtools.gui.table.cellRenderers.DefaultFieldTableCellRenderer;
import org.jtools.gui.table.utils.TableUtils;
import org.jtools.mappings.common.MappingUtils;
import org.jtools.mappings.editors.simple.SimpleMappingEditorRow.SimpleMappingRowType;
import org.jtools.mappings.simple.SimpleMapping;
import org.jtools.utils.gui.components.ButtonColumn;
// TODO: Auto-generated Javadoc

/**
 * The Class SimpleMappingEditorTable.
 */
public class SimpleMappingEditorTable extends JTable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -4774247812741796742L;
	
	/**
	 * Instantiates a new simple mapping editor table.
	 *
	 * @param mapping the mapping
	 */
	public SimpleMappingEditorTable(SimpleMapping<?> mapping) {

		SimpleMappingEditorTableModel model = new SimpleMappingEditorTableModel(mapping);
		setModel(model);

		setShowGrid(false);
		setRowSelectionAllowed(false);
		setCellSelectionEnabled(false);
		setRowHeight(20);

		new AddRemoveButtonColumn(this, SimpleMappingEditorColumn.ADD_REMOVE_BUTTON_COL.getColumnIndex());
		getColumnModel().getColumn(SimpleMappingEditorColumn.ADD_REMOVE_BUTTON_COL.getColumnIndex()).setPreferredWidth(30);

		getColumnModel().getColumn(SimpleMappingEditorColumn.OUTPUT_COLUMN_HEARDER_COL.getColumnIndex()).setPreferredWidth(100);
		getColumnModel().getColumn(SimpleMappingEditorColumn.OUTPUT_COLUMN_HEARDER_COL.getColumnIndex()).setCellEditor(new DefaultCellEditor(new JTextField()));

		getColumnModel().getColumn(SimpleMappingEditorColumn.OUTPUT_COLUMN_COL.getColumnIndex()).setCellEditor(new DefaultCellEditor(new JComboBox<>(MappingUtils.possibleColumns.toArray())));
		getColumnModel().getColumn(SimpleMappingEditorColumn.OUTPUT_COLUMN_COL.getColumnIndex()).setPreferredWidth(70);

		getColumnModel().getColumn(SimpleMappingEditorColumn.MAPPING_ARROW_COL.getColumnIndex()).setPreferredWidth(4);

		getColumnModel().getColumn(SimpleMappingEditorColumn.OBJECT_FIELD_COL.getColumnIndex()).setCellRenderer(new DefaultFieldTableCellRenderer());
		getColumnModel().getColumn(SimpleMappingEditorColumn.OBJECT_FIELD_COL.getColumnIndex()).setCellEditor(new DefaultFieldTableCellEditor(mapping.getObjectClass()));
		getColumnModel().getColumn(SimpleMappingEditorColumn.OBJECT_FIELD_COL.getColumnIndex()).setPreferredWidth(100);

		TableUtils.installAutoStopEditingCellEditors(this);
		TableUtils.installCenteredLabelsCellRenderers(this);
	}

	/**
	 * Apply.
	 *
	 * @return the simple mapping
	 */
	public SimpleMapping<?> apply() {
		return((SimpleMappingEditorTableModel)getModel()).apply();
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
		 * @param arg0 the arg 0
		 */
		@Override
		public void actionPerformed(ActionEvent arg0) {
			SimpleMappingEditorTableModel model = (SimpleMappingEditorTableModel)table.getModel();
			int row = table.convertRowIndexToModel( table.getEditingRow() );
			SimpleMappingEditorRow mapperRow = model.getRow(row);
			if(mapperRow.getRowType() == SimpleMappingRowType.NEW_ROW) {
				model.addRow(new SimpleMappingEditorRow());
			} else {
				model.removeRow(row);
			}

			// Lose focus and stop editing
			table.requestFocusInWindow();
			fireEditingStopped();
		}

	}
}
