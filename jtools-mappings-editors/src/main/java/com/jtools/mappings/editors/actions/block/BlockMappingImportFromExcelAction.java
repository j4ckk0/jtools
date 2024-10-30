/**
 * 
 */
package com.jtools.mappings.editors.actions.block;

import javax.swing.Icon;

import com.jtools.mappings.block.importers.BlockMappingExcelImporter;

/**
 * @author j4ckk0
 *
 */
public class BlockMappingImportFromExcelAction extends ABlockMappingImportFromAction {

	private static final long serialVersionUID = 5366162869248310896L;

	public BlockMappingImportFromExcelAction(String name, Icon icon) {
		super(name, icon, BlockMappingExcelImporter.instance());
	}

	public BlockMappingImportFromExcelAction(String name) {
		super(name, BlockMappingExcelImporter.instance());
	}

}
