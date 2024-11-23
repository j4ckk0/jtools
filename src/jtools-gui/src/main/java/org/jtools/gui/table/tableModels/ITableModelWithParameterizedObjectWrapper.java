package org.jtools.gui.table.tableModels;

// TODO: Auto-generated Javadoc
/**
 * The Interface ITableModelWithParameterizedObjectWrapper.
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
public interface ITableModelWithParameterizedObjectWrapper extends ITableModelWithObjectWrapper {

	/**
	 * Gets the wrapped parameterized class.
	 *
	 * @param row the row
	 * @param column the column
	 * @return the wrapped parameterized class
	 */
	public Class<?> getWrappedParameterizedClass(int row, int column);

}
