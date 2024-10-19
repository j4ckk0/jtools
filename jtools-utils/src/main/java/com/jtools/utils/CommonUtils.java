/**
 * 
 */
package com.jtools.utils;

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

/**
 * @author j4ckk0
 *
 */
public class CommonUtils {

	public static final String PROPERTIES_FILES_EXTENSION = ".properties";

	public static final String EXCEL_FILES_EXTENSION = ".xls";

	public static final String LOAD_EXCEL_DIALOG_TITLE = "Select the input " + CommonUtils.EXCEL_FILES_EXTENSION + " file";

	/**
	 * 
	 * @param propertiesFilePath
	 * @return
	 * @throws IOException
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
	 * 
	 * @param properties
	 * @param propertiesFilePath
	 * @throws IOException
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
	 * 
	 * @param dialogType  JFileChooser.OPEN_DIALOG or JFileChooser.SAVE_DIALOG
	 * @param defaultFile
	 * @return
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
	 * 
	 * @param list
	 * @return
	 */
	public static Class<?>[] classListToArray(List<Class<?>> list) {
		Class<?>[] array = new Class<?>[list.size()];
		list.toArray(array);
		return array;
	}

	/**
	 * 
	 * @param list
	 * @return
	 */
	public static String[] stringListToArray(List<String> list) {
		String[] array = new String[list.size()];
		list.toArray(array);
		return array;
	}

	/**
	 * 
	 * @param set
	 * @return
	 */
	public static String[] stringSetToArray(Set<String> set) {
		String[] array = new String[set.size()];
		set.toArray(array);
		return array;
	}

	/**
	 * 
	 * @param <K>
	 * @param <V>
	 * @param map
	 * @return
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
	 * 
	 * @author j4ckk0
	 *
	 */
	public static class ExtensionFileFilter extends FileFilter {

		private String fileExtension;

		public ExtensionFileFilter(String fileExtension) {
			this.fileExtension = fileExtension;
		}

		@Override
		public String getDescription() {
			return fileExtension + " files";
		}

		@Override
		public boolean accept(File file) {
			return file.isDirectory() || file.getName().endsWith(fileExtension);
		}
	}

}
