package org.jtools.gui.table.tableModels;

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

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jtools.utils.objects.ObjectInfoProvider;
import org.jtools.utils.objects.ObjectInfoProvider.ObjectInfo;
// TODO: Auto-generated Javadoc

/**
 * The Interface IObjectRow.
 */
public interface IObjectRow {

	/**
	 * Gets the value at.
	 *
	 * @param column the column
	 * @return the value at
	 */
	public Object getValueAt(int column);

	/**
	 * Sets the value at.
	 *
	 * @param column the column
	 * @param value the value
	 */
	public void setValueAt(int column, Object value);
	
	/**
	 * The Class ObjectRow.
	 *
	 * @param <E> the element type
	 */
	public class ObjectRow<E extends Object> implements IObjectRow {

		/** The object. */
		protected final E object;

		/**
		 * Instantiates a new object row.
		 *
		 * @param object the object
		 */
		public ObjectRow(E object) {
			this.object = object;
		}

		/**
		 * Gets the value at.
		 *
		 * @param column the column
		 * @return the value at
		 */
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

		/**
		 * Sets the value at.
		 *
		 * @param column the column
		 * @param value the value
		 */
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

		/**
		 * Gets the object.
		 *
		 * @return the object
		 */
		public E getObject() {
			return object;
		}
	}
	
	/**
	 * The Class NewRow.
	 */
	public class NewRow implements IObjectRow {

		/**
		 * Instantiates a new new row.
		 */
		public NewRow() {
			super();
		}
		
		/**
		 * Gets the value at.
		 *
		 * @param column the column
		 * @return the value at
		 */
		@Override
		public Object getValueAt(int column) {
			return null;
		}

		/**
		 * Sets the value at.
		 *
		 * @param column the column
		 * @param value the value
		 */
		@Override
		public void setValueAt(int column, Object value) {
			// Nothing
		}

	}

}
