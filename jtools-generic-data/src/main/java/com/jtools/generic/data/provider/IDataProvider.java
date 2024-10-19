/**
 * 
 */
package com.jtools.generic.data.provider;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Note: as multiple heritage is forbidden in java, it is way easier to delacre
 * this class as an Interface with default methods
 * 
 * @author j4ckk0
 *
 */
public interface IDataProvider {
	
	public String getProviderName();

	public Class<?> getDataClass();

	public List<?> getDataList();

	public default List<Class<?>> getPossibleDataClasses() {
		return Collections.singletonList(getDataClass());
	}

	public default Map<Class<?>, List<?>> getDataMap() {
		return Collections.singletonMap(getDataClass(), getDataList());
	}

	@SuppressWarnings("unchecked")
	public default <E> List<E> getDataList(Class<E> dataClass) {
		Map<Class<?>, List<?>> dataMap = getDataMap();
		if (dataMap == null) {
			throw new RuntimeException("method getDataMap() returned null. Bad implementation");
		}

		List<?> dataList = dataMap.get(dataClass);
		if (dataList == null) {
			Logger.getLogger(getClass().getName()).log(Level.WARNING,
					"Data class " + dataClass.getName() + " is not supported by this DataProvider");
			return Collections.emptyList();
		}

		return (List<E>) dataList;
	}

}
