package org.jtools.mappings.common;

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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.poi.hssf.util.HSSFColor.HSSFColorPredefined;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFCreationHelper;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jtools.mappings.common.apachepoi.MergedRegion;
import org.jtools.utils.CommonUtils;
import org.jtools.utils.dates.DateFormatManager;
import org.jtools.utils.objects.ObjectUtils;
// TODO: Auto-generated Javadoc

/**
 * The Class MappingUtils.
 */
public class MappingUtils {

	/**
	 * Instantiates a new mapping utils.
	 */
	private MappingUtils() {

	}

	/**
	 * The Enum MappingFontType.
	 */
	public enum MappingFontType {

		/** The header. */
		HEADER,
		/** The body. */
		BODY;

	}

	/**
	 * The Enum MappingCellStyleType.
	 */
	public enum MappingCellStyleType {

		/** The header. */
		HEADER,
		/** The body. */
		BODY,
		/** The date. */
		DATE;

	}

	/** The Constant fontsMap. */
	private static final Map<Workbook, Map<MappingFontType, XSSFFont>> fontsMap = new HashMap<>();

	/** The Constant cellTypesMap. */
	private static final Map<Workbook, Map<MappingCellStyleType, XSSFCellStyle>> cellTypesMap = new HashMap<>();

	/** The Constant ALL_LETTERS. */
	private static final char[] ALL_LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

	/** The Constant possibleColumns. */
	public static final List<String> possibleColumns = new ArrayList<>();
	static {
		// Round 1
		for (int i = 0; i < ALL_LETTERS.length; i++) {
			possibleColumns.add("" + ALL_LETTERS[i]);
		}

		// Round 2
		for (int i = 0; i < ALL_LETTERS.length; i++) {
			for (int j = 0; j < ALL_LETTERS.length; j++) {
				possibleColumns.add("" + ALL_LETTERS[i] + ALL_LETTERS[j]);
			}
		}
	}

	/**
	 * Gets the columns range as list.
	 *
	 * @param fromColumn the from column
	 * @param toColumn   the to column
	 * @return the columns range as list
	 */
	public static List<String> getColumnsRangeAsList(String fromColumn, String toColumn) {
		List<String> possibleColumnsSubset = new ArrayList<>();

		boolean addingColumnFlag = false;

		for (String column : possibleColumns) {

			if (column.equals(fromColumn)) {
				addingColumnFlag = true;
			}

			if (addingColumnFlag) {
				possibleColumnsSubset.add(column);
			}

			if (column.equals(toColumn)) {
				addingColumnFlag = false;
			}
		}

		return possibleColumnsSubset;
	}

	/**
	 * Gets the columns range as array.
	 *
	 * @param fromColumn the from column
	 * @param toColumn   the to column
	 * @return the columns range as array
	 */
	public static String[] getColumnsRangeAsArray(String fromColumn, String toColumn) {
		return CommonUtils.stringListToArray(getColumnsRangeAsList(fromColumn, toColumn));
	}

	/**
	 * Gets the possible columns.
	 *
	 * @return the possible columns
	 */
	public static String[] getPossibleColumns() {
		return CommonUtils.stringListToArray(possibleColumns);
	}

	/**
	 * Gets the font.
	 *
	 * @param workbook the workbook
	 * @param key      the key
	 * @return the font
	 */
	private static Font getFont(XSSFWorkbook workbook, MappingFontType key) {
		Map<MappingFontType, XSSFFont> workbookFontMap = fontsMap.get(workbook);
		if (workbookFontMap == null) {
			workbookFontMap = new HashMap<>();
			fontsMap.put(workbook, workbookFontMap);
		}

		XSSFFont font = workbookFontMap.get(key);
		if (font == null) {
			font = createFont(workbook, key);

			workbookFontMap.put(key, font);
			fontsMap.put(workbook, workbookFontMap);
		}
		return font;
	}

	/**
	 * Creates the font.
	 *
	 * @param workbook the workbook
	 * @param key      the key
	 * @return the XSSF font
	 */
	private static XSSFFont createFont(XSSFWorkbook workbook, MappingFontType key) {
		XSSFFont font = workbook.createFont();

		if (key == MappingFontType.HEADER) {
			font.setFontName("Courier New");
			font.setBold(true);
			font.setUnderline(Font.U_SINGLE);
			font.setColor(HSSFColorPredefined.DARK_RED.getIndex());
		}

		if (key == MappingFontType.BODY) {
			font.setFontName("Courier New");
			font.setColor(HSSFColorPredefined.BLUE.getIndex());
		}

		return font;
	}

	/**
	 * Gets the cell style.
	 *
	 * @param workbook the workbook
	 * @param key      the key
	 * @return the cell style
	 */
	public static XSSFCellStyle getCellStyle(XSSFWorkbook workbook, MappingCellStyleType key) {
		Map<MappingCellStyleType, XSSFCellStyle> workbookCellTypesMap = cellTypesMap.get(workbook);
		if (workbookCellTypesMap == null) {
			workbookCellTypesMap = new HashMap<>();
			cellTypesMap.put(workbook, workbookCellTypesMap);
		}

		XSSFCellStyle cellStyle = workbookCellTypesMap.get(key);
		if (cellStyle == null) {
			cellStyle = createCellStyle(workbook, key);

			workbookCellTypesMap.put(key, cellStyle);
			cellTypesMap.put(workbook, workbookCellTypesMap);
		}
		return cellStyle;
	}

	/**
	 * Creates the cell style.
	 *
	 * @param workbook the workbook
	 * @param key      the key
	 * @return the XSSF cell style
	 */
	private static XSSFCellStyle createCellStyle(XSSFWorkbook workbook, MappingCellStyleType key) {
		XSSFCellStyle cellStyle = workbook.createCellStyle();

		if (key == MappingCellStyleType.HEADER) {
			cellStyle.setFont(MappingUtils.getFont(workbook, MappingFontType.HEADER));
			cellStyle.setAlignment(HorizontalAlignment.CENTER);
			cellStyle.setBorderBottom(BorderStyle.THIN);
			cellStyle.setBorderTop(BorderStyle.THIN);
			cellStyle.setBorderLeft(BorderStyle.THIN);
			cellStyle.setBorderRight(BorderStyle.THIN);
			cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			cellStyle.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
		}

		if (key == MappingCellStyleType.BODY) {
			cellStyle.setFont(MappingUtils.getFont(workbook, MappingFontType.BODY));
			cellStyle.setAlignment(HorizontalAlignment.LEFT);
			cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
			cellStyle.setBorderBottom(BorderStyle.THIN);
			cellStyle.setBorderTop(BorderStyle.THIN);
			cellStyle.setBorderLeft(BorderStyle.THIN);
			cellStyle.setBorderRight(BorderStyle.THIN);
			cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			cellStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
		}

		if (key == MappingCellStyleType.DATE) {
			XSSFCreationHelper creationHelper = workbook.getCreationHelper();
			cellStyle.setDataFormat(creationHelper.createDataFormat().getFormat("yyyy-MM-dd HH:mm:ss"));
		}

		XSSFCellStyle cloneWTF = workbook.createCellStyle();
		cloneWTF.cloneStyleFrom(cellStyle);

		return cloneWTF;
	}

	/**
	 * Gets the value from cell.
	 *
	 * @param cell         the cell
	 * @param mergedRegion the merged region
	 * @return the value from cell
	 */
	public static Object getValueFromCell(Cell cell, MergedRegion mergedRegion) {

		// If the cell is in a merged region, parse it
		if (mergedRegion != null) {
			return mergedRegion.getValue();
		}

		if (cell == null) {
			return null;
		}

		// If not get the cell value
		if (cell.getCellType() == CellType.STRING) {
			return cell.getStringCellValue();
		}
		if (cell.getCellType() == CellType.NUMERIC) {
			return cell.getNumericCellValue();
		}
		if (cell.getCellType() == CellType.BOOLEAN) {
			return cell.getBooleanCellValue();
		}
		if (cell.getCellType() == CellType.BLANK) {
			Logger.getLogger(MappingUtils.class.getName()).log(Level.FINE,
					"Cell @ row=" + cell.getRowIndex() + " - column=" + cell.getColumnIndex() + " is empty");
			return cell.getBooleanCellValue();
		}
		Logger.getLogger(MappingUtils.class.getName()).log(Level.FINEST,
				"Unable to get an usefull value for cell @ row=" + cell.getRowIndex() + " - column="
						+ cell.getColumnIndex());

		return null;
	}

	/**
	 * Sets the value from cell.
	 *
	 * @param <T>    the generic type
	 * @param cell   the cell
	 * @param object the object
	 * @param setter the setter
	 * @throws IllegalAccessException    the illegal access exception
	 * @throws InvocationTargetException the invocation target exception
	 */
	public static <T extends Object> void setValueFromCell(Cell cell, T object, Method setter)
			throws IllegalAccessException, InvocationTargetException {

		Parameter parameter = setter.getParameters()[0];
		Class<?> parameterType = parameter.getType();

		// Case : setter parameter is a String
		if (parameterType.getName().equals(String.class.getName())) {
			if (cell.getCellType() == CellType.STRING) {
				setter.invoke(object, cell.getStringCellValue());
				return;
			}
			if (cell.getCellType() == CellType.NUMERIC) {
				setter.invoke(object, Double.toString(cell.getNumericCellValue()));
				return;
			}
			if (cell.getCellType() == CellType.BOOLEAN) {
				setter.invoke(object, Boolean.toString(cell.getBooleanCellValue()));
				return;
			}
			Logger.getLogger(MappingUtils.class.getName()).log(Level.SEVERE,
					"Parsing String from " + parameterType.getName() + " failed");
		}

		// Case : setter parameter is a Integer
		if (parameterType.getName().equals(Integer.class.getName()) || parameterType.getName().equals("int")) {
			if (cell.getCellType() == CellType.STRING) {
				setter.invoke(object, Integer.parseInt(cell.getStringCellValue()));
				return;
			}
			if (cell.getCellType() == CellType.NUMERIC) {
				setter.invoke(object, Integer.valueOf(Double.valueOf(cell.getNumericCellValue()).intValue()));
				return;
			}
			if (cell.getCellType() == CellType.BOOLEAN) {
				setter.invoke(object, cell.getBooleanCellValue() ? 1 : 0);
				return;
			}
			Logger.getLogger(MappingUtils.class.getName()).log(Level.SEVERE,
					"Parsing Integer from " + parameterType.getName() + " failed");
		}

		// Case : setter parameter is a Double
		if (parameterType.getName().equals(Double.class.getName()) || parameterType.getName().equals("double")) {
			if (cell.getCellType() == CellType.STRING) {
				setter.invoke(object, Double.parseDouble(cell.getStringCellValue()));
				return;
			}
			if (cell.getCellType() == CellType.NUMERIC) {
				setter.invoke(object, Double.valueOf(cell.getNumericCellValue()).intValue());
				return;
			}
			if (cell.getCellType() == CellType.BOOLEAN) {
				setter.invoke(object, cell.getBooleanCellValue() ? 1 : 0);
				return;
			}
			Logger.getLogger(MappingUtils.class.getName()).log(Level.SEVERE,
					"Parsing Double from " + parameterType.getName() + " failed");
		}

		// Case : setter parameter is a Float
		if (parameterType.getName().equals(Float.class.getName()) || parameterType.getName().equals("float")) {
			if (cell.getCellType() == CellType.STRING) {
				setter.invoke(object, Float.parseFloat(cell.getStringCellValue()));
				return;
			}
			if (cell.getCellType() == CellType.NUMERIC) {
				setter.invoke(object, Float.valueOf(Double.valueOf(cell.getNumericCellValue()).intValue()));
				return;
			}
			if (cell.getCellType() == CellType.BOOLEAN) {
				setter.invoke(object, cell.getBooleanCellValue() ? 1 : 0);
				return;
			}
			Logger.getLogger(MappingUtils.class.getName()).log(Level.SEVERE,
					"Parsing Float from " + parameterType.getName() + " failed");
		}

		// Case : setter parameter is a Long
		if (parameterType.getName().equals(Long.class.getName()) || parameterType.getName().equals("long")) {
			if (cell.getCellType() == CellType.STRING) {
				setter.invoke(object, Long.parseLong(cell.getStringCellValue()));
				return;
			}
			if (cell.getCellType() == CellType.NUMERIC) {
				setter.invoke(object, Long.valueOf(Double.valueOf(cell.getNumericCellValue()).intValue()));
				return;
			}
			if (cell.getCellType() == CellType.BOOLEAN) {
				setter.invoke(object, cell.getBooleanCellValue() ? 1 : 0);
				return;
			}
			Logger.getLogger(MappingUtils.class.getName()).log(Level.SEVERE,
					"Parsing Long from " + parameterType.getName() + " failed");
		}

		// Case : setter parameter is a Boolean
		if (parameterType.getName().equals(Boolean.class.getName()) || parameterType.getName().equals("boolean")) {
			if (cell.getCellType() == CellType.STRING) {
				setter.invoke(object, Boolean.parseBoolean(cell.getStringCellValue()));
				return;
			}
			if (cell.getCellType() == CellType.NUMERIC) {
				setter.invoke(object, Double.valueOf(cell.getNumericCellValue()) == 1 ? Boolean.TRUE : Boolean.FALSE);
				return;
			}
			if (cell.getCellType() == CellType.BOOLEAN) {
				setter.invoke(object, Boolean.valueOf(cell.getBooleanCellValue()));
				return;
			}
			Logger.getLogger(MappingUtils.class.getName()).log(Level.SEVERE,
					"Parsing Boolean from " + parameterType.getName() + " failed");
		}

		// Case : setter parameter is a Date
		if (parameterType.getName().equals(Date.class.getName())) {
			if (cell.getCellType() == CellType.STRING) {
				setter.invoke(object, DateFormatManager.instance().parse(cell.getStringCellValue()));
				return;
			}
			if (cell.getCellType() == CellType.NUMERIC) {
				setter.invoke(object, cell.getDateCellValue());
				return;
			}

			Logger.getLogger(MappingUtils.class.getName()).log(Level.SEVERE,
					"Parsing Date from " + parameterType.getName() + " failed");
		}

		Logger.getLogger(MappingUtils.class.getName()).log(Level.SEVERE,
				"No case found for " + parameterType.getCanonicalName());
	}

	/**
	 * Sets the cell value.
	 *
	 * @param workbook    the workbook
	 * @param cell        the cell
	 * @param valueObject the value object
	 */
	public static void setCellValue(XSSFWorkbook workbook, XSSFCell cell, Object valueObject) {
		if (valueObject instanceof Boolean)
			cell.setCellValue((Boolean) valueObject);
		else if (valueObject instanceof Double)
			cell.setCellValue((double) valueObject);
		else if (valueObject instanceof Float)
			cell.setCellValue((float) valueObject);
		else if (valueObject instanceof Date) {
			cell.setCellValue((Date) valueObject);
			cell.setCellStyle(MappingUtils.getCellStyle(workbook, MappingCellStyleType.DATE));
		} else if (Collection.class.isAssignableFrom(valueObject.getClass())) {
			cell.setCellValue(ObjectUtils.toString(((Collection<?>) valueObject), ", "));
		} else {
			cell.setCellValue(valueObject.toString());
		}
	}

	/**
	 * Gets the cell range address.
	 *
	 * @param mergedRegions the merged regions
	 * @param cell          the cell
	 * @return the cell range address
	 */
	public static CellRangeAddress getCellRangeAddress(List<CellRangeAddress> mergedRegions, Cell cell) {
		for (CellRangeAddress cellRangeAddress : mergedRegions) {
			if (cellRangeAddress.isInRange(cell)) {
				return cellRangeAddress;
			}
		}
		return null;
	}

	/**
	 * Checks if is cell merged.
	 *
	 * @param mergedRegions the merged regions
	 * @param cell          the cell
	 * @return true, if is cell merged
	 */
	public static boolean isCellMerged(List<CellRangeAddress> mergedRegions, Cell cell) {
		return getCellRangeAddress(mergedRegions, cell) != null;
	}

	/**
	 * Gets the merged regions.
	 *
	 * @param sheet the sheet
	 * @return the merged regions
	 */
	public static Collection<MergedRegion> getMergedRegions(XSSFSheet sheet) {
		Map<CellRangeAddress, MergedRegion> mergedRegions = new HashMap<>();

		List<CellRangeAddress> cellRangeAddresses = sheet.getMergedRegions();

		Iterator<Row> rowIterator = sheet.iterator();
		while (rowIterator.hasNext()) {

			Row row = rowIterator.next();

			if (row != null) {

				// For each row, iterate through all the columns
				Iterator<Cell> cellIterator = row.cellIterator();

				while (cellIterator.hasNext()) {

					Cell cell = cellIterator.next();

					if (cell != null) {

						CellRangeAddress cellRangeAddress = getCellRangeAddress(cellRangeAddresses, cell);
						if (cellRangeAddress != null) {

							MergedRegion mergedRegion = mergedRegions.get(cellRangeAddress);
							if (mergedRegion == null) {
								mergedRegion = new MergedRegion(cellRangeAddress);
								mergedRegions.put(cellRangeAddress, mergedRegion);
							}

							mergedRegion.getCells().add(cell);

							Object valueFromCell = MappingUtils.getValueFromCell(cell, null);
							if (mergedRegion.getValue() == null && valueFromCell != null) {
								mergedRegion.setValue(valueFromCell);
							}
						}
					}
				}
			}
		}

		return mergedRegions.values();
	}

	/**
	 * Gets the merged region for cell.
	 *
	 * @param mergedRegions the merged regions
	 * @param cell          the cell
	 * @return the merged region for cell
	 */
	public static MergedRegion getMergedRegionForCell(Collection<MergedRegion> mergedRegions, Cell cell) {
		for (MergedRegion mergedRegion : mergedRegions) {
			if (mergedRegion.getCellRangeAddress().isInRange(cell)) {
				return mergedRegion;
			}
		}
		return null;
	}
}
