package org.jtools.mappings.simple.io;

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

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFileChooser;

import org.apache.commons.collections4.map.HashedMap;
import org.jtools.mappings.common.MappingException;
import org.jtools.mappings.simple.SimpleMapping;
import org.jtools.mappings.simple.SimpleMappingRow;
import org.jtools.utils.CommonUtils;
import org.jtools.utils.dates.DateFormatManager;
import org.jtools.utils.geo.CoordinatesFormatManager;
import org.jtools.utils.geo.CoordinatesFormatManager.CoordinatesFormat;
import org.jtools.utils.objects.ObjectUtils;
// TODO: Auto-generated Javadoc

/**
 * The Class SimpleMappingFileManager.
 */
public class SimpleMappingFileManager {

	//////////////////////////////
	//
	// Constants and fields
	//
	//////////////////////////////

	/** The Constant MAPPING_OUTPUT_COLUMN_PROPERTIES_PARSE_PATTERN. */
	private static final Pattern MAPPING_OUTPUT_COLUMN_PROPERTIES_PARSE_PATTERN = Pattern.compile("(.+) \\[(.+)\\]");
	
	/** The Constant MAPPING_OUTPUT_COLUMN_PROPERTIES_FORMAT_PATTERN. */
	private static final String MAPPING_OUTPUT_COLUMN_PROPERTIES_FORMAT_PATTERN = "{0} [{1}]";

	/** The Constant SIMPLE_MAPPING_FILE_MARK_PROPERTY. */
	private static final String SIMPLE_MAPPING_FILE_MARK_PROPERTY = "josm";
	
	/** The Constant SIMPLE_MAPPING_FILE_MARK_VALUE. */
	private static final String SIMPLE_MAPPING_FILE_MARK_VALUE = "Java Object Simple Mapping";

	/** The Constant OBJECT_CLASS_PROPERTY. */
	private static final String OBJECT_CLASS_PROPERTY = "object.class";

	/** The Constant DATE_FORMAT_PROPERTY. */
	private static final String DATE_FORMAT_PROPERTY = "date.format";

	/** The Constant COORDINATES_FORMAT_PROPERTY. */
	private static final String COORDINATES_FORMAT_PROPERTY = "coordinates.format";

	/** The Constant SAVE_SIMPLE_MAPPING_DIALOG_TITLE. */
	public static final String SAVE_SIMPLE_MAPPING_DIALOG_TITLE = "Select a simple mapping file";

	/** The Constant LOAD_SIMPLE_MAPPING_DIALOG_TITLE. */
	public static final String LOAD_SIMPLE_MAPPING_DIALOG_TITLE = "Select a simple mapping file";

	/** The Constant SIMPLE_MAPPING_FILE_EXTENSION. */
	public static final String SIMPLE_MAPPING_FILE_EXTENSION = ".josm"; // Java Object Simple Mapping

	/** The instance. */
	private static SimpleMappingFileManager instance;

	/** The mappings file paths. */
	private final Map<UUID, String> mappingsFilePaths = new HashedMap<>();

	//////////////////////////////
	//
	// Constructors
	//
	//////////////////////////////

	/**
	 * Instantiates a new simple mapping file manager.
	 */
	private SimpleMappingFileManager() {

	}

	//////////////////////////////
	//
	// Public methods
	//
	//////////////////////////////

	/**
	 * Instance.
	 *
	 * @return the simple mapping file manager
	 */
	public static SimpleMappingFileManager instance() {
		if(instance == null) {
			instance = new SimpleMappingFileManager();
		}
		return instance;
	}
	
	/**
	 * Load mapping.
	 *
	 * @param <E> the element type
	 * @param mappingFilepath the mapping filepath
	 * @return the simple mapping
	 * @throws InstantiationException the instantiation exception
	 * @throws MappingException the mapping exception
	 */
	@SuppressWarnings({ "unchecked" })
	public <E extends Object> SimpleMapping<E> loadMapping(String mappingFilepath) throws InstantiationException, MappingException {
		Properties properties;
		try {
			properties = CommonUtils.loadProperties(mappingFilepath);
		} catch (IOException e) {
			throw new InstantiationException("An error occured while loading mapping editor: " + e.getMessage());
		}

		// Object class
		Object objectClassProperty = properties.get(OBJECT_CLASS_PROPERTY);
		if(objectClassProperty == null) {
			throw new InstantiationException("An error occured while loading mapping editor: Selected mapping file does not contains any mapping object class");
		}

		if(!(objectClassProperty instanceof String)) {
			throw new InstantiationException("An error occured while loading mapping editor: Selected mapping file does not contains a String definition of mapping object class");
		}

		Class<E> objectClass = null;
		try {
			objectClass = (Class<E>) Class.forName((String)objectClassProperty);
		} catch (ClassNotFoundException e) {
			throw new InstantiationException("Unknown class: " + e.getMessage());
		}

		// Rows
		List<SimpleMappingRow> rows = new ArrayList<>();
		try {
			rows = propertiesToRows(properties, objectClass);
		}catch (MappingException e) {
			throw new InstantiationException(e.getMessage());
		}

		try {
			SimpleMapping<E> mapping = new SimpleMapping<>(objectClass, rows);
			mappingsFilePaths.put(mapping.getId(), mappingFilepath);
			
			return mapping;

		}catch (Exception e) {
			throw new InstantiationException("An error occured while loading mapping editor: " + e.getMessage());
		}
	}
	
	/**
	 * Gets the default mapping file path.
	 *
	 * @param objectClass the object class
	 * @return the default mapping file path
	 */
	public String getDefaultMappingFilePath(Class<?> objectClass) {
		return "./resources/" + objectClass.getName() + ".properties";
	}
	
	/**
	 * Gets the default mapping file.
	 *
	 * @param objectClass the object class
	 * @return the default mapping file
	 */
	public File getDefaultMappingFile(Class<?> objectClass) {
		return new File(getDefaultMappingFilePath(objectClass));
	}

	/**
	 * Save.
	 *
	 * @param mapping the mapping
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void save(SimpleMapping<?> mapping) throws IOException {
		String mappingFilepath = getMappingFilepath(mapping.getId());

		File defaultFile = null;
		if(mappingFilepath != null && mappingFilepath.length() != 0) {
			defaultFile = new File(mappingFilepath);
		}

		File mappingFile = CommonUtils.chooseFile(JFileChooser.SAVE_DIALOG, defaultFile, SAVE_SIMPLE_MAPPING_DIALOG_TITLE, SIMPLE_MAPPING_FILE_EXTENSION);
		if(mappingFile != null) {
			Properties properties = rowsToProperties(mapping.getObjectClass(), mapping.getRows());

			mappingsFilePaths.put(mapping.getId(), mappingFile.getAbsolutePath());

			CommonUtils.saveProperties(properties, mappingFile.getAbsolutePath());
		}
	}

	/**
	 * Gets the mapping filepath.
	 *
	 * @param id the id
	 * @return the mapping filepath
	 */
	public String getMappingFilepath(UUID id) {
		return mappingsFilePaths.get(id);
	}

	//////////////////////////////
	//
	// Private methods
	//
	//////////////////////////////

	/**
	 * Properties to rows.
	 *
	 * @param properties the properties
	 * @param objectClass the object class
	 * @return the list
	 * @throws MappingException the mapping exception
	 */
	private List<SimpleMappingRow> propertiesToRows(Properties properties, Class<?> objectClass) throws MappingException {
		// Test if the Properties is valid
		Object fileMarkProperty = properties.get(SIMPLE_MAPPING_FILE_MARK_PROPERTY);
		if (fileMarkProperty == null) {
			throw new MappingException("Loaded mapping file seems not to be a valid simple mapping file");
		}

		// Get date format
		Object dateFormatProperty = properties.get(DATE_FORMAT_PROPERTY);
		if (!(dateFormatProperty instanceof String)) {
			Logger.getLogger(getClass().getName()).log(Level.WARNING, "Unable to find the date format. Using default one.");
			DateFormatManager.instance().setActiveDateFormat(DateFormatManager.BASIC_DATE_FORMAT);
		} else {
			DateFormatManager.instance().setActiveDateFormat((String)dateFormatProperty);
		}

		// Get formats
		Object coordinatesFormatProperty = properties.get(COORDINATES_FORMAT_PROPERTY);
		if (!(coordinatesFormatProperty instanceof String)) {
			Logger.getLogger(getClass().getName()).log(Level.WARNING, "Unable to find the coordinates format. Using default one.");
			CoordinatesFormatManager.instance().setActiveCoordinatesFormat(CoordinatesFormat.LAT_LON_DD);
		} else {
			CoordinatesFormatManager.instance().setActiveCoordinatesFormat(Enum.valueOf(CoordinatesFormat.class, (String) coordinatesFormatProperty));
		}

		// Test if the Properties does contains the appropriate mappings
		Object objectClassProperty = properties.get(OBJECT_CLASS_PROPERTY);
		if(!(objectClassProperty instanceof String)) {
			throw new MappingException("Loaded mapping file seems not to be a valid mapping file: no ObjectClass property found");
		}
		try {
			Class<?> objectClassToTest = Class.forName((String) objectClassProperty);

			if(objectClassToTest != objectClass) {
				throw new MappingException("Loaded mappings do not match with the object to export");
			}
		} catch (ClassNotFoundException e) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage());
			Logger.getLogger(getClass().getName()).log(Level.FINE, e.getMessage(), e);
			throw new MappingException("Could not find class " + objectClassProperty);
		}

		// Load mapping
		List<SimpleMappingRow> rows = new ArrayList<>();

		for(Entry<Object, Object> property : properties.entrySet()) {
			try {
				propertyToRow(objectClass, rows, property);
			} catch (SecurityException e) {
				Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage());
				Logger.getLogger(getClass().getName()).log(Level.FINE, e.getMessage(), e);
			}
		}

		return rows;
	}

	/**
	 * Property to row.
	 *
	 * @param objectClass the object class
	 * @param rows the rows
	 * @param property the property
	 * @throws SecurityException the security exception
	 */
	private void propertyToRow(Class<?> objectClass, List<SimpleMappingRow> rows, Entry<Object, Object> property) throws SecurityException {

		String key = (String)property.getKey();

		if(!key.equals(OBJECT_CLASS_PROPERTY) 
				&& !key.equals(SIMPLE_MAPPING_FILE_MARK_PROPERTY)
				&& !key.equals(DATE_FORMAT_PROPERTY)
				&& !key.equals(COORDINATES_FORMAT_PROPERTY)) {
			String value = (String)property.getValue();

			String objectFieldName = key;
			String outputColumn = value;
			String outputColumnHeader = null;

			try {
				Matcher matcher = MAPPING_OUTPUT_COLUMN_PROPERTIES_PARSE_PATTERN.matcher(value);
				matcher.find();
				outputColumn = matcher.group(1);
				outputColumnHeader = matcher.group(2);
			} catch(Exception e) {
				Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage());
				Logger.getLogger(getClass().getName()).log(Level.FINE, e.getMessage(), e);
			}

			SimpleMappingRow row = new SimpleMappingRow(outputColumnHeader, outputColumn, ObjectUtils.getField(objectClass, objectFieldName));
			rows.add(row);
		}

	}

	/**
	 * Rows to properties.
	 *
	 * @param objectClass the object class
	 * @param rows the rows
	 * @return the properties
	 */
	private Properties rowsToProperties(Class<?> objectClass, List<SimpleMappingRow> rows) {

		Properties properties = new Properties();

		properties.put(SIMPLE_MAPPING_FILE_MARK_PROPERTY, SIMPLE_MAPPING_FILE_MARK_VALUE);

		properties.put(DATE_FORMAT_PROPERTY, DateFormatManager.instance().getActiveDateFormatPattern());

		properties.put(COORDINATES_FORMAT_PROPERTY, CoordinatesFormatManager.instance().getActiveCoordinatesFormat().name());

		properties.put(OBJECT_CLASS_PROPERTY, objectClass.getCanonicalName());

		for(SimpleMappingRow row : rows) {
			properties.put(row.getObjectField().getName(), MessageFormat.format(MAPPING_OUTPUT_COLUMN_PROPERTIES_FORMAT_PATTERN, row.getOutputColumn(), row.getOutputColumnHeader()));
		}

		return properties;
	}
}
