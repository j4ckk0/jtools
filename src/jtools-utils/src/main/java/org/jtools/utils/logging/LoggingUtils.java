package org.jtools.utils.logging;

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

import java.net.URL;
import java.util.Properties;
// TODO: Auto-generated Javadoc

/**
 * The Class LoggingUtils.
 */
public class LoggingUtils {

	/**
	 * Load default config.
	 */
	public static void loadDefaultConfig() {
		Properties props = System.getProperties();

		// Logging
		URL loggingPropertiesFile = ClassLoader.getSystemResource("logging.properties");
		if (loggingPropertiesFile != null) {
			props.setProperty("java.util.logging.config.file", loggingPropertiesFile.getPath());
		}
	}

}
