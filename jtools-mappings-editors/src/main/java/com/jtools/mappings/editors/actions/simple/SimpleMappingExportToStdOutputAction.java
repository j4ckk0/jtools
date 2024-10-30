/**
 * 
 */
package com.jtools.mappings.editors.actions.simple;

import javax.swing.Icon;

import com.jtools.mappings.simple.exporters.SimpleMappingStdOutputExporter;

/**
 * @author j4ckk0
 *
 */
public class SimpleMappingExportToStdOutputAction extends ASimpleMappingExportToAction {

	private static final long serialVersionUID = -5666508052700983450L;

	public SimpleMappingExportToStdOutputAction(String name, Icon icon) {
		super(name, icon, SimpleMappingStdOutputExporter.instance());
	}

	public SimpleMappingExportToStdOutputAction(String name) {
		super(name, SimpleMappingStdOutputExporter.instance());
	}

}
