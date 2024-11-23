package org.jtools.utils;

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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
// TODO: Auto-generated Javadoc

/**
 * The Class CommonUtils.
 */
public class CommonUtils {

	/** The Constant PROPERTIES_FILES_EXTENSION. */
	public static final String PROPERTIES_FILES_EXTENSION = ".properties";

	/** The Constant EXCEL_FILES_EXTENSION. */
	public static final String EXCEL_FILES_EXTENSION = ".xls";

	/** The Constant LOAD_EXCEL_DIALOG_TITLE. */
	public static final String LOAD_EXCEL_DIALOG_TITLE = "Select the input " + CommonUtils.EXCEL_FILES_EXTENSION + " file";

	/**
	 * Instantiates a new common utils.
	 */
	private CommonUtils() {}
		
		/**
		 * Load properties.
		 *
		 * @param propertiesFilePath the properties file path
		 * @return the properties
		 * @throws IOException Signals that an I/O exception has occurred.
		 */
		public static Properties loadProperties(String propertiesFilePath) throws IOException {
		Properties properties = new Properties();

		File file = new File(propertiesFilePath);
		if (!file.exists()) {
			return properties;
		}

		FileInputStream fileInputStream = null;
		try {
			fileInputStream = new FileInputStream(file);
			properties.load(fileInputStream);
		} finally {
			if (fileInputStream != null) {
				fileInputStream.close();
			}
		}
		return properties;
	}
	
	/**
	 * Save properties.
	 *
	 * @param properties the properties
	 * @param propertiesFilePath the properties file path
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void saveProperties(Properties properties, String propertiesFilePath) throws IOException {
		File file = new File(propertiesFilePath);
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
	
	/**
	 * Choose file.
	 *
	 * @param dialogType the dialog type
	 * @param defaultFile the default file
	 * @param dialogTitle the dialog title
	 * @param fileExtension the file extension
	 * @return the file
	 */
	public static File chooseFile(int dialogType, File defaultFile, String dialogTitle, String fileExtension) {

		String currentDirectoryPath = ".";
		if (defaultFile != null) {
			if (defaultFile.isDirectory()) {
				currentDirectoryPath = defaultFile.getAbsolutePath();
			} else {
				currentDirectoryPath = defaultFile.getParentFile().getAbsolutePath();
			}
		}

		if(!fileExtension.startsWith(".")) {
			fileExtension = "." + fileExtension;
		}

		JFileChooser fileChooser = new JFileChooser(currentDirectoryPath);
		fileChooser.setDialogTitle(dialogTitle);

		if (defaultFile != null) {
			fileChooser.setSelectedFile(defaultFile);
		}

		fileChooser.setFileFilter(new ExtensionFileFilter(fileExtension));

		int result;
		if (dialogType == JFileChooser.OPEN_DIALOG) {
			result = fileChooser.showOpenDialog(null);
		} else if (dialogType == JFileChooser.SAVE_DIALOG) {
			result = fileChooser.showSaveDialog(null);
		} else {
			throw new IllegalArgumentException(
					"dialogType shall be one of JFileChooser.OPEN_DIALOG or JFileChooser.SAVE_DIALOG");
		}
		if (result == JFileChooser.APPROVE_OPTION) {

			File fileToBeSaved = fileChooser.getSelectedFile();

			if(!fileChooser.getSelectedFile().getAbsolutePath().endsWith(fileExtension)){
				fileToBeSaved = new File(fileChooser.getSelectedFile().getAbsolutePath() + fileExtension);
			}

			return fileToBeSaved;
		}
		return null;
	}

	/**
	 * Class list to array.
	 *
	 * @param list the list
	 * @return the class[]
	 */
	public static Class<?>[] classListToArray(List<Class<?>> list) {
		Class<?>[] array = new Class<?>[list.size()];
		list.toArray(array);
		return array;
	}

	/**
	 * String list to array.
	 *
	 * @param list the list
	 * @return the string[]
	 */
	public static String[] stringListToArray(List<String> list) {
		String[] array = new String[list.size()];
		list.toArray(array);
		return array;
	}

	/**
	 * String set to array.
	 *
	 * @param set the set
	 * @return the string[]
	 */
	public static String[] stringSetToArray(Set<String> set) {
		String[] array = new String[set.size()];
		set.toArray(array);
		return array;
	}

	/**
	 * Sort by value.
	 *
	 * @param <K> the key type
	 * @param <V> the value type
	 * @param map the map
	 * @return the map
	 */
	public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
		List<Entry<K, V>> list = new ArrayList<>(map.entrySet());
		list.sort(Entry.comparingByValue());

		Map<K, V> result = new LinkedHashMap<>();
		for (Entry<K, V> entry : list) {
			result.put(entry.getKey(), entry.getValue());
		}

		return result;
	}

	/**
	 * The Class ExtensionFileFilter.
	 *
	 * @author j4ckk0
	 */
	public static class ExtensionFileFilter extends FileFilter {

		/** The file extension. */
		private String fileExtension;

		/**
		 * Instantiates a new extension file filter.
		 *
		 * @param fileExtension the file extension
		 */
		public ExtensionFileFilter(String fileExtension) {
			this.fileExtension = fileExtension;
		}

		/**
		 * Gets the description.
		 *
		 * @return the description
		 */
		@Override
		public String getDescription() {
			return fileExtension + " files";
		}

		/**
		 * Accept.
		 *
		 * @param file the file
		 * @return true, if successful
		 */
		@Override
		public boolean accept(File file) {
			return file.isDirectory() || file.getName().endsWith(fileExtension);
		}
	}

}
