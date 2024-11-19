package org.jtools.utils.objects;

/*-
 * #%L
 * Java Tools - Utils
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
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@SuppressWarnings({"rawtypes"})
public class ObjectInfoProvider {

	private static final Map<Class, ObjectInfo> objectInfoMap = new HashMap<>();

	private ObjectInfoProvider() {}
	
	public static <E extends Object> ObjectInfo getObjectInfo(Class<E> objectClass) {
		ObjectInfo objectInfo = objectInfoMap.get(objectClass);
		if(objectInfo == null) {
			objectInfo = new ObjectInfo(objectClass);
			objectInfoMap.put(objectClass, objectInfo);
		}
		return objectInfo;
	}

	public static class ObjectInfo {

		protected final Class<?> objectClass;

		protected final List<Field> possibleFields;

		public ObjectInfo(Class<?> objectClass) {
			this.objectClass = objectClass;
			this.possibleFields = ObjectUtils.getFields(objectClass);
		}

		public List<Field> getPossibleFields() {
			return possibleFields;
		}

		public int getPossibleFieldsCount() {
			return possibleFields.size();
		}

		public Method findGetter(Field field) {
			return ObjectUtils.findGetter(objectClass, field);
		}

		public Method findSetter(Field field) {
			return ObjectUtils.findSetter(objectClass, field);
		}

		
	}
}
