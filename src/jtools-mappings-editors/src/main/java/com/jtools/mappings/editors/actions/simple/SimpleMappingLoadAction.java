/**
 * 
 */
package com.jtools.mappings.editors.actions.simple;

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
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.Icon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import com.jtools.mappings.common.MappingException;
import com.jtools.mappings.editors.simple.SimpleMappingEditor;
import com.jtools.mappings.simple.SimpleMapping;
import com.jtools.mappings.simple.io.SimpleMappingFileManager;
import com.jtools.utils.CommonUtils;
import com.jtools.utils.gui.editor.AEditorAction;

/**
 * @author j4ckk0
 *
 */
public class SimpleMappingLoadAction extends AEditorAction {

	private static final long serialVersionUID = -7300287893183273865L;

	public SimpleMappingLoadAction(String name, Icon icon) {
		super(name, icon);
	}

	public SimpleMappingLoadAction(String name) {
		super(name);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		File choosenMappingFile = CommonUtils.chooseFile(JFileChooser.OPEN_DIALOG, new File("."), SimpleMappingFileManager.LOAD_SIMPLE_MAPPING_DIALOG_TITLE, SimpleMappingFileManager.SIMPLE_MAPPING_FILE_EXTENSION);

		try {
			SimpleMapping<?> simpleMapping = SimpleMappingFileManager.instance().loadMapping(choosenMappingFile.getAbsolutePath());
			SimpleMappingEditor<?> mappingEditor = new SimpleMappingEditor<>(simpleMapping);
			showEditor(mappingEditor);
		} catch (InstantiationException e) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage());
			Logger.getLogger(getClass().getName()).log(Level.FINE, e.getMessage(), e);
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error while loading editor", JOptionPane.ERROR_MESSAGE);
		} catch(MappingException e) {
			Logger.getLogger(getClass().getName()).log(Level.WARNING, e.getMessage());
			Logger.getLogger(getClass().getName()).log(Level.FINE, e.getMessage(), e);
			JOptionPane.showMessageDialog(null, e.getMessage(), "Bad mapping", JOptionPane.ERROR_MESSAGE);
		} catch (Exception ex) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, ex.getMessage());
			Logger.getLogger(getClass().getName()).log(Level.FINE, ex.getMessage(), ex);
		}

	}
}