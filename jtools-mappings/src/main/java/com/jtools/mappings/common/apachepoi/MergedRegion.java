package com.jtools.mappings.common.apachepoi;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.util.CellRangeAddress;

public class MergedRegion {

	private final CellRangeAddress cellRangeAddress;
	private final List<Cell> cells;
	private Object value = null;

	private boolean alreadyProcessedFlag;

	public MergedRegion(CellRangeAddress cellRangeAddress) {
		this.cellRangeAddress = cellRangeAddress;
		this.cells = new ArrayList<>();
	}

	public CellRangeAddress getCellRangeAddress() {
		return cellRangeAddress;
	}

	public List<Cell> getCells() {
		return cells;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public boolean isAlreadyProcessedFlag() {
		return alreadyProcessedFlag;
	}

	public void setAlreadyProcessedFlag(boolean alreadyProcessedFlag) {
		this.alreadyProcessedFlag = alreadyProcessedFlag;
	}
}