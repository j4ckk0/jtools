package com.jtools.mappings.simple;

import java.lang.reflect.Field;

/**
 * 
 * @author j4ckk0
 *
 */
public class SimpleMappingRow {

	protected String outputColumnHeader;
	protected String outputColumn;
	protected Field objectField;

	public SimpleMappingRow() {
		this.outputColumnHeader = null;
		this.outputColumn = null;
		this.objectField = null;
	}

	public SimpleMappingRow(String outputColumnHeader, String outputColumn, Field objectField) {
		this.outputColumnHeader = outputColumnHeader;
		this.outputColumn = outputColumn;
		this.objectField = objectField;
	}

	/**
	 * @return the outputColumnHeader
	 */
	public String getOutputColumnHeader() {
		return outputColumnHeader;
	}

	/**
	 * @param outputColumnHeader the outputColumnHeader to set
	 */
	public void setOutputColumnHeader(String outputColumnHeader) {
		this.outputColumnHeader = outputColumnHeader;
	}

	/**
	 * @return the outputColumn
	 */
	public String getOutputColumn() {
		return outputColumn;
	}

	/**
	 * @param outputColumn the outputColumn to set
	 */
	public void setOutputColumn(String outputColumn) {
		this.outputColumn = outputColumn;
	}

	/**
	 * @return the objectField
	 */
	public Field getObjectField() {
		return objectField;
	}

	/**
	 * @param objectField the objectField to set
	 */
	public void setObjectField(Field objectField) {
		this.objectField = objectField;
	}
}