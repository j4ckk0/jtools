package org.jtools.mappings.editors.simple;

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

import java.lang.reflect.Field;

import org.jtools.mappings.simple.SimpleMappingRow;
import org.jtools.utils.gui.GuiUtils;
// TODO: Auto-generated Javadoc

/**
 * The Class SimpleMappingEditorRow.
 */
public class SimpleMappingEditorRow {

	/** The simple mapping row. */
	protected final SimpleMappingRow simpleMappingRow;
	

	/**
	 * Instantiates a new simple mapping editor row.
	 *
	 * @param simpleMappingRow the simple mapping row
	 */
	public SimpleMappingEditorRow(SimpleMappingRow simpleMappingRow) {
		this.simpleMappingRow = simpleMappingRow;
	}
	
	/**
	 * Instantiates a new simple mapping editor row.
	 */
	public SimpleMappingEditorRow() {
		this.simpleMappingRow = new SimpleMappingRow();
	}

	/**
	 * Gets the simple mapping row.
	 *
	 * @return the simple mapping row
	 */
	public SimpleMappingRow getSimpleMappingRow() {
		return simpleMappingRow;
	}

	/**
	 * Gets the row type.
	 *
	 * @return the row type
	 */
	public SimpleMappingRowType getRowType() {
		return SimpleMappingRowType.MAPPING_ROW;
	}

	/**
	 * Gets the mapping arrow.
	 *
	 * @return the mapping arrow
	 */
	public String getMappingArrow() {
		return GuiUtils.ARROW_ICON_TXT;
	}

	/**
	 * Gets the output column header.
	 *
	 * @return the output column header
	 */
	public String getOutputColumnHeader() {
		if(simpleMappingRow != null) {
			return simpleMappingRow.getOutputColumnHeader();
		}
		return null;
	}

	/**
	 * Sets the output column header.
	 *
	 * @param value the new output column header
	 */
	public void setOutputColumnHeader(String value) {
		if(simpleMappingRow != null) {
			simpleMappingRow.setOutputColumnHeader(value);
		}
	}

	/**
	 * Gets the output column.
	 *
	 * @return the output column
	 */
	public String getOutputColumn() {
		if(simpleMappingRow != null) {
			return simpleMappingRow.getOutputColumn();
		}
		return null;
	}

	/**
	 * Sets the output column.
	 *
	 * @param value the new output column
	 */
	public void setOutputColumn(String value) {
		if(simpleMappingRow != null) {
			simpleMappingRow.setOutputColumn(value);
		}
	}

	/**
	 * Gets the object field.
	 *
	 * @return the object field
	 */
	public Field getObjectField() {
		if(simpleMappingRow != null) {
			return simpleMappingRow.getObjectField();
		}
		return null;
	}

	/**
	 * Sets the object field.
	 *
	 * @param value the new object field
	 */
	public void setObjectField(Field value) {
		if(simpleMappingRow != null) {
			simpleMappingRow.setObjectField(value);
		}
	}
		
		/**
		 * The Class SimpleMappingEditorNewRow.
		 */
		public static class SimpleMappingEditorNewRow extends SimpleMappingEditorRow {

		/**
		 * Instantiates a new simple mapping editor new row.
		 */
		public SimpleMappingEditorNewRow() {
			super(null);
		}

		/**
		 * Gets the row type.
		 *
		 * @return the row type
		 */
		@Override
		public SimpleMappingRowType getRowType() {
			return SimpleMappingRowType.NEW_ROW;
		}

		/**
		 * Gets the mapping arrow.
		 *
		 * @return the mapping arrow
		 */
		@Override
		public String getMappingArrow() {
			return null;
		}
	}
		
		/**
		 * The Enum SimpleMappingRowType.
		 */
		public enum SimpleMappingRowType {
		
		/** The mapping row. */
		MAPPING_ROW(GuiUtils.MINUS_ICON_TXT), 
 /** The new row. */
 NEW_ROW(GuiUtils.PLUS_ICON_TXT);

		/** The label. */
		private final String label;

		/**
		 * Instantiates a new simple mapping row type.
		 *
		 * @param label the label
		 */
		private SimpleMappingRowType(String label) {
			this.label = label;
		}

		/**
		 * To string.
		 *
		 * @return the string
		 */
		@Override
		public String toString() {
			return label;
		}
	}
}
