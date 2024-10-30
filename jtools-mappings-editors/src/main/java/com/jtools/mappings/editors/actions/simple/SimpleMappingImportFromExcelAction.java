/**
 * 
 */
package com.jtools.mappings.editors.actions.simple;

import javax.swing.Icon;

import com.jtools.mappings.simple.importers.SimpleMappingExcelImporter;

/**
 * @author j4ckk0
 *
 */
public class SimpleMappingImportFromExcelAction extends ASimpleMappingImportFromAction {

	private static final long serialVersionUID = 5366162869248310896L;

	public SimpleMappingImportFromExcelAction(String name, Icon icon) {
		super(name, icon, SimpleMappingExcelImporter.instance());
	}

	public SimpleMappingImportFromExcelAction(String name) {
		super(name, SimpleMappingExcelImporter.instance());
	}

}
