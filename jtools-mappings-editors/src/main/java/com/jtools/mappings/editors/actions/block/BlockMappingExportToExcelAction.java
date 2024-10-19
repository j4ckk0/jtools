/**
 * 
 */
package com.jtools.mappings.editors.actions.block;

import javax.swing.Icon;

import com.jtools.mappings.block.exporters.BlockMappingExcelExporter;

/**
 * @author j4ckk0
 *
 */
public class BlockMappingExportToExcelAction extends ABlockMappingExportToAction {

	private static final long serialVersionUID = -5666508052700983450L;

	public BlockMappingExportToExcelAction(String name, Icon icon) {
		super(name, icon, BlockMappingExcelExporter.getInstance());
	}

	public BlockMappingExportToExcelAction(String name) {
		super(name, BlockMappingExcelExporter.getInstance());
	}
	
	public BlockMappingExportToExcelAction(String name, Icon icon, String mappingsFilepath) {
		super(name, icon, BlockMappingExcelExporter.getInstance(), mappingsFilepath);
	}

	public BlockMappingExportToExcelAction(String name, String mappingsFilepath) {
		super(name, BlockMappingExcelExporter.getInstance(), mappingsFilepath);
	}

}
