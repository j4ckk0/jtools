/**
 * 
 */
package com.jtools.mappings.editors.actions.simple;

import javax.swing.Icon;

import com.jtools.mappings.simple.exporters.SimpleMappingExcelExporter;

/**
 * @author j4ckk0
 *
 */
public class SimpleMappingExportToExcelAction extends ASimpleMappingExportToAction {

	private static final long serialVersionUID = -5666508052700983450L;

	public SimpleMappingExportToExcelAction(String name, Icon icon) {
		super(name, icon, SimpleMappingExcelExporter.getInstance());
	}

	public SimpleMappingExportToExcelAction(String name) {
		super(name, SimpleMappingExcelExporter.getInstance());
	}

}
