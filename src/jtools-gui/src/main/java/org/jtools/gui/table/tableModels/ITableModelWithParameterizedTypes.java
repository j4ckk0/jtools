package org.jtools.gui.table.tableModels;

// TODO: Auto-generated Javadoc
/**
 * The Interface ITableModelWithParameterizedTypes.
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
public interface ITableModelWithParameterizedTypes {

	/**
	 * Gets the column parameterized class.
	 *
	 * @param columnIndex the column index
	 * @return the column parameterized class
	 */
	public Class<?> getColumnParameterizedClass(int columnIndex);

}
