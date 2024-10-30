/**
 * 
 */
package com.jtools.mappings.simple.io;

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

import com.jtools.mappings.common.MappingException;
import com.jtools.mappings.simple.SimpleMapping;
import com.jtools.mappings.simple.SimpleMappingRow;
import com.jtools.utils.CommonUtils;
import com.jtools.utils.dates.DateFormatManager;
import com.jtools.utils.geo.CoordinatesFormatManager;
import com.jtools.utils.geo.CoordinatesFormatManager.CoordinatesFormat;
import com.jtools.utils.objects.ObjectUtils;

/**
 * @author j4ckk0
 *
 */
public class SimpleMappingFileManager {

	//////////////////////////////
	//
	// Constants and fields
	//
	//////////////////////////////

	private static final Pattern MAPPING_OUTPUT_COLUMN_PROPERTIES_PARSE_PATTERN = Pattern.compile("(.+) \\[(.+)\\]");
	private static final String MAPPING_OUTPUT_COLUMN_PROPERTIES_FORMAT_PATTERN = "{0} [{1}]";

	private static final String SIMPLE_MAPPING_FILE_MARK_PROPERTY = "josm";
	private static final String SIMPLE_MAPPING_FILE_MARK_VALUE = "Java Object Simple Mapping";

	private static final String OBJECT_CLASS_PROPERTY = "object.class";

	private static final String DATE_FORMAT_PROPERTY = "date.format";

	private static final String COORDINATES_FORMAT_PROPERTY = "coordinates.format";

	public static final String SAVE_SIMPLE_MAPPING_DIALOG_TITLE = "Select a simple mapping file";

	public static final String LOAD_SIMPLE_MAPPING_DIALOG_TITLE = "Select a simple mapping file";

	public static final String SIMPLE_MAPPING_FILE_EXTENSION = ".josm"; // Java Object Simple Mapping

	private static SimpleMappingFileManager instance;

	private final Map<UUID, String> mappingsFilePaths = new HashedMap<>();

	//////////////////////////////
	//
	// Constructors
	//
	//////////////////////////////

	private SimpleMappingFileManager() {

	}

	//////////////////////////////
	//
	// Public methods
	//
	//////////////////////////////

	public static SimpleMappingFileManager instance() {
		if(instance == null) {
			instance = new SimpleMappingFileManager();
		}
		return instance;
	}

	/**
	 * 
	 * @param mappingFilepath
	 * @return
	 * @throws InstantiationException
	 * @throws MappingException 
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
	 * 
	 * @param objectClass
	 * @return
	 */
	public String getDefaultMappingFilePath(Class<?> objectClass) {
		return "./resources/" + objectClass.getName() + ".properties";
	}

	/**
	 * 
	 * @param objectClass
	 * @return
	 */
	public File getDefaultMappingFile(Class<?> objectClass) {
		return new File(getDefaultMappingFilePath(objectClass));
	}

	/**
	 * 
	 * @param mapping
	 * @throws IOException 
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
	 * 
	 * @param id
	 * @return
	 */
	public String getMappingFilepath(UUID id) {
		return mappingsFilePaths.get(id);
	}

	//////////////////////////////
	//
	// Private methods
	//
	//////////////////////////////

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
