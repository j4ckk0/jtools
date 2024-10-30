/**
 * 
 */
package com.jtools.mappings.editors.block;

import java.util.ArrayList;
import java.util.List;

import com.jtools.mappings.block.BlockMapping;
import com.jtools.mappings.block.BlockMappingRow;

/**
 * @author j4ckk0
 *
 */
public class BlockMappingEditorUtils {
	
	/**
	 * 
	 * @param blockMapping
	 * @return
	 */
	public static List<BlockMappingEditorRow> getBlockMappingEditorRows(BlockMapping<?> blockMapping) {
		List<BlockMappingEditorRow> rows = new ArrayList<>();
		for(BlockMappingRow row : blockMapping.getRows()) {
			rows.add(new BlockMappingEditorRow(row));
		}
		return rows;
	}
}
