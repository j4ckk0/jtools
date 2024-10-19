/**
 * 
 */
package com.jtools.utils.objects;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author j4ckk0
 *
 */
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


	/**
	 * 
	 * @author j4ckk0
	 *
	 * @param <E>
	 */
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
