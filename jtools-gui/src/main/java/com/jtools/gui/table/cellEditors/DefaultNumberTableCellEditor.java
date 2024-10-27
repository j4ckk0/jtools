package com.jtools.gui.table.cellEditors;

import java.awt.Component;

import javax.swing.DefaultCellEditor;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableModel;

import com.jtools.gui.table.tableModels.ITableModelWithCellsCustomAlignment;
import com.jtools.utils.gui.components.NumberTextField;

/**
 * 
 * @author j4ckk0
 *
 * @param <E>
 */
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