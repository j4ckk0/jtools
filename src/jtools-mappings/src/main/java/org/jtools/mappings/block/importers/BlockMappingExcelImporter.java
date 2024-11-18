/**
 * 
 */
package org.jtools.mappings.block.importers;

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
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jtools.mappings.block.BlockMapping;
import org.jtools.mappings.block.BlockMappingRow;
import org.jtools.mappings.common.MappingUtils;
import org.jtools.mappings.common.apachepoi.CellInfo;
import org.jtools.mappings.common.apachepoi.MergedRegion;
import org.jtools.mappings.common.importers.ExcelImportConfigPanel;
import org.jtools.utils.objects.ObjectUtils;

/**
 * @author j4ckk0
 *
 */
public class BlockMappingExcelImporter implements IBlockMappingImporter {

	private static BlockMappingExcelImporter instance;

	private static final String OBJECT_SEPARATOR = ">";
	private static final String FIELD_SEPARATOR = "#";

	private final FlushInstructionComparator flushInstructionComparator = new FlushInstructionComparator();

	public static BlockMappingExcelImporter instance() {
		if (instance == null) {
			instance = new BlockMappingExcelImporter();
		}
		return instance;
	}

	public <T extends Object> List<T> importData(Class<T> importedObjectClass, BlockMapping<?> mapping)
			throws IOException {

		ExcelImportConfigPanel excelImporterDefinitionPanel = new ExcelImportConfigPanel(importedObjectClass);

		int result = JOptionPane.showConfirmDialog(null, excelImporterDefinitionPanel, "Excel importer",
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

		if (result == JOptionPane.OK_OPTION) {
			return doImport(importedObjectClass, mapping, excelImporterDefinitionPanel.getSelectedFile(),
					excelImporterDefinitionPanel.getFirstDataRow());
		}

		return null;
	}

	public <T extends Object> List<T> doImport(Class<T> importedObjectClass, BlockMapping<?> blockMapping, File file,
			int firstDataRowIndex) {
		List<T> importedObjects = new ArrayList<>();

		try {
			// Create Workbook instance holding reference to .xlsx file
			XSSFWorkbook workbook = new XSSFWorkbook(file);

			// Get first/desired sheet from the workbook
			XSSFSheet sheet = workbook.getSheetAt(0);

			// Collect info from the sheet
			Collection<MergedRegion> mergedRegions = MappingUtils.getMergedRegions(sheet);
			List<FlushInstruction> referenceFlushInstructions = getReferenceFlushInstructions(sheet, mergedRegions,
					blockMapping, firstDataRowIndex);

			// Init some variables for the algo
			Map<String, Object> objectsBuffer = new HashMap<>();

			// Iterate through each rows one by one
			int rowIndex = -1;
			Iterator<Row> rowIterator = sheet.iterator();
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();

				rowIndex++;
				if (rowIndex < firstDataRowIndex) {
					continue;
				}

				List<CellInfo> cellsInfoList = getCellsInfo(row, mergedRegions, true, blockMapping);

				List<FlushInstruction> flushInstructions = pickFlushInstructions(cellsInfoList,
						referenceFlushInstructions);

				flushObjectsBuffer(flushInstructions, objectsBuffer, importedObjects, importedObjectClass);

				buildObjectsFromCells(cellsInfoList, objectsBuffer);
			}

			flushObjectsBuffer(referenceFlushInstructions, objectsBuffer, importedObjects, importedObjectClass);

			workbook.close();

		} catch (Exception e) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage());
			Logger.getLogger(getClass().getName()).log(Level.FINE, e.getMessage(), e);
		}

		return importedObjects;
	}

	private List<FlushInstruction> getReferenceFlushInstructions(XSSFSheet sheet,
			Collection<MergedRegion> mergedRegions, BlockMapping<?> blockMapping, int firstDataRowIndex) {
		// Iterate through each rows one by one
		int rowIndex = -1;
		Iterator<Row> rowIterator = sheet.iterator();

		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();

			rowIndex++;
			if (rowIndex < firstDataRowIndex) {
				continue;
			}

			List<CellInfo> cellsInfoList = getCellsInfo(row, mergedRegions, false, blockMapping);

			return getFlushInstructions(cellsInfoList, false);
		}

		return null;
	}

	private List<CellInfo> getCellsInfo(Row row, Collection<MergedRegion> mergedRegions, boolean markMergedRegions,
			BlockMapping<?> blockMapping) {

		List<CellInfo> cellsInfoList = new ArrayList<>();

		Iterator<Cell> cellIterator = row.cellIterator();

		// make possible to define a range of columns
		while (cellIterator.hasNext()) {
			Cell cell = cellIterator.next();

			if (cell == null) {
				Logger.getLogger(getClass().getName()).log(Level.FINE, "Null cell ignored : row = " + row.getRowNum());
				continue;
			}

			MergedRegion mergedRegion = MappingUtils.getMergedRegionForCell(mergedRegions, cell);

			// Empty cells are ignored unless they are merge
			if ((cell.getCellType() == CellType.BLANK) && (mergedRegion == null)) {
				Logger.getLogger(getClass().getName()).log(Level.FINE,
						"Empty cell ignored : row = " + row.getRowNum() + " - col = " + cell.getColumnIndex());
				continue;
			}

			// Deal with merged regions
			if (mergedRegion != null) {
				// Cells in merge regions which have already been processed are ignored
				if (mergedRegion.isAlreadyProcessedFlag()) {
					Logger.getLogger(getClass().getName()).log(Level.FINE, "Cell in merged region ignored : row = "
							+ row.getRowNum() + " - col = " + cell.getColumnIndex());
					continue;
				}

				// We are going to process the merge region, so flag it if required.
				// For example, when getting the cells info to get the "reference" flush
				// instructions, we don't want to mark the regions
				else if (markMergedRegions) {
					mergedRegion.setAlreadyProcessedFlag(true);
				}
			}

			// None empty cell. Get the column letter to get the corresponding mapping row
			String inputColumn = MappingUtils.possibleColumns.get(cell.getColumnIndex());

			// Build the "field path" for the cell
			BlockMapping<?> testedBlockMapping = blockMapping;
			String fieldPath = "";
			while (testedBlockMapping != null) {

				fieldPath += OBJECT_SEPARATOR + testedBlockMapping.getObjectClass().getCanonicalName();

				// Get the mapping row corresponding to the cell, to know what it is
				BlockMappingRow mappingRow = getMappingRow(testedBlockMapping, inputColumn);
				if (mappingRow == null) {
					Logger.getLogger(getClass().getName()).log(Level.WARNING,
							"No mapping found for column " + inputColumn);
					continue;
				}

				Field objectField = mappingRow.getObjectField();
				if (objectField == null) {
					Logger.getLogger(getClass().getName()).log(Level.WARNING, "Illegal mapping row : no field defined");
					continue;
				}

				// Update the fieldPath
				fieldPath += FIELD_SEPARATOR + objectField.getName();

				// Continue ... or not
				testedBlockMapping = mappingRow.getSubBlockMapping();
			}

			CellInfo cellInfo = new CellInfo(cell, fieldPath.substring(1), mergedRegion);
			cellsInfoList.add(cellInfo);

		}

		return cellsInfoList;
	}

	private List<FlushInstruction> pickFlushInstructions(List<CellInfo> cellsInfoList,
			List<FlushInstruction> referenceFlushInstructions) {
		List<FlushInstruction> flushInstructions = new ArrayList<>();

		// Process the cells info
		for (CellInfo cellInfo : cellsInfoList) {

			String[] objectsPathArray = cellInfo.getFieldPath().split(OBJECT_SEPARATOR);

			String cellObjectPath = objectsPathArray[objectsPathArray.length - 1];
			String cellObjectClassName = cellObjectPath.split(FIELD_SEPARATOR)[0];

			// Get the flush instruction for the objet itself
			FlushInstruction flushInstructionFor = getFlushInstructionFor(referenceFlushInstructions,
					cellObjectClassName);
			
			if (flushInstructionFor == null) {
				Logger.getLogger(getClass().getName()).log(Level.WARNING,
						"Unable to find a flush entry for " + cellObjectClassName);
				return Collections.emptyList();
			}
			
			// Add the flush instruction fo the object
			if (!flushInstructions.contains(flushInstructionFor)) {
				flushInstructions.add(flushInstructionFor);
			}

			// Get all the flush instructions with a complexity superior
			for (FlushInstruction flushInstruction : referenceFlushInstructions) {
				if (flushInstruction.complexity > flushInstructionFor.complexity) {
					if (!flushInstructions.contains(flushInstruction)) {
						flushInstructions.add(flushInstruction);
					}
				}
			}
		}

		Collections.sort(flushInstructions, flushInstructionComparator);

		return flushInstructions;
	}

	private <T extends Object> List<FlushInstruction> getFlushInstructions(List<CellInfo> cellsInfoList,
			boolean processFullPath) {

		List<FlushInstruction> flushInstructions = new ArrayList<>();

		// Process the cells info
		for (CellInfo cellInfo : cellsInfoList) {

			String[] objectsPathArray = cellInfo.getFieldPath().split(OBJECT_SEPARATOR);

			String cellObjectPath = objectsPathArray[objectsPathArray.length - 1];
			String cellObjectClassName = cellObjectPath.split(FIELD_SEPARATOR)[0];

			// The case of the flush instructions for "imported objects" (flushPath is null)
			if (objectsPathArray.length == 1) {
				FlushInstruction flushInstruction = new FlushInstruction(objectsPathArray.length, cellObjectClassName,
						null);
				if (!flushInstructions.contains(flushInstruction)) {
					flushInstructions.add(flushInstruction);
				}
			}

			// The case of all the others objects (flushPath is built)
			else if (objectsPathArray.length >= 2) {

				String objectToFlushClassName = cellObjectClassName;

				// We can choose not to parse the full path. For "row to row" flushing, we just
				// need to flush 1 depth level
				int stopIndex = processFullPath ? 0 : objectsPathArray.length - 2;

				for (int i = objectsPathArray.length - 2; i >= stopIndex; i--) {
					String flushPath = objectsPathArray[i];

					FlushInstruction flushInstruction = new FlushInstruction(objectsPathArray.length,
							objectToFlushClassName, flushPath);

					if (!flushInstructions.contains(flushInstruction)) {
						flushInstructions.add(flushInstruction);
					}

					objectToFlushClassName = flushPath.split(FIELD_SEPARATOR)[0];
				}
			}
		}

		Collections.sort(flushInstructions, flushInstructionComparator);

		return flushInstructions;

	}

	private <T extends Object> void buildObjectsFromCells(List<CellInfo> cellsInfoList,
			Map<String, Object> objectsBuffer) {

		// Process the cells info
		for (CellInfo cellInfo : cellsInfoList) {

			String[] objectsPathArray = cellInfo.getFieldPath().split(OBJECT_SEPARATOR);

			String cellObjectPath = objectsPathArray[objectsPathArray.length - 1];

			setValueFromCell(cellObjectPath, cellInfo.getCell(), objectsBuffer);
		}

	}

	@SuppressWarnings("unchecked")
	private <T extends Object> void flushObjectsBuffer(List<FlushInstruction> flushInstructions,
			Map<String, Object> objectsBuffer, List<T> importedObjects, Class<T> importedObjectClass) {
		// Flush
		for (FlushInstruction flushInstruction : flushInstructions) {
			String flushPath = flushInstruction.flushPath;
			String objectToFlushClassName = flushInstruction.objectToFlushClassName;

			Object objectToFlush = objectsBuffer.get(flushInstruction.objectToFlushClassName);

			if (objectToFlush != null) {
				// Flush buffered objects into them selves
				if (flushPath != null) {
					flushObject(flushPath, objectToFlush, objectsBuffer);

					objectsBuffer.remove(flushInstruction.objectToFlushClassName);
				}

				// Normally : flush the final objects into the final list of objects
				else if (objectToFlushClassName.equals(importedObjectClass.getCanonicalName())
						&& objectToFlush.getClass().equals(importedObjectClass)) {
					importedObjects.add((T) objectToFlush);

					objectsBuffer.remove(flushInstruction.objectToFlushClassName);
				}

				else {
					Logger.getLogger(getClass().getName()).log(Level.WARNING,
							"Unable to flush entry " + flushInstruction.objectToFlushClassName);
				}
			}
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Object flushObject(String flushPath, Object objectToFlush, Map<String, Object> objectsBuffer) {
		String[] fieldInfo = flushPath.split(FIELD_SEPARATOR);

		String objectClassName = fieldInfo[0];

		// Get the object owning the field
		Object object = objectsBuffer.get(objectClassName);
		if (object == null) {
			object = ObjectUtils.instanciateObject(objectClassName);
			objectsBuffer.put(objectClassName, object);
		}

		// Get the field
		String fieldName = fieldInfo[1];
		Class<?> objectClass = ObjectUtils.getObjectClass(objectClassName);
		Field field = ObjectUtils.getField(objectClassName, fieldName);

		// Get the setter for the field
		Method setter = ObjectUtils.findSetter(objectClass, field);
		if (setter == null) {
			Logger.getLogger(getClass().getName()).log(Level.FINE, "setter not found for field " + fieldName);
			return object;
		}

		if (setter.getParameterCount() != 1) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, "setter " + setter.getName() + " has "
					+ setter.getParameterCount() + " parameters. Unsupported usecase");
			return object;
		}

		Parameter parameter = setter.getParameters()[0];
		Class<?> parameterType = parameter.getType();

		try {
			if (List.class.isAssignableFrom(parameterType)) {
				List list = getList(objectClass, object, field);
				if (list == null) {
					list = createList(object, setter);
				}

				list.add(objectToFlush);

			} else {
				setter.invoke(object, objectToFlush);
			}
		} catch (IllegalAccessException | InvocationTargetException e) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE,
					"An error occurred while flushing : " + e.getMessage(), e);
		}

		return object;
	}

	private List<?> createList(Object object, Method setter) {
		try {
			List<?> list = new ArrayList<>();

			setter.invoke(object, list);

			return list;
		} catch (IllegalAccessException | InvocationTargetException e) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE,
					"An error occurred while getting the list : " + e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}

	private List<?> getList(Class<?> objectClass, Object object, Field field) {
		try {
			Method getter = ObjectUtils.findGetter(objectClass, field);
			if (getter == null) {
				Logger.getLogger(getClass().getName()).log(Level.FINE, "getter not found for field " + field.getName());
				return null;
			}

			return (List<?>) getter.invoke(object);
		} catch (IllegalAccessException | InvocationTargetException | IllegalArgumentException | ClassCastException e) {
			Logger.getLogger(getClass().getName()).log(Level.FINEST,
					"An error occurred while getting the list : " + e.getMessage(), e);
		}

		return null;
	}

	protected FlushInstruction getFlushInstructionFor(List<FlushInstruction> flushInstructions,
			String objectToFlushClassName) {
		for (FlushInstruction flushInstruction : flushInstructions) {
			if (flushInstruction.objectToFlushClassName.equals(objectToFlushClassName)) {
				return flushInstruction;
			}
		}
		return null;
	}

	private Object setValueFromCell(String objectPath, Cell cell, Map<String, Object> objectsBuffer) {
		String[] fieldInfo = objectPath.split(FIELD_SEPARATOR);

		String objectClassName = fieldInfo[0];

		// Get the object owning the field
		Object object = objectsBuffer.get(objectClassName);
		if (object == null) {
			object = ObjectUtils.instanciateObject(objectClassName);
			objectsBuffer.put(objectClassName, object);
		}

		// Get the field
		String fieldName = fieldInfo[1];
		Class<?> objectClass = ObjectUtils.getObjectClass(objectClassName);
		Field field = ObjectUtils.getField(objectClassName, fieldName);

		// Get the setter for the field
		Method setter = ObjectUtils.findSetter(objectClass, field);
		if (setter == null) {
			Logger.getLogger(getClass().getName()).log(Level.FINE, "setter not found for field " + fieldName);
			return object;
		}

		if (setter.getParameterCount() != 1) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, "setter " + setter.getName() + " has "
					+ setter.getParameterCount() + " parameters. Unsupported usecase");
			return object;
		}

		// Set the value
		try {
			MappingUtils.setValueFromCell(cell, object, setter);
		} catch (IllegalAccessException | InvocationTargetException e) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE,
					"An error occued while setting the value from cell: " + e.getMessage(), e);
		}

		return object;
	}

	private BlockMappingRow getMappingRow(BlockMapping<?> blockMapping, String inputColumn) {
		for (BlockMappingRow mapping : blockMapping.getRows()) {
			List<String> columnsRange = MappingUtils.getColumnsRangeAsList(mapping.getFromColumn(),
					mapping.getToColumn());
			if (columnsRange.contains(inputColumn)) {
				return mapping;
			}
		}
		return null;
	}

	/**
	 * 
	 * @author j4ckk0
	 *
	 */
	private class FlushInstruction {

		private final int complexity;
		private final String objectToFlushClassName;
		private final String flushPath;

		public FlushInstruction(int complexity, String objectToFlushClassName, String flushPath) {
			this.complexity = complexity;
			this.objectToFlushClassName = objectToFlushClassName;
			this.flushPath = flushPath;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj instanceof FlushInstruction) {
				return ((FlushInstruction) obj).objectToFlushClassName.equals(objectToFlushClassName);
			}
			return false;
		}
		
		@Override
		public int hashCode() {
			return super.hashCode();
		}

	}

	private class FlushInstructionComparator implements Comparator<FlushInstruction> {

		@Override
		public int compare(FlushInstruction o1, FlushInstruction o2) {
			return o2.complexity - o1.complexity;
		}

	}

}
