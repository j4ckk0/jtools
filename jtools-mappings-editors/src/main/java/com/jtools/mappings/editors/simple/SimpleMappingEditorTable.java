/**
 * 
 */
package com.jtools.mappings.editors.simple;

import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.jtools.gui.table.cellEditors.DefaultFieldTableCellEditor;
import com.jtools.gui.table.cellRenderers.DefaultFieldTableCellRenderer;
import com.jtools.gui.table.utils.TableUtils;
import com.jtools.mappings.common.MappingUtils;
import com.jtools.mappings.editors.simple.SimpleMappingEditorRow.SimpleMappingEditorNewRow;
import com.jtools.mappings.editors.simple.SimpleMappingEditorRow.SimpleMappingRowType;
import com.jtools.mappings.simple.SimpleMapping;
import com.jtools.mappings.simple.SimpleMappingRow;
import com.jtools.utils.gui.components.ButtonColumn;

/**
 * @author j4ckk0
 *
 */
public class SimpleMappingEditorTable extends JTable {

	private static final long serialVersionUID = -4774247812741796742L;

	/**
	 * 
	 * @param mapper
	 * @param rows
	 */
	public SimpleMappingEditorTable(SimpleMapping<?> mapping) {

		SimpleMappingEditorTableModel model = new SimpleMappingEditorTableModel(mapping.getMappingRows());
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

	public List<SimpleMappingRow> getRows() {
		return((SimpleMappingEditorTableModel)getModel()).getRows();
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
			SimpleMappingEditorTableModel model = (SimpleMappingEditorTableModel)table.getModel();
			int row = table.convertRowIndexToModel( table.getEditingRow() );
			SimpleMappingEditorRow mapperRow = model.getRow(row);
			if(mapperRow.getRowType() == SimpleMappingRowType.NEW_ROW) {
				model.addRow(new SimpleMappingEditorNewRow());
			} else {
				model.removeRow(row);
			}

			// Lose focus and stop editing
			table.requestFocusInWindow();
			fireEditingStopped();
		}

	}
}
