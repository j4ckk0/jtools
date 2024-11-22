package org.jtools.mappings.block.io;

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
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Instant;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFileChooser;

import org.apache.commons.collections4.map.HashedMap;
import org.javers.core.JaversBuilder;
import org.javers.core.json.JsonConverter;
import org.jtools.mappings.block.BlockMapping;
import org.jtools.mappings.common.MappingException;
import org.jtools.utils.CommonUtils;
import org.jtools.utils.dates.DateFormatManager;
import org.jtools.utils.geo.CoordinatesFormatManager;
import org.jtools.utils.geo.CoordinatesFormatManager.CoordinatesFormat;
public class BlockMappingFileManager {

	//////////////////////////////
	//
	// Constants and fields
	//
	//////////////////////////////

	private static final String BLOCK_MAPPING_FILE_MARK_PROPERTY = "jobm";
	private static final String BLOCK_MAPPING_FILE_MARK_VALUE = "Java Object Block Mapping";

	private static final String OBJECT_CLASS_PROPERTY = "object.class";

	private static final String BLOCK_MAPPING_PROPERTY = "block.mapping";

	private static final String DATE_FORMAT_PROPERTY = "date.format";

	private static final String COORDINATES_FORMAT_PROPERTY = "coordinates.format";

	public static final String SAVE_BLOCK_MAPPING_DIALOG_TITLE = "Select a block mapping file";

	public static final String LOAD_BLOCK_MAPPING_DIALOG_TITLE = "Select a block mapping file";

	public static final String BLOCK_MAPPING_FILE_EXTENSION = ".jobm"; // Java Object Block Mapping

	private static BlockMappingFileManager instance;

	private static final JsonConverter jsonConverter = JaversBuilder.javers().build().getJsonConverter();

	private final Map<UUID, String> mappingsFilePaths = new HashedMap<>();

	//////////////////////////////
	//
	// Constructors
	//
	//////////////////////////////

	private BlockMappingFileManager() {

	}

	//////////////////////////////
	//
	// Public methods
	//
	//////////////////////////////

	public static BlockMappingFileManager instance() {
		if (instance == null) {
			instance = new BlockMappingFileManager();
		}
		return instance;
	}
	@SuppressWarnings({ "unchecked" })
	public <E extends Object> BlockMapping<E> loadMapping(String mappingFilepath) throws InstantiationException, MappingException {
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
			throw new InstantiationException(e.getMessage());
		}

		// Rows
		try {
			BlockMapping<E> mapping = (BlockMapping<E>) propertiesToMapping(properties, objectClass);
			mappingsFilePaths.put(mapping.getId(), mappingFilepath);
			return mapping;

		}catch (Exception e) {
			throw new InstantiationException("An error occured while loading mapping editor: " + e.getMessage());
		}
	}
	public void save(BlockMapping<?> mapping) throws IOException {
		String mappingFilepath = getMappingFilepath(mapping.getId());

		File defaultFile = null;
		if (mappingFilepath != null && mappingFilepath.length() != 0) {
			defaultFile = new File(mappingFilepath);
		}

		File mappingFile = CommonUtils.chooseFile(JFileChooser.SAVE_DIALOG, defaultFile,
				SAVE_BLOCK_MAPPING_DIALOG_TITLE, BLOCK_MAPPING_FILE_EXTENSION);
		if (mappingFile != null) {

			Properties properties = mappingToProperties(mapping.getObjectClass(), mapping);

			mappingsFilePaths.put(mapping.getId(), mappingFile.getAbsolutePath());

			File file = new File(mappingFile.getAbsolutePath());
			if (!file.exists()) {
				file.createNewFile();
			}
			FileOutputStream fileOutputStream = null;
			try {
				fileOutputStream = new FileOutputStream(file);
				properties.store(fileOutputStream, Instant.now().toString());
			} finally {
				if (fileOutputStream != null) {
					fileOutputStream.close();
				}
			}
		}
	}
	public String getMappingFilepath(UUID id) {
		return mappingsFilePaths.get(id);
	}

	//////////////////////////////
	//
	// Private methods
	//
	//////////////////////////////

	private BlockMapping<?> propertiesToMapping(Properties properties, Class<?> objectClass) throws MappingException {
		// Test if the Properties is valid
		Object fileMarkProperty = properties.get(BLOCK_MAPPING_FILE_MARK_PROPERTY);
		if (fileMarkProperty == null) {
			throw new MappingException("Loaded mapping file seems not to be a valid block mapping file");
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
			CoordinatesFormatManager.instance().setActiveCoordinatesFormat(Enum.valueOf(CoordinatesFormat.class, (String)coordinatesFormatProperty));
		}

		// Test if the Properties does contains the appropriate mappings
		Object objectClassProperty = properties.get(OBJECT_CLASS_PROPERTY);
		if (!(objectClassProperty instanceof String)) {
			throw new MappingException(
					"Loaded mapping file seems not to be a valid mapping file: no ObjectClass property found");
		}
		try {
			Class<?> objectClassToTest = Class.forName((String) objectClassProperty);

			if (objectClassToTest != objectClass) {
				throw new MappingException("Loaded mappings do not match with the object to export");
			}
		} catch (ClassNotFoundException e) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage());
			Logger.getLogger(getClass().getName()).log(Level.FINE, e.getMessage(), e);
			throw new MappingException("Could not find class " + objectClassProperty);
		}

		// Load mapping
		for (Entry<Object, Object> property : properties.entrySet()) {
			try {
				String key = (String) property.getKey();

				if (key.equals(BLOCK_MAPPING_PROPERTY)) {
					String json = (String) property.getValue();
					return jsonConverter.fromJson(json, BlockMapping.class);
				}

			} catch (SecurityException e) {
				Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage());
				Logger.getLogger(getClass().getName()).log(Level.FINE, e.getMessage(), e);
			}
		}

		return null;
	}

	private Properties mappingToProperties(Class<?> objectClass, BlockMapping<?> blockMapping) {

		Properties properties = new Properties();

		properties.put(BLOCK_MAPPING_FILE_MARK_PROPERTY, BLOCK_MAPPING_FILE_MARK_VALUE);
		
		properties.put(DATE_FORMAT_PROPERTY, DateFormatManager.instance().getActiveDateFormatPattern());
		
		properties.put(COORDINATES_FORMAT_PROPERTY, CoordinatesFormatManager.instance().getActiveCoordinatesFormat().name());

		properties.put(OBJECT_CLASS_PROPERTY, objectClass.getCanonicalName());

		String json = jsonConverter.toJson(blockMapping);
		properties.put(BLOCK_MAPPING_PROPERTY, json);

		return properties;
	}
}