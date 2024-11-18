package org.jtools.mappings.common.apachepoi;

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
