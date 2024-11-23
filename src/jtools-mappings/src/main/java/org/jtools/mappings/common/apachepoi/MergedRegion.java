package org.jtools.mappings.common.apachepoi;

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

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.util.CellRangeAddress;

// TODO: Auto-generated Javadoc
/**
 * The Class MergedRegion.
 */
public class MergedRegion {

	/** The cell range address. */
	private final CellRangeAddress cellRangeAddress;
	
	/** The cells. */
	private final List<Cell> cells;
	
	/** The value. */
	private Object value = null;

	/** The already processed flag. */
	private boolean alreadyProcessedFlag;

	/**
	 * Instantiates a new merged region.
	 *
	 * @param cellRangeAddress the cell range address
	 */
	public MergedRegion(CellRangeAddress cellRangeAddress) {
		this.cellRangeAddress = cellRangeAddress;
		this.cells = new ArrayList<>();
	}

	/**
	 * Gets the cell range address.
	 *
	 * @return the cell range address
	 */
	public CellRangeAddress getCellRangeAddress() {
		return cellRangeAddress;
	}

	/**
	 * Gets the cells.
	 *
	 * @return the cells
	 */
	public List<Cell> getCells() {
		return cells;
	}

	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * Sets the value.
	 *
	 * @param value the new value
	 */
	public void setValue(Object value) {
		this.value = value;
	}

	/**
	 * Checks if is already processed flag.
	 *
	 * @return true, if is already processed flag
	 */
	public boolean isAlreadyProcessedFlag() {
		return alreadyProcessedFlag;
	}

	/**
	 * Sets the already processed flag.
	 *
	 * @param alreadyProcessedFlag the new already processed flag
	 */
	public void setAlreadyProcessedFlag(boolean alreadyProcessedFlag) {
		this.alreadyProcessedFlag = alreadyProcessedFlag;
	}
}
