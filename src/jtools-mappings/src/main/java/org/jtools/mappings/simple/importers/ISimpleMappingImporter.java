package org.jtools.mappings.simple.importers;

/*-
 * #%L
 * Java Tools - Mappings
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

import java.io.IOException;
import java.util.List;

import org.jtools.mappings.simple.SimpleMappingRow;
// TODO: Auto-generated Javadoc

/**
 * The Class ASimpleMappingImporter.
 */
public interface ISimpleMappingImporter {

	/**
	 * Import data.
	 *
	 * @param <T>         the generic type
	 * @param objectClass the object class
	 * @param mappings    the mappings
	 * @return the list
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public <T> List<T> importData(Class<T> objectClass, List<SimpleMappingRow> mappings) throws IOException;

}
