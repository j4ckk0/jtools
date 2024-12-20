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
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.Icon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.jtools.mappings.block.BlockMapping;
import org.jtools.mappings.block.io.BlockMappingFileManager;
import org.jtools.mappings.common.MappingException;
import org.jtools.mappings.common.MappingUtils;
import org.jtools.mappings.editors.block.BlockMappingEditor;
import org.jtools.utils.CommonUtils;
import org.jtools.utils.gui.editor.AEditorAction;
// TODO: Auto-generated Javadoc

/**
 * The Class BlockMappingLoadAction.
 */
public class BlockMappingLoadAction extends AEditorAction {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -7300287893183273865L;

	/**
	 * Instantiates a new block mapping load action.
	 *
	 * @param name the name
	 * @param icon the icon
	 */
	public BlockMappingLoadAction(String name, Icon icon) {
		super(name, icon);
	}

	/**
	 * Instantiates a new block mapping load action.
	 *
	 * @param name the name
	 */
	public BlockMappingLoadAction(String name) {
		super(name);
	}

	/**
	 * Action performed.
	 *
	 * @param event the event
	 */
	@Override
	public void actionPerformed(ActionEvent event) {
		File choosenMappingFile = CommonUtils.chooseFile(JFileChooser.OPEN_DIALOG, new File("."), BlockMappingFileManager.LOAD_BLOCK_MAPPING_DIALOG_TITLE, BlockMappingFileManager.BLOCK_MAPPING_FILE_EXTENSION);

		try {
			BlockMapping<?> blockMapping = BlockMappingFileManager.instance().loadMapping(choosenMappingFile.getAbsolutePath());

			BlockMappingEditor<?> mappingEditor = new BlockMappingEditor<>(blockMapping, MappingUtils.getPossibleColumns());
			showEditor(mappingEditor);
		} catch (InstantiationException e) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage());
			Logger.getLogger(getClass().getName()).log(Level.FINE, e.getMessage(), e);
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error while loading editor", JOptionPane.ERROR_MESSAGE);
		} catch(MappingException e) {
			Logger.getLogger(getClass().getName()).log(Level.WARNING, e.getMessage());
			Logger.getLogger(getClass().getName()).log(Level.FINE, e.getMessage(), e);
			JOptionPane.showMessageDialog(null, e.getMessage(), "Bad mapping", JOptionPane.ERROR_MESSAGE);
		}  catch(Exception e) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage());
			Logger.getLogger(getClass().getName()).log(Level.FINE, e.getMessage(), e);
		}
	}
}
