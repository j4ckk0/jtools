/**
 * 
 */
package com.jtools.mappings.editors.block;

/*-
 * #%L
 * Java Tools - Mappings Editors
 * %%
 * Copyright (C) 2024 j4ckk0
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

import com.jtools.gui.table.cellEditors.DefaultFieldTableCellEditor;
import com.jtools.gui.table.cellRenderers.DefaultFieldTableCellRenderer;
import com.jtools.gui.table.utils.TableUtils;
import com.jtools.mappings.block.BlockMapping;
import com.jtools.mappings.editors.block.BlockMappingEditorRow.BlockMappingRowType;
import com.jtools.utils.gui.components.ButtonColumn;

/**
 * @author j4ckk0
 *
 */
public class BlockMappingEditorTable<E extends Object> extends JTable {

	private static final long serialVersionUID = -4774247812741796742L;

	/**
	 * 
	 * @param mapper
	 * @param rows
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

	@SuppressWarnings("unchecked")
	public BlockMapping<E> apply() {
		return ((BlockMappingEditorTableModel<E>) getModel()).apply();
	}

	public void initRows(List<BlockMappingEditorRow> rows) {
		((BlockMappingEditorTableModel<?>) getModel()).initRows(rows);
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