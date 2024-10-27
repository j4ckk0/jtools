/**
 * 
 */
package com.jtools.utils.logging;

import java.net.URL;
import java.util.Properties;

/**
 * @author j4ckk0
 *
 */
public class LoggingUtils {

	public static void loadDefaultConfig() {
		Properties props = System.getProperties();

		// Logging
		URL loggingPropertiesFile = ClassLoader.getSystemResource("logging.properties");
		if (loggingPropertiesFile != null) {
			props.setProperty("java.util.logging.config.file", loggingPropertiesFile.getPath());
		}
	}

}
