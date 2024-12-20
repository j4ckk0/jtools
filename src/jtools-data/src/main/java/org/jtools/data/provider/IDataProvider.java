package org.jtools.data.provider;

/*-
 * #%L
 * Java Tools - Data
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
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
// TODO: Auto-generated Javadoc

/**
 * The Interface IDataProvider.
 */
public interface IDataProvider extends IDataClassProvider {
	
	/**
	 * Gets the provider name.
	 *
	 * @return the provider name
	 */
	public String getProviderName();

	/**
	 * Gets the data list.
	 *
	 * @return the data list
	 */
	public List<?> getDataList();

	/**
	 * Gets the data map.
	 *
	 * @return the data map
	 */
	public default Map<Class<?>, List<?>> getDataMap() {
		return Collections.singletonMap(getDataClass(), getDataList());
	}

	/**
	 * Gets the data list.
	 *
	 * @param <E> the element type
	 * @param dataClass the data class
	 * @return the data list
	 */
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
