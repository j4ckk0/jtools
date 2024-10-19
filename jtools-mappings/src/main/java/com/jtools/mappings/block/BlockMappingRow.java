package com.jtools.mappings.block;

import java.io.Serializable;
import java.lang.reflect.Field;

import com.jtools.utils.objects.SerializableField;

/**
 * 
 * @author j4ckk0
 *
 */
public class BlockMappingRow implements Serializable {

	private static final long serialVersionUID = -9078066581764951630L;

	protected String fromColumn;
	protected String toColumn;

	protected String headerValue;

	protected SerializableField objectField;

	protected BlockMapping<?> subBlockMapping;

	public BlockMappingRow() {
		this(null, null, null,null);
	}
	
	public BlockMappingRow(String fromColumn, String toColumn, Field objectField, String headerValue) {
		this(fromColumn, toColumn, objectField, headerValue, null);
	}

	public BlockMappingRow(String fromColumn, String toColumn, Field objectField, String headerValue, BlockMapping<?> subBlockMapping) {
		this.fromColumn = fromColumn;
		this.toColumn = toColumn;
		this.headerValue = headerValue;
		this.objectField = objectField != null ? new SerializableField(objectField) : null;
		this.subBlockMapping = subBlockMapping;
	}

	public String getFromColumn() {
		return fromColumn;
	}

	public String getToColumn() {
		return toColumn;
	}

	public String getHeaderValue() {
		return headerValue;
	}

	/**
	 * @param fromColumn the fromColumn to set
	 */
	public void setFromColumn(String fromColumn) {
		this.fromColumn = fromColumn;
	}

	/**
	 * @param toColumn the toColumn to set
	 */
	public void setToColumn(String toColumn) {
		this.toColumn = toColumn;
	}

	/**
	 * @param headerValue the headerValue to set
	 */
	public void setHeaderValue(String headerValue) {
		this.headerValue = headerValue;
	}

	/**
	 * @param objectField the objectField to set
	 */
	public void setObjectField(SerializableField objectField) {
		this.objectField = objectField;
	}

	/**
	 * @param subBlockMapping the subBlockMapping to set
	 */
	public void setSubBlockMapping(BlockMapping<?> subBlockMapping) {
		this.subBlockMapping = subBlockMapping;
	}

	public SerializableField getSerializableObjectField() {
		return objectField;
	}

	public Field getObjectField() {
		return objectField != null ? objectField.getField() : null;
	}

	public BlockMapping<?> getSubBlockMapping() {
		return subBlockMapping;
	}

}