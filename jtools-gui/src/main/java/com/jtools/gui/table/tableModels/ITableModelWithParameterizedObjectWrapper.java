/**
 * 
 */
package com.jtools.gui.table.tableModels;

/**
 * @author j4ckk0
 *
 */
public interface ITableModelWithParameterizedObjectWrapper extends ITableModelWithObjectWrapper {

	public Class<?> getWrappedParameterizedClass(int row, int column);

}
