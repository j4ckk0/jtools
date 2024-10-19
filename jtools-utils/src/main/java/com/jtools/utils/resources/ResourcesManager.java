/**
 * 
 */
package com.jtools.utils.resources;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import org.apache.commons.io.IOUtils;

/**
 * 
 * @author j4ckk0
 *
 */
public class ResourcesManager {

	//////////////////////////////////////////////////
	//
	// Class variables and constants
	//
	//////////////////////////////////////////////////

	private static ResourcesManager instance;

	protected static final String LABEL_PROPERTIES_FILE = "labels.properties";

	protected static final String CONFIG_PROPERTIES_FILE = "config.properties";

	protected static final String PREFERENCES_PROPERTIES_FILE = "resources/conf/preferences.properties";

	private HashMap<String, BufferedImage> images;

	private Properties preferencesProperties;

	private Properties labelsProperties;

	private Properties configProperties;

	//////////////////////////////////////////////////
	//
	// Constructors
	//
	//////////////////////////////////////////////////

	/**
	 * 
	 */
	private ResourcesManager() {
		images = new HashMap<>();
//		preferencesProperties = this.loadExternalProperties(PREFERENCES_PROPERTIES_FILE);
//		configProperties = this.loadPropertiesInJar(CONFIG_PROPERTIES_FILE);
//		labelsProperties = this.loadPropertiesInJar(LABEL_PROPERTIES_FILE);
	}

	/**
	 * 
	 * @return
	 */
	public static ResourcesManager instance() {
		if (null == instance) {
			instance = new ResourcesManager();
		}
		return instance;
	}

	//////////////////////////////////////////////////
	//
	// Public methods
	//
	//////////////////////////////////////////////////

	/**
	 * 
	 * @param imageFilename
	 * @return
	 */
	public BufferedImage getImage(String imageFilename) {
		BufferedImage image = images.get(imageFilename);
		if (null == image) {
			InputStream iconInputStream = ClassLoader.getSystemResourceAsStream(imageFilename);
			try {
				image = ImageIO.read(iconInputStream);
			} catch (IOException e) {
				Logger.getLogger(ResourcesManager.class.getName()).log(Level.SEVERE, e.getMessage(), e);
			}
		}
		return image;
	}

	/**
	 * 
	 * @param key
	 * @return
	 */
	public String getLabel(String key) {
		String langage = getPreferencesProperty("langage");
		String label = labelsProperties.getProperty(key + "." + langage);
		if (null == label) {
			label = "/!\\ " + key;
			Logger.getLogger(ResourcesManager.class.getName()).log(Level.WARNING, "Missing label: " + key);
		}
		return label;
	}

	/**
	 * 
	 * @param key
	 * @param replacementValues
	 * @return
	 */
	public String getLabel(String key, String... replacementValues) {
		String label = this.getLabel(key);
		for (int i = 0; i < replacementValues.length; i++) {
			label = label.replace("{" + i + "}", replacementValues[i]);
		}
		return label;
	}

	/**
	 * 
	 * @param key
	 * @return
	 */
	public String getPreferencesProperty(String key) {
		return preferencesProperties.getProperty(key);
	}

	/**
	 * 
	 * @param key
	 * @return
	 */
	public String getConfigProperty(String key) {
		return configProperties.getProperty(key);
	}

	/**
	 * 
	 * @param key
	 * @return
	 * @throws NumberFormatException
	 */
	public Integer getConfigPropertyInt(String key) throws NumberFormatException {
		String property = configProperties.getProperty(key);
		if (property.equals("MAX")) {
			return Integer.MAX_VALUE;
		}
		if (property.equals("MAX")) {
			return Integer.MIN_VALUE;
		}

		return Integer.parseInt(property);
	}

	/**
	 * 
	 * @param key
	 * @return
	 * @throws NumberFormatException
	 */
	public Float getConfigPropertyFloat(String key) throws NumberFormatException {
		String property = configProperties.getProperty(key);
		if (property.equals("MAX")) {
			return Float.MAX_VALUE;
		}
		if (property.equals("MAX")) {
			return Float.MIN_VALUE;
		}

		return Float.parseFloat(property);
	}

	/**
	 * 
	 * @param key
	 * @return
	 * @throws NumberFormatException
	 */
	public Boolean getConfigPropertyBoolean(String key) throws NumberFormatException {
		String propriete = configProperties.getProperty(key);

		return Boolean.parseBoolean(propriete);
	}

	/**
	 * 
	 * @param filename
	 * @return
	 */
	public String loadFileContent(String filename) {
		try {
			InputStream is = ClassLoader.getSystemResourceAsStream(filename);
			return IOUtils.toString(is, StandardCharsets.UTF_8);
		} catch (IOException e) {
			Logger.getLogger(ResourcesManager.class.getName()).log(Level.SEVERE, e.getMessage(), e);
		}
		return null;
	}

	//////////////////////////////////////////////////
	//
	// Private methods
	//
	//////////////////////////////////////////////////

	/**
	 * 
	 * @param propertiesFilename
	 * @return
	 */
	protected Properties loadExternalProperties(String propertiesFilename) {
		Properties properties = new Properties();
		try {
			properties.load(new FileInputStream(propertiesFilename));
		} catch (IOException e) {
			Logger.getLogger(ResourcesManager.class.getName()).log(Level.SEVERE, e.getMessage(), e);
		}
		return properties;
	}

	/**
	 * 
	 * @param propertiesFileName
	 * @return
	 */
	protected Properties loadPropertiesInJar(String propertiesFileName) {
		Properties properties = new Properties();
		InputStream propertiesIS = ClassLoader.getSystemResourceAsStream(propertiesFileName);
		try {
			properties.load(propertiesIS);
		} catch (IOException e) {
			Logger.getLogger(ResourcesManager.class.getName()).log(Level.SEVERE, e.getMessage(), e);
		}
		return properties;
	}
}
