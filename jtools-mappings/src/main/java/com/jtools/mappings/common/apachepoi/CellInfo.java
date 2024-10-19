package com.jtools.mappings.common.apachepoi;

import org.apache.poi.ss.usermodel.Cell;

import com.jtools.mappings.common.MappingUtils;

/**
 * 
 * @author j4ckk0
 *
 */
public class CellInfo {

	final Cell cell;
	final String fieldPath;
	private final Object cellValue;
	private final MergedRegion mergedRegion;

	public CellInfo(Cell cell, String fieldPath, MergedRegion mergedRegion) {
		this.cell = cell;
		this.fieldPath = fieldPath;
		this.mergedRegion = mergedRegion;
		this.cellValue = MappingUtils.getValueFromCell(cell, mergedRegion);
	}

	public Cell getCell() {
		return cell;
	}

	public String getFieldPath() {
		return fieldPath;
	}

	public Object getCellValue() {
		return cellValue;
	}

	public MergedRegion getMergedRegion() {
		return mergedRegion;
	}
}