package org.jtools.gui.table.cellEditors;

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

import java.awt.Component;

import javax.swing.DefaultCellEditor;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableModel;

import org.jtools.gui.table.tableModels.ITableModelWithCellsCustomAlignment;
import org.jtools.utils.gui.components.NumberTextField;
@SuppressWarnings({ "rawtypes", "unchecked" })
public class DefaultNumberTableCellEditor<E extends Number> extends DefaultCellEditor {

	private static final long serialVersionUID = 8619029332353311810L;

	private final Class<E> numberClass;

	public DefaultNumberTableCellEditor(Class<E> numberClass) {
		super(new NumberTextField<>(numberClass));
		this.numberClass = numberClass;
	}

	@Override
	public E getCellEditorValue() {
		Object cellEditorValue = super.getCellEditorValue();
		if(cellEditorValue == null) {
			return null;
		}
		if(!(cellEditorValue instanceof String)) {
			return null;
		}
		if(cellEditorValue instanceof String && ((String)cellEditorValue).length() == 0) {
			return null;
		}

		if(numberClass == Byte.class) {
			return numberClass.cast(Byte.parseByte((String)cellEditorValue));
		}
		if(numberClass == Short.class) {
			return numberClass.cast(Short.parseShort((String)cellEditorValue));
		}
		if(numberClass == Integer.class) {
			return numberClass.cast(Integer.parseInt((String)cellEditorValue));
		}
		if(numberClass == Double.class) {
			return numberClass.cast(Double.parseDouble((String)cellEditorValue));
		}
		if(numberClass == Float.class) {
			return numberClass.cast(Float.parseFloat((String)cellEditorValue));
		}
		if(numberClass == Long.class) {
			return numberClass.cast(Long.parseLong((String)cellEditorValue));
		}
		return null;
	}

	public static boolean isAssignableFrom(Class<?> columnClass) {
		if(columnClass.getSimpleName().equals("byte")) {
			return true;
		}
		if(columnClass.getSimpleName().equals("short")) {
			return true;
		}
		if(columnClass.getSimpleName().equals("int")) {
			return true;
		}
		if(columnClass.getSimpleName().equals("double")) {
			return true;
		}
		if(columnClass.getSimpleName().equals("float")) {
			return true;
		}
		if(columnClass.getSimpleName().equals("long")) {
			return true;
		}
		return false;
	}

	public static DefaultNumberTableCellEditor getEditorForColumnClass(Class<?> columnClass) {
		if(columnClass.getSimpleName().equals("byte")) {
			return new DefaultNumberTableCellEditor(Byte.class);
		}
		if(columnClass.getSimpleName().equals("short")) {
			return new DefaultNumberTableCellEditor(Short.class);
		}
		if(columnClass.getSimpleName().equals("int")) {
			return new DefaultNumberTableCellEditor(Integer.class);
		}
		if(columnClass.getSimpleName().equals("double")) {
			return new DefaultNumberTableCellEditor(Double.class);
		}
		if(columnClass.getSimpleName().equals("float")) {
			return new DefaultNumberTableCellEditor(Float.class);
		}
		if(columnClass.getSimpleName().equals("long")) {
			return new DefaultNumberTableCellEditor(Long.class);
		}
		return null;
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		Component component = super.getTableCellEditorComponent(table, value, isSelected, row, column);
		if(component instanceof JTextField) {
			TableModel model = table.getModel();
			if(model instanceof ITableModelWithCellsCustomAlignment) {
				((JTextField) component).setHorizontalAlignment(((ITableModelWithCellsCustomAlignment)model).getCellHorizontalAlignment(row, column));
			}
		}
		return component;
	}
}
