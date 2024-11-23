package org.jtools.mappings.editors.block;

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
import java.util.List;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JTextField;

import org.jtools.gui.table.cellEditors.DefaultFieldTableCellEditor;
import org.jtools.gui.table.cellRenderers.DefaultFieldTableCellRenderer;
import org.jtools.gui.table.utils.TableUtils;
import org.jtools.mappings.block.BlockMapping;
import org.jtools.mappings.editors.block.BlockMappingEditorRow.BlockMappingRowType;
import org.jtools.utils.gui.components.ButtonColumn;
// TODO: Auto-generated Javadoc

/**
 * The Class BlockMappingEditorTable.
 *
 * @param <E> the element type
 */
public class BlockMappingEditorTable<E extends Object> extends JTable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -4774247812741796742L;
	
	/**
	 * Instantiates a new block mapping editor table.
	 *
	 * @param blockMapping the block mapping
	 * @param possibleColumns the possible columns
	 * @param possibleClasses the possible classes
	 */
	public BlockMappingEditorTable(BlockMapping<E> blockMapping, String[] possibleColumns, Class<?>... possibleClasses) {
		BlockMappingEditorTableModel<E> model = new BlockMappingEditorTableModel<E>(blockMapping, possibleClasses);
		setModel(model);

		setShowGrid(false);
		setRowSelectionAllowed(false);
		setCellSelectionEnabled(false);
		setRowHeight(20);

		new AddRemoveButtonColumn(this, BlockMappingEditorColumn.ADD_REMOVE_BUTTON_COL.getColumnIndex());

		getColumnModel().getColumn(BlockMappingEditorColumn.FROM_COLUMN_COL.getColumnIndex()).setCellEditor(new DefaultCellEditor(new JComboBox<>(possibleColumns)));

		getColumnModel().getColumn(BlockMappingEditorColumn.TO_COLUMN_COL.getColumnIndex()).setCellEditor(new DefaultCellEditor(new JComboBox<>(possibleColumns)));

		getColumnModel().getColumn(BlockMappingEditorColumn.OBJECT_FIELD_COL.getColumnIndex()).setCellRenderer(new DefaultFieldTableCellRenderer());
		getColumnModel().getColumn(BlockMappingEditorColumn.OBJECT_FIELD_COL.getColumnIndex()).setCellEditor(new DefaultFieldTableCellEditor());

		getColumnModel().getColumn(BlockMappingEditorColumn.HEADER_VALUE_COL.getColumnIndex()).setCellEditor(new DefaultCellEditor(new JTextField()));

		getColumnModel().getColumn(BlockMappingEditorColumn.SUB_BLOCK_MAPPING_COL.getColumnIndex()).setCellRenderer(new BlockMappingTableCellRenderer());
		getColumnModel().getColumn(BlockMappingEditorColumn.SUB_BLOCK_MAPPING_COL.getColumnIndex()).setCellEditor(new BlockMappingTableCellEditor(possibleClasses));

		TableUtils.installAutoStopEditingCellEditors(this);
		TableUtils.installCenteredLabelsCellRenderers(this);
		
		List<BlockMappingEditorRow> blockMappingEditorRows = BlockMappingEditorUtils.getBlockMappingEditorRows(blockMapping);
		initRows(blockMappingEditorRows);
	}

	/**
	 * Apply.
	 *
	 * @return the block mapping
	 */
	@SuppressWarnings("unchecked")
	public BlockMapping<E> apply() {
		return ((BlockMappingEditorTableModel<E>) getModel()).apply();
	}

	/**
	 * Inits the rows.
	 *
	 * @param rows the rows
	 */
	public void initRows(List<BlockMappingEditorRow> rows) {
		((BlockMappingEditorTableModel<?>) getModel()).initRows(rows);
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
			BlockMappingEditorTableModel<?> model = (BlockMappingEditorTableModel<?>) table.getModel();
			int row = table.convertRowIndexToModel(table.getEditingRow());
			BlockMappingEditorRow mapperRow = model.getRow(row);
			if (mapperRow.getRowType() == BlockMappingRowType.NEW_ROW) {
				model.addRow(new BlockMappingEditorRow());
			} else {
				model.removeRow(row);
			}

			// Lose focus and stop editing
			table.requestFocusInWindow();
			fireEditingStopped();
		}
	}
}
