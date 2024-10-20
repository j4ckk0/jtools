/**
 * 
 */
package com.jtools.generic.data.io;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFileChooser;

import org.javers.core.JaversBuilder;
import org.javers.core.json.JsonConverter;

import com.jtools.generic.data.DataException;
import com.jtools.utils.CommonUtils;

/**
 * @author j4ckk0
 *
 */
public class DataFileManager {

	//////////////////////////////
	//
	// Constants and fields
	//
	//////////////////////////////

	private static DataFileManager INSTANCE;

	public static final String SAVE_MAPPING_DIALOG_TITLE = "Select a mapping file";

	private static final String DATA_FILE_MARK_PROPERTY = "jojop";
	private static final String DATA_FILE_MARK_VALUE = "Java Object as JsOnified Properties";

	private static final String DATA_CLASS_PROPERTY = "DATA_CLASS";

	private static final String DATA_PROPERTY = "DATA_OBJECT";

	public static final String SAVE_DATA_DIALOG_TITLE = "Select data file";

	public static final String LOAD_DATA_DIALOG_TITLE = "Select data file";

	public static final String DATA_FILE_EXTENSION = ".jojop"; // Java Object as JsOnified Properties

	private static final JsonConverter jsonConverter = JaversBuilder.javers().build().getJsonConverter();

	//////////////////////////////
	//
	// Constructors
	//
	//////////////////////////////

	private DataFileManager() {

	}

	//////////////////////////////
	//
	// Public methods
	//
	//////////////////////////////

	public static DataFileManager instance() {
		if (INSTANCE == null) {
			INSTANCE = new DataFileManager();
		}
		return INSTANCE;
	}

	public void save(List<?> dataList) throws IOException {
		File outputFile = CommonUtils.chooseFile(JFileChooser.SAVE_DIALOG, new File("."), SAVE_DATA_DIALOG_TITLE,
				DATA_FILE_EXTENSION);
		if (outputFile != null) {
			Properties properties = dataToProperties(dataList);

			File file = new File(outputFile.getAbsolutePath());
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

	public List<?> load() throws InstantiationException, DataException {
		File inputFilePath = CommonUtils.chooseFile(JFileChooser.OPEN_DIALOG, new File("."), LOAD_DATA_DIALOG_TITLE,
				DATA_FILE_EXTENSION);

		if(inputFilePath != null) {
			return load(inputFilePath.getAbsolutePath());
		}
		
		return null;
	}

	private List<?> load(String inputFilePath) throws InstantiationException, DataException {
		Properties properties;
		try {
			properties = CommonUtils.loadProperties(inputFilePath);
		} catch (Exception e) {
			throw new InstantiationException("An error occured while loading data: " + e.getMessage());
		}

		return propertiesToData(properties);
	}

	//////////////////////////////
	//
	// Private methods
	//
	//////////////////////////////

	private Properties dataToProperties(List<?> dataList) {

		Properties properties = new Properties();

		properties.put(DATA_FILE_MARK_PROPERTY, DATA_FILE_MARK_VALUE);

		for (int i = 0; i < dataList.size(); i++) {
			Object data = dataList.get(i);

			properties.put(DATA_CLASS_PROPERTY + "." + i, data.getClass().getCanonicalName());

			String json = jsonConverter.toJson(data);
			properties.put(DATA_PROPERTY + "." + i, json);
		}

		return properties;
	}

	private List<?> propertiesToData(Properties properties) throws DataException {
		// Test if the Properties is valid
		Object fileMarkProperty = properties.get(DATA_FILE_MARK_PROPERTY);
		if (fileMarkProperty == null) {
			throw new DataException("Loaded data file seems not to be a valid data file");
		}

		// Load data
		List<Object> dataList = new ArrayList<>();

		for (Entry<Object, Object> property : properties.entrySet()) {
			try {
				String key = (String) property.getKey();

				if (key.contains(DATA_PROPERTY)) {

					int index = Integer.parseInt(key.replace(DATA_PROPERTY + ".", "").trim());

					String dataClassName = (String) properties.get(DATA_CLASS_PROPERTY + "." + index);
					Class<?> dataClass = Class.forName(dataClassName);

					String json = (String) property.getValue();
					Object data = jsonConverter.fromJson(json, dataClass);
					dataList.add(data);
				}

			} catch (SecurityException | ClassNotFoundException e) {
				Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
			}
		}

		return dataList;
	}
}
