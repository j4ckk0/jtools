package org.jtools.mappings.simple.exporters;

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

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jtools.mappings.common.MappingUtils;
import org.jtools.mappings.common.MappingUtils.MappingCellStyleType;
import org.jtools.mappings.simple.SimpleMappingRow;
import org.jtools.utils.CommonUtils;
import org.jtools.utils.objects.ObjectInfoProvider;
// TODO: Auto-generated Javadoc

/**
 * The Class SimpleMappingExcelExporter.
 */
public class SimpleMappingExcelExporter implements ISimpleMappingExporter {

	/** The instance. */
	private static SimpleMappingExcelExporter instance;

	/**
	 * Instance.
	 *
	 * @return the simple mapping excel exporter
	 */
	public static SimpleMappingExcelExporter instance() {
		if (instance == null) {
			instance = new SimpleMappingExcelExporter();
		}
		return instance;
	}

	/**
	 * Instantiates a new simple mapping excel exporter.
	 */
	private SimpleMappingExcelExporter() {
		super();
	}

	/**
	 * Export data.
	 *
	 * @param <T>      the generic type
	 * @param data     the data
	 * @param mappings the mappings
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public <T> void exportData(List<T> data, List<SimpleMappingRow> mappings) throws IOException {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileFilter(new FileFilter() {

			@Override
			public String getDescription() {
				return CommonUtils.EXCEL_FILES_EXTENSION + " files";
			}

			@Override
			public boolean accept(File file) {
				return file.isDirectory() || file.getName().endsWith(CommonUtils.EXCEL_FILES_EXTENSION);
			}
		});

		int result = fileChooser.showSaveDialog(null);
		if (result == JFileChooser.APPROVE_OPTION) {
			try {

				File outputFile = fileChooser.getSelectedFile();
				if (!outputFile.getAbsolutePath().endsWith(CommonUtils.EXCEL_FILES_EXTENSION)) {
					outputFile = new File(outputFile.getAbsolutePath() + CommonUtils.EXCEL_FILES_EXTENSION);
				}

				SimpleMappingExcelExporter.instance().export(data, mappings, outputFile);

				int confirm = JOptionPane.showConfirmDialog(null, "Do you want to open the file ?", "Export succeed",
						JOptionPane.YES_NO_OPTION);
				if (confirm == JOptionPane.YES_OPTION) {
					Desktop.getDesktop().open(outputFile);
				}

			} catch (IOException e) {
				Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage());
				Logger.getLogger(getClass().getName()).log(Level.FINE, e.getMessage(), e);
			}
		}
	}

	/**
	 * Export.
	 *
	 * @param <T>        the generic type
	 * @param data       the data
	 * @param mappings   the mappings
	 * @param outputFile the output file
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private <T> void export(List<T> data, List<SimpleMappingRow> mappings, File outputFile) throws IOException {
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("Data");

		writeHeaderLine(mappings, workbook, sheet);

		writeDataLines(data, mappings, workbook, sheet);

		FileOutputStream outputStream = new FileOutputStream(outputFile);
		workbook.write(outputStream);
		workbook.close();
	}

	/**
	 * Write header line.
	 *
	 * @param mappings the mappings
	 * @param workbook the workbook
	 * @param sheet    the sheet
	 */
	private void writeHeaderLine(List<SimpleMappingRow> mappings, XSSFWorkbook workbook, XSSFSheet sheet) {
		XSSFRow headerRow = sheet.createRow(0);

		// exclude the first column which is the ID field
		for (SimpleMappingRow mapping : mappings) {
			int i = MappingUtils.possibleColumns().indexOf(mapping.getOutputColumn());
			XSSFCell headerCell = headerRow.createCell(i);
			headerCell.setCellValue(mapping.getOutputColumnHeader());
			headerCell.setCellStyle(MappingUtils.getCellStyle(workbook, MappingCellStyleType.HEADER));
		}
	}

	/**
	 * Write data lines.
	 *
	 * @param <T>      the generic type
	 * @param data     the data
	 * @param mappings the mappings
	 * @param workbook the workbook
	 * @param sheet    the sheet
	 */
	private <T extends Object> void writeDataLines(List<T> data, List<SimpleMappingRow> mappings, XSSFWorkbook workbook,
			XSSFSheet sheet) {

		int rowCount = 1;
		for (T object : data) {
			XSSFRow row = sheet.createRow(rowCount);

			for (SimpleMappingRow mapping : mappings) {
				try {
					Field field = mapping.getObjectField();
					if (field != null) {

						Method getter = ObjectInfoProvider.getObjectInfo(object.getClass()).findGetter(field);
						if (getter != null) {
							Object valueObject = getter.invoke(object);

							int i = MappingUtils.possibleColumns().indexOf(mapping.getOutputColumn());
							XSSFCell cell = row.createCell(i);

							MappingUtils.setCellValue(workbook, cell, valueObject);
						} else {
							Logger.getLogger(getClass().getName()).log(Level.FINE,
									"getter not found for field " + field.getName());
						}
					}
				} catch (Exception e) {
					Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage());
					Logger.getLogger(getClass().getName()).log(Level.FINE, e.getMessage(), e);
				}
			}

			rowCount++;
		}
	}

}
