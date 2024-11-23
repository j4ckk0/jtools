package org.jtools.gui.table.tableModels;

// TODO: Auto-generated Javadoc
/**
 * The Interface ITableModelWithCellsCustomAlignment.
 */
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
public interface ITableModelWithCellsCustomAlignment {

	/**
	 * Gets the cell horizontal alignment.
	 *
	 * @param row the row
	 * @param column the column
	 * @return the cell horizontal alignment
	 */
	public int getCellHorizontalAlignment(int row, int column);

}
