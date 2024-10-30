package com.jtools.mappings.editors.block;

import java.awt.Component;
import java.awt.Window;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.AbstractCellEditor;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableModel;

import com.jtools.gui.table.tableModels.ITableModelWithCellsCustomAlignment;
import com.jtools.mappings.block.BlockMapping;
import com.jtools.mappings.common.MappingUtils;
import com.jtools.utils.concurrent.NamedCallable;

class BlockMappingTableCellEditor extends AbstractCellEditor implements TableCellEditor {

	private static final long serialVersionUID = 4289295153595588330L;

	private final Class<?>[] possibleClasses;

	private final JLabel component = new JLabel();

	private transient Object value;

	public BlockMappingTableCellEditor(Class<?>... possibleClasses) {
		this.possibleClasses = possibleClasses;
	}

	@Override
	public Object getCellEditorValue() {
		return value;
	}

	public void setCellEditorValue(Object value) {
		this.value = value;
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		setCellEditorValue(value);

		TableModel model = table.getModel();

		if (model instanceof ITableModelWithCellsCustomAlignment) {
			((JLabel) component).setHorizontalAlignment(
					((ITableModelWithCellsCustomAlignment) model).getCellHorizontalAlignment(row, column));
		}

		component.setText(getCellEditorValue() instanceof BlockMapping
				? ((BlockMapping<?>) getCellEditorValue()).getObjectClass().getSimpleName()
				: null);

		String fromColumn = (String) model.getValueAt(row, BlockMappingEditorColumn.FROM_COLUMN_COL.getColumnIndex());
		String toColumn = (String) model.getValueAt(row, BlockMappingEditorColumn.TO_COLUMN_COL.getColumnIndex());

		Field objectField = (Field) model.getValueAt(row, BlockMappingEditorColumn.OBJECT_FIELD_COL.getColumnIndex());

		String[] possibleColumns = MappingUtils.getColumnsRangeAsArray(fromColumn, toColumn);

		showEditorAsynch(table, objectField, value, row, possibleColumns);

		return component;
	}

	private void showEditorAsynch(JTable table, Field objectField, Object value, int row, String[] possibleColumns) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			@SuppressWarnings({ "rawtypes", "unchecked" })
			public void run() {
				Window parentWindow = (Window) SwingUtilities.getAncestorOfClass(Window.class, table);

				try {

					String identifier = objectField != null ? objectField.getName() : "" + row;

					Class<?> objectClass = getObjectClass(objectField, possibleClasses);
					if (objectClass == null) {
						Logger.getLogger(getClass().getName()).log(Level.FINE,
								"Cannot propose block mapping for " + identifier);
						return;
					}

					BlockMappingEditor editor = null;

					if (value == null) {
						BlockMapping<?> blockMapping = new BlockMapping<>(objectClass);
						
						editor = new BlockMappingEditor(blockMapping, possibleColumns, possibleClasses);
					}

					else if (value instanceof BlockMapping) {
						editor = new BlockMappingEditor((BlockMapping) value, possibleColumns);
					}

					if (editor != null) {
						editor.showEditorAsDialog(parentWindow, true, getOnOkCallable(editor, table));
					}
				} catch (IOException ex) {
					Logger.getLogger(getClass().getName()).log(Level.SEVERE, ex.getMessage());
					Logger.getLogger(getClass().getName()).log(Level.FINE, ex.getMessage(), ex);
				}
			}
		});
	}

	private NamedCallable<Void> getOnOkCallable(BlockMappingEditor<?> editor, JTable table) {
		return new NamedCallable<Void>() {

			@Override
			public Void call() throws Exception {
				setCellEditorValue(editor.getBlockMapping());
				editor.close();
				table.editingStopped(new ChangeEvent(BlockMappingTableCellEditor.this));
				return null;
			}
			
			@Override
			public String getName() {
				return "Set value & close";
			}
		};
	}

	public static Class<?> getObjectClass(Field objectField, Class<?>[] possibleClasses) {
		if (objectField == null) {
			return null;
		} else {
			List<Class<?>> possibleClassesList = Arrays.stream(possibleClasses).toList();

			// If the field is not a collection, filter the proposed through the given
			// possible classes
			if (possibleClassesList.contains(objectField.getType())) {
				return objectField.getType();
			}

			// If the field is a collection, filter the proposed through the given possible
			// classes looking at the generic type of the collection
			else if (Collection.class.isAssignableFrom(objectField.getType())) {
				Type genericType = objectField.getGenericType();
				if (genericType instanceof ParameterizedType) {
					ParameterizedType aType = (ParameterizedType) genericType;
					for (Type fieldArgsType : aType.getActualTypeArguments()) {
						Class<?> fieldArgClass = (Class<?>) fieldArgsType;
						if (possibleClassesList.contains(fieldArgClass)) {
							return fieldArgClass;
						}
					}
				}
				return null;
			}
		}

		return null;
	}

}