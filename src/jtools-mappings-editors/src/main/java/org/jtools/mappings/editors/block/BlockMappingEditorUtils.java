package org.jtools.mappings.editors.block;

/*-
 * #%L
 * Java Tools - Mappings Editors
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

import org.jtools.mappings.block.BlockMapping;
import org.jtools.mappings.block.BlockMappingRow;
// TODO: Auto-generated Javadoc

/**
 * The Class BlockMappingEditorUtils.
 */
public class BlockMappingEditorUtils {
		
		/**
		 * Gets the block mapping editor rows.
		 *
		 * @param blockMapping the block mapping
		 * @return the block mapping editor rows
		 */
		public static List<BlockMappingEditorRow> getBlockMappingEditorRows(BlockMapping<?> blockMapping) {
		List<BlockMappingEditorRow> rows = new ArrayList<>();
		for(BlockMappingRow row : blockMapping.getRows()) {
			rows.add(new BlockMappingEditorRow(row));
		}
		return rows;
	}
}
