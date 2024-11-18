/**
 * 
 */
package org.jtools.gui.list;

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

import java.util.List;

import javax.swing.JList;

import org.jtools.gui.list.listModels.ObjectsListModel;

/**
 * @author j4ckk0
 *
 */
public class ObjectsList<E extends Object> extends JList<E> {

	private static final long serialVersionUID = -759785412202035062L;

	protected final Class<E> objectClass;

	public ObjectsList(Class<E> objectClass, List<E> initialValues) {
		this.objectClass = objectClass;

		ObjectsListModel<E> model = new ObjectsListModel<E>();
		setModel(model);

		if(initialValues != null) {
			model.addAll(initialValues);
		}
	}

	public List<E> getDataList() {
		return ((ObjectsListModel<E>)getModel()).getElements();
	}

	public Class<E> getDataClass() {
		return objectClass;
	}

}
