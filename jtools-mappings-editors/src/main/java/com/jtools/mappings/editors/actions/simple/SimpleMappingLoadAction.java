/**
 * 
 */
package com.jtools.mappings.editors.actions.simple;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
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

	private String mappingFilepath;

	public SimpleMappingLoadAction(String name, Icon icon) {
		super(name, icon);
		this.mappingFilepath = null;
	}

	public SimpleMappingLoadAction(String name) {
		super(name);
		this.mappingFilepath = null;
	}

	public SimpleMappingLoadAction(String name, Icon icon, String mappingsFilepath) {
		super(name, icon);
		this.mappingFilepath = mappingsFilepath;
	}

	public SimpleMappingLoadAction(String name, String mappingsFilepath) {
		super(name);
		this.mappingFilepath = mappingsFilepath;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		String localMappingFilepath;
		if (this.mappingFilepath == null || this.mappingFilepath.length() == 0) {
			File mappingFile = CommonUtils.chooseFile(JFileChooser.OPEN_DIALOG, null, SimpleMappingFileManager.SAVE_SIMPLE_MAPPING_DIALOG_TITLE, SimpleMappingFileManager.SIMPLE_MAPPING_FILE_EXTENSION);
			if(mappingFile == null) {
				return;
			}
			localMappingFilepath = mappingFile.getAbsolutePath();
		} else {
			localMappingFilepath = this.mappingFilepath;
		}

		try {
			SimpleMapping<?> simpleMapping = SimpleMappingFileManager.instance().loadMapping(localMappingFilepath);
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
		} catch (IOException ex) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, ex.getMessage(), ex);
		}

	}
}
