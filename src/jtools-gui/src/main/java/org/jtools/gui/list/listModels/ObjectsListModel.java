package org.jtools.gui.list.listModels;

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

import java.util.Collections;
import java.util.List;

import javax.swing.DefaultListModel;
// TODO: Auto-generated Javadoc

/**
 * The Class ObjectsListModel.
 *
 * @param <E> the element type
 */
public class ObjectsListModel<E extends Object> extends DefaultListModel<E> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -890234908906634753L;

	/**
	 * Gets the elements.
	 *
	 * @return the elements
	 */
	public List<E> getElements() {
		return Collections.list(elements());
	}

	/**
	 * Gets the size.
	 *
	 * @return the size
	 */
	@Override
	public int getSize() {
		return size();
	}

	/**
	 * Gets the element at.
	 *
	 * @param index the index
	 * @return the element at
	 */
	@Override
	public E getElementAt(int index) {
		return get(index);
	}

}
