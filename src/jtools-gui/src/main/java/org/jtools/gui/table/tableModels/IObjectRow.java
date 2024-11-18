/**
 * 
 */
package org.jtools.gui.table.tableModels;

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

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jtools.utils.objects.ObjectInfoProvider;
import org.jtools.utils.objects.ObjectInfoProvider.ObjectInfo;

/**
 * @author j4ckk0
 *
 */
public interface IObjectRow {

	public Object getValueAt(int column);

	public void setValueAt(int column, Object value);

	/**
	 * 
	 * @author j4ckk0
	 *
	 */
	public class ObjectRow<E extends Object> implements IObjectRow {

		protected final E object;

		public ObjectRow(E object) {
			this.object = object;
		}

		@Override
		public Object getValueAt(int column) {
			try {
				ObjectInfo objectInfo = ObjectInfoProvider.getObjectInfo(object.getClass());
				Field field = objectInfo.getPossibleFields().get(column - 1);
				Method getter = objectInfo.findGetter(field);
				if(getter != null) {
					return getter.invoke(object);
				} else {
					Logger.getLogger(getClass().getName()).log(Level.FINE, "getter not found for field " + field.getName());
					return null;
				}
			} catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
				Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage());
				Logger.getLogger(getClass().getName()).log(Level.FINE, e.getMessage(), e);
				return null;
			}
		}

		@Override
		public void setValueAt(int column, Object value) {
			try {
				ObjectInfo objectInfo = ObjectInfoProvider.getObjectInfo(object.getClass());
				Field field = objectInfo.getPossibleFields().get(column - 1);
				Method setter = objectInfo.findSetter(field);
				if (setter != null) {
					setter.invoke(object, value);
				} else {
					Logger.getLogger(getClass().getName()).log(Level.FINE, "setter not found for field " + field.getName());
				}
			} catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
				Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage());
				Logger.getLogger(getClass().getName()).log(Level.FINE, e.getMessage(), e);
				return;
			}
		}

		public E getObject() {
			return object;
		}
	}

	/**
	 * 
	 * @author j4ckk0
	 *
	 */
	public class NewRow implements IObjectRow {

		@Override
		public Object getValueAt(int column) {
			return null;
		}

		@Override
		public void setValueAt(int column, Object value) {
			// Nothing
		}

	}

}
