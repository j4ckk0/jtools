package org.jtools.mappings.block.exporters;

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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jtools.mappings.block.BlockMapping;
import org.jtools.mappings.block.BlockMappingRow;
import org.jtools.mappings.common.MappingUtils;
import org.jtools.mappings.common.MappingUtils.MappingCellStyleType;
import org.jtools.utils.CommonUtils;
import org.jtools.utils.objects.ObjectInfoProvider;
// TODO: Auto-generated Javadoc

/**
 * The Class BlockMappingExcelExporter.
 */
public class BlockMappingExcelExporter implements IBlockMappingExporter {

	/** The instance. */
	private static BlockMappingExcelExporter instance;

	/** The rows map. */
	private final Map<XSSFSheet, Map<Integer, XSSFRow>> rowsMap = new HashMap<>();

	/** The row index. */
	private int rowIndex;

	/**
	 * Instance.
	 *
	 * @return the block mapping excel exporter
	 */
	public static BlockMappingExcelExporter instance() {
		if (instance == null) {
			instance = new BlockMappingExcelExporter();
		}
		return instance;
	}

	/**
	 * Export data.
	 *
	 * @param <T> the generic type
	 * @param dataList the data list
	 * @param mappings the mappings
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public <T> void exportData(List<T> dataList, BlockMapping<?> mappings) throws IOException {
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

				BlockMappingExcelExporter.instance().export(dataList, mappings, outputFile);

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
	 * @param <T> the generic type
	 * @param dataList the data list
	 * @param mapping the mapping
	 * @param outputFile the output file
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private <T> void export(List<T> dataList, BlockMapping<?> mapping, File outputFile) throws IOException {
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("Data");

		rowIndex = -1;
		writeHeader(Collections.singletonList(mapping), workbook, sheet, getRow(sheet, ++rowIndex));

		for (Object data : dataList) {
			writeData(data, mapping, workbook, sheet, getRow(sheet, ++rowIndex));
		}

		FileOutputStream outputStream = new FileOutputStream(outputFile);
		workbook.write(outputStream);
		workbook.close();
	}

	/**
	 * Write header.
	 *
	 * @param mappings the mappings
	 * @param workbook the workbook
	 * @param sheet the sheet
	 * @param row the row
	 */
	private void writeHeader(List<BlockMapping<?>> mappings, XSSFWorkbook workbook, XSSFSheet sheet, XSSFRow row) {
		List<BlockMapping<?>> subMappings = new ArrayList<>();

		for (BlockMapping<?> mapping : mappings) {
			for (BlockMappingRow mappingRow : mapping.getRows()) {

				String headerValue = mappingRow.getHeaderValue();
				if (headerValue != null) {
					int fromColumn = MappingUtils.possibleColumns.indexOf(mappingRow.getFromColumn());
					int toColumn = MappingUtils.possibleColumns.indexOf(mappingRow.getToColumn());

					XSSFCell cell = row.createCell(fromColumn);

					cell.setCellValue(headerValue);
					cell.setCellStyle(MappingUtils.getCellStyle(workbook, MappingCellStyleType.HEADER));

					if (fromColumn != toColumn) {
						CellRangeAddress cellRangeAddress = new CellRangeAddress(rowIndex, rowIndex, fromColumn,
								toColumn);
						sheet.addMergedRegion(cellRangeAddress);

						formatHeaderRegion(sheet, cellRangeAddress);
					}
				}

				BlockMapping<?> subBlockMapping = mappingRow.getSubBlockMapping();
				if (subBlockMapping != null) {
					subMappings.add(mappingRow.getSubBlockMapping());
				}
			}
		}

		if (!subMappings.isEmpty()) {
			writeHeader(subMappings, workbook, sheet, getRow(sheet, ++rowIndex));
		}

	}

	/**
	 * Write data.
	 *
	 * @param object the object
	 * @param mapping the mapping
	 * @param workbook the workbook
	 * @param sheet the sheet
	 * @param row the row
	 */
	private void writeData(Object object, BlockMapping<?> mapping, XSSFWorkbook workbook, XSSFSheet sheet, XSSFRow row) {
		// Case of a collection as value
		if (Collection.class.isAssignableFrom(object.getClass())) {
			int rowIndexBefore = rowIndex;

			boolean first = true;
			for (Object subObject : ((Collection<?>) object)) {
				if (first) {
					writeData(subObject, mapping, workbook, sheet, row);
					first = false;
				} else {
					writeData(subObject, mapping, workbook, sheet, getRow(sheet, ++rowIndex));
				}
			}

			int rowIndexAfter = rowIndex;

			int startColumn = getStartColumn(mapping);
			for (int i = 0; i < startColumn; i++) {
				sheet.addMergedRegion(new CellRangeAddress(rowIndexBefore, rowIndexAfter, i, i));
			}

		}

		// Case of a single object as value
		else {

			for (BlockMappingRow mappingRow : mapping.getRows()) {

				try {
					int fromColumn = MappingUtils.possibleColumns.indexOf(mappingRow.getFromColumn());
					int toColumn = MappingUtils.possibleColumns.indexOf(mappingRow.getToColumn());

					Field field = mappingRow.getObjectField();
					if (field != null) {

						Method getter = ObjectInfoProvider.getObjectInfo(object.getClass()).findGetter(field);
						if (getter != null) {
							Object valueObject = getter.invoke(object);
							if (valueObject != null) {
								BlockMapping<?> subBlockMapping = mappingRow.getSubBlockMapping();
								if (subBlockMapping != null) {
									writeData(valueObject, subBlockMapping, workbook, sheet, row);
								} else {
									XSSFCell cell = row.createCell(fromColumn);
									cell.setCellStyle(MappingUtils.getCellStyle(workbook, MappingCellStyleType.BODY));

									CellRangeAddress cellRange = new CellRangeAddress(rowIndex, rowIndex, fromColumn,
											toColumn);

									if (valueObject instanceof Boolean)
										cell.setCellValue((Boolean) valueObject);
									else if (valueObject instanceof Double)
										cell.setCellValue((double) valueObject);
									else if (valueObject instanceof Float)
										cell.setCellValue((float) valueObject);
									else if (valueObject instanceof Date) {
										cell.setCellValue((Date) valueObject);
										cell.setCellStyle(
												MappingUtils.getCellStyle(workbook, MappingCellStyleType.DATE));
									} else {
										cell.setCellValue(valueObject.toString());
									}

									if (fromColumn != toColumn) {
										sheet.addMergedRegion(cellRange);
									}
								}
							}
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
		}
	}

	/**
	 * Gets the start column.
	 *
	 * @param mapping the mapping
	 * @return the start column
	 */
	private int getStartColumn(BlockMapping<?> mapping) {
		Integer startColumn = null;
		for (BlockMappingRow mappingRow : mapping.getRows()) {
			int mappingFromColumn = MappingUtils.possibleColumns.indexOf(mappingRow.getFromColumn());
			if (startColumn == null || mappingFromColumn < startColumn) {
				startColumn = mappingFromColumn;
			}
		}
		return startColumn;
	}

	/**
	 * Gets the row.
	 *
	 * @param sheet the sheet
	 * @param rowIndex the row index
	 * @return the row
	 */
	private XSSFRow getRow(XSSFSheet sheet, int rowIndex) {
		Map<Integer, XSSFRow> sheetRowsMap = rowsMap.get(sheet);
		if (sheetRowsMap == null) {
			sheetRowsMap = new HashMap<>();
			rowsMap.put(sheet, sheetRowsMap);
		}

		XSSFRow row = sheetRowsMap.get(rowIndex);
		if (row == null) {
			row = sheet.createRow(rowIndex);

			sheetRowsMap.put(rowIndex, row);
			rowsMap.put(sheet, sheetRowsMap);
		}
		return row;
	}

	/**
	 * Format header region.
	 *
	 * @param sheet the sheet
	 * @param cellRangeAddress the cell range address
	 */
	private void formatHeaderRegion(XSSFSheet sheet, CellRangeAddress cellRangeAddress) {
		RegionUtil.setBorderBottom(BorderStyle.THIN, cellRangeAddress, sheet);
		RegionUtil.setBorderTop(BorderStyle.THIN, cellRangeAddress, sheet);
		RegionUtil.setBorderLeft(BorderStyle.THIN, cellRangeAddress, sheet);
		RegionUtil.setBorderRight(BorderStyle.THIN, cellRangeAddress, sheet);

	}

}
