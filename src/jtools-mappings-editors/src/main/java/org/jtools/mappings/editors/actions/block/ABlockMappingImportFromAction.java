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
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.Icon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.jtools.data.DataEditor;
import org.jtools.mappings.block.BlockMapping;
import org.jtools.mappings.block.importers.IBlockMappingImporter;
import org.jtools.mappings.block.io.BlockMappingFileManager;
import org.jtools.mappings.common.MappingException;
import org.jtools.utils.CommonUtils;
import org.jtools.utils.gui.editor.AEditorAction;
// TODO: Auto-generated Javadoc

/**
 * The Class ABlockMappingImportFromAction.
 */
public abstract class ABlockMappingImportFromAction extends AEditorAction {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -5347933034897416218L;

	/** The importer. */
	private transient IBlockMappingImporter importer;

	/** The block mapping. */
	private transient BlockMapping<?> blockMapping;

	/** The imported objects. */
	private transient List<?> importedObjects;

	/**
	 * Instantiates a new a block mapping import from action.
	 *
	 * @param name the name
	 * @param icon the icon
	 * @param importer the importer
	 */
	protected ABlockMappingImportFromAction(String name, Icon icon, IBlockMappingImporter importer) {
		super(name, icon);
		this.importer = importer;
	}

	/**
	 * Instantiates a new a block mapping import from action.
	 *
	 * @param name the name
	 * @param importer the importer
	 */
	protected ABlockMappingImportFromAction(String name, IBlockMappingImporter importer) {
		super(name);
		this.importer = importer;
	}

	/**
	 * Sets the mapping.
	 *
	 * @param mapping the new mapping
	 */
	public void setMapping(BlockMapping<?> mapping) {
		this.blockMapping = mapping;
	}

	/**
	 * Action performed.
	 *
	 * @param event the event
	 */
	@Override
	public void actionPerformed(ActionEvent event) {

		if(importer == null) {
			Logger.getLogger(getClass().getName()).log(Level.WARNING, "Objects importer has not been set");
			JOptionPane.showMessageDialog(null, "Objects importer has not been set", "No importer", JOptionPane.WARNING_MESSAGE);
			return;
		}

		try {
			if (blockMapping == null) {
				Logger.getLogger(getClass().getName()).log(Level.INFO, "No mapping defined. Load one");

				File choosenMappingFile = CommonUtils.chooseFile(JFileChooser.OPEN_DIALOG, new File("."), BlockMappingFileManager.LOAD_BLOCK_MAPPING_DIALOG_TITLE, BlockMappingFileManager.BLOCK_MAPPING_FILE_EXTENSION);

				blockMapping = BlockMappingFileManager.instance().loadMapping(choosenMappingFile.getAbsolutePath());
			}

			importedObjects = importer.importData(blockMapping.getObjectClass(), blockMapping);

			if(importedObjects != null) {
				DataEditor dataEditor = new DataEditor(importedObjects, blockMapping.getObjectClass());

				int confirm = JOptionPane.showConfirmDialog(null, "Do you want to open the data table ?", "Import succeed", JOptionPane.YES_NO_OPTION);
				if(confirm == JOptionPane.YES_OPTION) {
					showEditor(dataEditor);
				}
			}

		} catch (IOException | InstantiationException e) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage());
			Logger.getLogger(getClass().getName()).log(Level.FINE, e.getMessage(), e);
		} catch(MappingException e) {
			Logger.getLogger(getClass().getName()).log(Level.WARNING, e.getMessage());
			Logger.getLogger(getClass().getName()).log(Level.FINE, e.getMessage(), e);
			JOptionPane.showMessageDialog(null, e.getMessage(), "Bad mapping", JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Gets the imported objects.
	 *
	 * @return the imported objects
	 */
	public List<?> getImportedObjects() {
		return importedObjects;
	}

}
