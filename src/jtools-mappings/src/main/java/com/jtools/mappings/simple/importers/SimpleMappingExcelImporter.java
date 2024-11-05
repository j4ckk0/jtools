/**
 * 
 */
package com.jtools.mappings.simple.importers;

/*-
 * #%L
 * Java Tools - Mappings
 * %%
 * Copyright (C) 2024 j4ckk0
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
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.jtools.mappings.common.MappingUtils;
import com.jtools.mappings.common.importers.ExcelImportConfigPanel;
import com.jtools.mappings.simple.SimpleMappingRow;
import com.jtools.utils.objects.ObjectInfoProvider;

/**
 * @author j4ckk0
 *
 */
public class SimpleMappingExcelImporter extends ASimpleMappingImporter {

	private static SimpleMappingExcelImporter instance;

	public static SimpleMappingExcelImporter instance() {
		if (instance == null) {
			instance = new SimpleMappingExcelImporter();
		}
		return instance;
	}

	public <T extends Object> List<T> importData(Class<T> objectClass, List<SimpleMappingRow> mappings)
			throws IOException {

		ExcelImportConfigPanel excelImporterDefinitionPanel = new ExcelImportConfigPanel(objectClass);

		int result = JOptionPane.showConfirmDialog(null, excelImporterDefinitionPanel, "Excel importer",
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

		if (result == JOptionPane.OK_OPTION) {
			return importData(objectClass, mappings, excelImporterDefinitionPanel.getSelectedFile(),
					excelImporterDefinitionPanel.getFirstDataRow());
		}

		return null;
	}

	public <T extends Object> List<T> importData(Class<T> objectClass, List<SimpleMappingRow> mappings, File file,
			int firstDataRowIndex) {
		List<T> data = new ArrayList<>();

		try {
			// Create Workbook instance holding reference to .xlsx file
			XSSFWorkbook workbook = new XSSFWorkbook(file);

			// Get first/desired sheet from the workbook
			XSSFSheet sheet = workbook.getSheetAt(0);

			// Iterate through each rows one by one
			int rowIndex = -1;
			Iterator<Row> rowIterator = sheet.iterator();
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();

				rowIndex++;
				if (rowIndex < firstDataRowIndex) {
					continue;
				}

				Constructor<T> constructor = objectClass.getConstructor();
				T object = constructor.newInstance();

				// For each row, iterate through all the columns
				// Fill the object's attributes
				Iterator<Cell> cellIterator = row.cellIterator();

				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();

					String inputColumn = MappingUtils.possibleColumns.get(cell.getColumnIndex());

					SimpleMappingRow mapping = getMapping(mappings, inputColumn);
					if (mapping != null) {
						try {
							Field objectField = mapping.getObjectField();
							Method setter = ObjectInfoProvider.getObjectInfo(object.getClass()).findSetter(objectField);
							if (setter != null) {
								if (setter.getParameterCount() == 1) {
									Parameter parameter = setter.getParameters()[0];
									Class<?> parameterType = parameter.getType();
									
									try {

										MappingUtils.setValueFromCell(cell, object, setter);

									} catch (Exception ex) {
										Logger.getLogger(getClass().getName()).log(Level.SEVERE,
												"An error occured while retrieving cell value with type "
														+ parameterType.getName() + " and cell type "
														+ cell.getCellType() + " for field " + objectField.getName()
														+ ". Cause: " + ex.getMessage());
										Logger.getLogger(getClass().getName()).log(Level.FINE, ex.getMessage(), ex);
									}
								} else {
									Logger.getLogger(getClass().getName()).log(Level.SEVERE,
											"setter " + setter.getName() + " has " + setter.getParameterCount()
													+ " parameters. Unsupported usecase");
								}
							} else {
								Logger.getLogger(getClass().getName()).log(Level.FINE,
										"setter not found for field " + objectField.getName());
							}
						} catch (Exception e) {
							Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage());
							Logger.getLogger(getClass().getName()).log(Level.FINE, e.getMessage(), e);
						}
					} else {
						Logger.getLogger(getClass().getName()).log(Level.WARNING,
								"No mapping found for column " + inputColumn);
					}
				}

				// Add the object to the list
				data.add(object);
			}

			workbook.close();

		} catch (Exception e) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage());
			Logger.getLogger(getClass().getName()).log(Level.FINE, e.getMessage(), e);
		}

		return data;
	}

	private SimpleMappingRow getMapping(List<SimpleMappingRow> mappings, String inputColumn) {
		for (SimpleMappingRow mapping : mappings) {
			if (mapping.getOutputColumn().equals(inputColumn)) {
				return mapping;
			}
		}
		return null;
	}

}
