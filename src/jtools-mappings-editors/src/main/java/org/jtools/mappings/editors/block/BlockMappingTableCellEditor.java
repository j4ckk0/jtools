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

import java.awt.Component;
import java.awt.Window;
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

import org.jtools.gui.table.tableModels.ITableModelWithCellsCustomAlignment;
import org.jtools.mappings.block.BlockMapping;
import org.jtools.mappings.common.MappingUtils;
import org.jtools.utils.concurrent.NamedCallable;

// TODO: Auto-generated Javadoc
/**
 * The Class BlockMappingTableCellEditor.
 */
class BlockMappingTableCellEditor extends AbstractCellEditor implements TableCellEditor {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 4289295153595588330L;

	/** The possible classes. */
	private final Class<?>[] possibleClasses;

	/** The component. */
	private final JLabel component = new JLabel();

	/** The value. */
	private transient Object value;

	/**
	 * Instantiates a new block mapping table cell editor.
	 *
	 * @param possibleClasses the possible classes
	 */
	public BlockMappingTableCellEditor(Class<?>... possibleClasses) {
		this.possibleClasses = possibleClasses;
	}

	/**
	 * Gets the cell editor value.
	 *
	 * @return the cell editor value
	 */
	@Override
	public Object getCellEditorValue() {
		return value;
	}

	/**
	 * Sets the cell editor value.
	 *
	 * @param value the new cell editor value
	 */
	public void setCellEditorValue(Object value) {
		this.value = value;
	}

	/**
	 * Gets the table cell editor component.
	 *
	 * @param table the table
	 * @param value the value
	 * @param isSelected the is selected
	 * @param row the row
	 * @param column the column
	 * @return the table cell editor component
	 */
	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		setCellEditorValue(value);

		TableModel model = table.getModel();

		if (model instanceof ITableModelWithCellsCustomAlignment) {
			component.setHorizontalAlignment(
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

	/**
	 * Show editor asynch.
	 *
	 * @param table the table
	 * @param objectField the object field
	 * @param value the value
	 * @param row the row
	 * @param possibleColumns the possible columns
	 */
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
				} catch (Exception ex) {
					Logger.getLogger(getClass().getName()).log(Level.SEVERE, ex.getMessage());
					Logger.getLogger(getClass().getName()).log(Level.FINE, ex.getMessage(), ex);
				}
			}
		});
	}

	/**
	 * Gets the on ok callable.
	 *
	 * @param editor the editor
	 * @param table the table
	 * @return the on ok callable
	 */
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

	/**
	 * Gets the object class.
	 *
	 * @param objectField the object field
	 * @param possibleClasses the possible classes
	 * @return the object class
	 */
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
