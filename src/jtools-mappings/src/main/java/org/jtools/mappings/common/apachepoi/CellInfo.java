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

import org.apache.poi.ss.usermodel.Cell;
import org.jtools.mappings.common.MappingUtils;
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
