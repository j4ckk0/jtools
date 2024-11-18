package org.jtools.mappings.editors.simple;

/*-
 * #%L
 * Java Tools - Mappings Editors
 * %%
 * Copyright (C) 2024 j4ckk0
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

/**
 * 
 * @author j4ckk0
 *
 */
public class SimpleMappingEditorRow {

	protected final SimpleMappingRow simpleMappingRow;
	

	public SimpleMappingEditorRow(SimpleMappingRow simpleMappingRow) {
		this.simpleMappingRow = simpleMappingRow;
	}
	
	public SimpleMappingEditorRow() {
		this.simpleMappingRow = new SimpleMappingRow();
	}

	public SimpleMappingRow getSimpleMappingRow() {
		return simpleMappingRow;
	}

	public SimpleMappingRowType getRowType() {
		return SimpleMappingRowType.MAPPING_ROW;
	}

	public String getMappingArrow() {
		return GuiUtils.ARROW_ICON_TXT;
	}

	public String getOutputColumnHeader() {
		if(simpleMappingRow != null) {
			return simpleMappingRow.getOutputColumnHeader();
		}
		return null;
	}

	public void setOutputColumnHeader(String value) {
		if(simpleMappingRow != null) {
			simpleMappingRow.setOutputColumnHeader(value);
		}
	}

	public String getOutputColumn() {
		if(simpleMappingRow != null) {
			return simpleMappingRow.getOutputColumn();
		}
		return null;
	}

	public void setOutputColumn(String value) {
		if(simpleMappingRow != null) {
			simpleMappingRow.setOutputColumn(value);
		}
	}

	public Field getObjectField() {
		if(simpleMappingRow != null) {
			return simpleMappingRow.getObjectField();
		}
		return null;
	}

	public void setObjectField(Field value) {
		if(simpleMappingRow != null) {
			simpleMappingRow.setObjectField(value);
		}
	}
	
	/**
	 * 
	 * @author j4ckk0
	 *
	 */
	public static class SimpleMappingEditorNewRow extends SimpleMappingEditorRow {

		public SimpleMappingEditorNewRow() {
			super(null);
		}

		@Override
		public SimpleMappingRowType getRowType() {
			return SimpleMappingRowType.NEW_ROW;
		}

		@Override
		public String getMappingArrow() {
			return null;
		}
	}
	
	/**
	 * 
	 * @author j4ckk0
	 *
	 */
	public enum SimpleMappingRowType {
		MAPPING_ROW(GuiUtils.MINUS_ICON_TXT), NEW_ROW(GuiUtils.PLUS_ICON_TXT);

		private final String label;

		private SimpleMappingRowType(String label) {
			this.label = label;
		}

		@Override
		public String toString() {
			return label;
		}
	}
}
