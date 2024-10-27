/**
 * 
 */
package com.jtools.gui.table.tableModels;

/**
 * @author j4ckk0
 *
 */
public interface ITableModelWithObjectWrapper {
	
	public static interface ObjectWrapper { }

	public Class<?> getWrappedClassAt(int row, int column);

	public Object getWrappedValueAt(int row, int column);

}
