/**
 * 
 */
package org.jtools.mappings.editors.actions.simple;

/*-
 * #%L
 * Java Tools - Mappings Editors
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

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.jtools.data.provider.IDataProvider;
import org.jtools.mappings.common.MappingException;
import org.jtools.mappings.simple.SimpleMapping;
import org.jtools.mappings.simple.SimpleMappingRow;
import org.jtools.mappings.simple.exporters.ASimpleMappingExporter;
import org.jtools.mappings.simple.io.SimpleMappingFileManager;
import org.jtools.utils.CommonUtils;

/**
 * @author j4ckk0
 *
 */
public abstract class ASimpleMappingExportToAction extends AbstractAction {

	private static final long serialVersionUID = -1660411517249745107L;

	private transient IDataProvider dataProvider;

	private transient SimpleMapping<?> mapping;

	private transient ASimpleMappingExporter exporter;

	protected ASimpleMappingExportToAction(String name, Icon icon, ASimpleMappingExporter exporter) {
		super(name, icon);
		this.exporter = exporter;
	}

	protected ASimpleMappingExportToAction(String name, ASimpleMappingExporter exporter) {
		super(name);
		this.exporter = exporter;
	}

	public void setDataProvider(IDataProvider dataProvider) {
		this.dataProvider = dataProvider;
	}

	public void setMapping(SimpleMapping<?>  mapping) {
		this.mapping = mapping;
	}

	@Override
	public void actionPerformed(ActionEvent event) {

		if(dataProvider == null) {
			Logger.getLogger(getClass().getName()).log(Level.WARNING, "Data provider not been set");
			JOptionPane.showMessageDialog(null, "Data provider not been set", "No data", JOptionPane.WARNING_MESSAGE);
			return;
		}

		if (exporter == null) {
			Logger.getLogger(getClass().getName()).log(Level.WARNING, "Objects exporter has not been set");
			JOptionPane.showMessageDialog(null, "Objects exporter has not been set", "No exporter", JOptionPane.WARNING_MESSAGE);
			return;
		}

		try {

			if(mapping == null) {
				Logger.getLogger(getClass().getName()).log(Level.INFO, "No mapping defined. Load one");

				File choosenMappingFile = CommonUtils.chooseFile(JFileChooser.OPEN_DIALOG, new File("."), SimpleMappingFileManager.LOAD_SIMPLE_MAPPING_DIALOG_TITLE, SimpleMappingFileManager.SIMPLE_MAPPING_FILE_EXTENSION);

				mapping = SimpleMappingFileManager.instance().loadMapping(choosenMappingFile.getAbsolutePath());
			}

			Logger.getLogger(getClass().getName()).log(Level.INFO, "========");
		
			Logger.getLogger(getClass().getName()).log(Level.INFO, "Exporting data from provider: " + dataProvider.getProviderName() + " with mapping: " + mapping.getMappingName());
			
			List<SimpleMappingRow> rows = mapping.getRows();
			List<?> objectsToExport = dataProvider.getDataList();

			exporter.exportData(objectsToExport, rows);
			
			Logger.getLogger(getClass().getName()).log(Level.INFO, "=======");

		} catch (IOException | InstantiationException e) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage());
			Logger.getLogger(getClass().getName()).log(Level.FINE, e.getMessage(), e);
		} catch(MappingException e) {
			Logger.getLogger(getClass().getName()).log(Level.WARNING, e.getMessage());
			Logger.getLogger(getClass().getName()).log(Level.FINE, e.getMessage(), e);
			JOptionPane.showMessageDialog(null, e.getMessage(), "Bad mapping", JOptionPane.ERROR_MESSAGE);
		}

	}

}
