/**
 * 
 */
package org.jtools.gui.list.listModels;

/*-
 * #%L
 * Java Tools - GUI
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

import java.util.Collections;
import java.util.List;

import javax.swing.DefaultListModel;

/**
 * @author j4ckk0
 *
 */
public class ObjectsListModel<E extends Object> extends DefaultListModel<E> {

	private static final long serialVersionUID = -890234908906634753L;

	public List<E> getElements() {
		return Collections.list(elements());
	}

	@Override
	public int getSize() {
		return size();
	}

	@Override
	public E getElementAt(int index) {
		return get(index);
	}

}
