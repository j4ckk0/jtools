package org.jtools.mappings.editors.actions.block;

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

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.jtools.data.provider.IDataProvider;
import org.jtools.mappings.block.BlockMapping;
import org.jtools.mappings.block.exporters.IBlockMappingExporter;
import org.jtools.mappings.block.io.BlockMappingFileManager;
import org.jtools.mappings.common.MappingException;
import org.jtools.utils.CommonUtils;
public abstract class ABlockMappingExportToAction extends AbstractAction {

	private static final long serialVersionUID = -1660411517249745107L;

	private transient IDataProvider dataProvider;

	private transient BlockMapping<?> blockMapping;

	private transient IBlockMappingExporter exporter;

	protected ABlockMappingExportToAction(String name, Icon icon, IBlockMappingExporter exporter) {
		super(name, icon);
		this.exporter = exporter;
	}

	protected ABlockMappingExportToAction(String name, IBlockMappingExporter exporter) {
		super(name);
		this.exporter = exporter;
	}

	public void setDataProvider(IDataProvider dataProvider) {
		this.dataProvider = dataProvider;
	}

	public void setMapping(BlockMapping<?> mapping) {
		this.blockMapping = mapping;
	}

	@Override
	public void actionPerformed(ActionEvent event) {

		if (dataProvider == null) {
			Logger.getLogger(getClass().getName()).log(Level.WARNING, "Data provider has not been set");
			JOptionPane.showMessageDialog(null, "Data provider has not been set", "No data",
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		if (exporter == null) {
			Logger.getLogger(getClass().getName()).log(Level.WARNING, "Objects exporter has not been set");
			JOptionPane.showMessageDialog(null, "Objects exporter has not been set", "No exporter",
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		try {

			if (blockMapping == null) {
				Logger.getLogger(getClass().getName()).log(Level.INFO, "No mapping defined. Load one");

				File choosenMappingFile = CommonUtils.chooseFile(JFileChooser.OPEN_DIALOG, new File("."), BlockMappingFileManager.LOAD_BLOCK_MAPPING_DIALOG_TITLE, BlockMappingFileManager.BLOCK_MAPPING_FILE_EXTENSION);

				blockMapping = BlockMappingFileManager.instance().loadMapping(choosenMappingFile.getAbsolutePath());
			}

			Map<Class<?>, List<?>> dataMap = dataProvider.getDataMap();

			List<?> data = dataMap.get(blockMapping.getObjectClass());

			if (data != null) {
				exporter.exportData(data, blockMapping);
			} else {
				Logger.getLogger(getClass().getName()).log(Level.WARNING,
						"No data of type " + blockMapping.getObjectClass());
				JOptionPane.showMessageDialog(null, "No data of type " + blockMapping.getObjectClass(), "Bad mapping",
						JOptionPane.ERROR_MESSAGE);
			}

		} catch (IOException | InstantiationException e) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage());
			Logger.getLogger(getClass().getName()).log(Level.FINE, e.getMessage(), e);
		} catch (MappingException e) {
			Logger.getLogger(getClass().getName()).log(Level.WARNING, e.getMessage());
			Logger.getLogger(getClass().getName()).log(Level.FINE, e.getMessage(), e);
			JOptionPane.showMessageDialog(null, e.getMessage(), "Bad mapping", JOptionPane.ERROR_MESSAGE);
		}

	}

}
