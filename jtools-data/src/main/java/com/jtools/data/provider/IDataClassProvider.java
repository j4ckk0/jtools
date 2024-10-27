/**
 * 
 */
package com.jtools.data.provider;

import java.util.Collections;
import java.util.List;

/**
 * @author j4ckk0
 *
 */
public interface IDataClassProvider {

	public Class<?> getDataClass();

	public default List<Class<?>> getPossibleDataClasses() {
		return Collections.singletonList(getDataClass());
	}

}
