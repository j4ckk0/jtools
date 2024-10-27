/**
 * 
 */
package com.jtools.mappings.editors.actions.simple;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.Icon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import com.jtools.data.DataEditor;
import com.jtools.mappings.common.MappingException;
import com.jtools.mappings.simple.SimpleMapping;
import com.jtools.mappings.simple.importers.ASimpleMappingImporter;
import com.jtools.mappings.simple.io.SimpleMappingFileManager;
import com.jtools.utils.CommonUtils;
import com.jtools.utils.gui.editor.AEditorAction;

/**
 * @author j4ckk0
 *
 */
public abstract class ASimpleMappingImportFromAction extends AEditorAction {

	private static final long serialVersionUID = -5347933034897416218L;

	private String mappingFilepath;

	private transient ASimpleMappingImporter importer;

	private transient List<?> importedObjects;

	protected ASimpleMappingImportFromAction(String name, Icon icon, ASimpleMappingImporter importer) {
		super(name, icon);
		this.importer = importer;
		this.mappingFilepath = null;
	}

	protected ASimpleMappingImportFromAction(String name, ASimpleMappingImporter importer) {
		super(name);
		this.importer = importer;
		this.mappingFilepath = null;
	}

	protected ASimpleMappingImportFromAction(String name, Icon icon, ASimpleMappingImporter importer, String mappingsFilepath) {
		super(name, icon);
		this.importer = importer;
		this.mappingFilepath = mappingsFilepath;
	}

	protected ASimpleMappingImportFromAction(String name, ASimpleMappingImporter importer, String mappingsFilepath) {
		super(name);
		this.importer = importer;
		this.mappingFilepath = mappingsFilepath;
	}

	@Override
	public void actionPerformed(ActionEvent event) {

		if(importer == null) {
			Logger.getLogger(getClass().getName()).log(Level.WARNING, "Objects importer has not been set");
			JOptionPane.showMessageDialog(null, "Objects importer has not been set", "No importer", JOptionPane.WARNING_MESSAGE);
			return;
		}

		String localMappingFilepath;
		if (this.mappingFilepath == null || this.mappingFilepath.length() == 0) {
			File mappingFile = CommonUtils.chooseFile(JFileChooser.OPEN_DIALOG, null, SimpleMappingFileManager.LOAD_SIMPLE_MAPPING_DIALOG_TITLE, SimpleMappingFileManager.SIMPLE_MAPPING_FILE_EXTENSION);
			if(mappingFile == null) {
				return;
			}
			localMappingFilepath = mappingFile.getAbsolutePath();
		} else {
			localMappingFilepath = this.mappingFilepath;
		}

		try {
			SimpleMapping<Object> simpleMapping = SimpleMappingFileManager.instance().loadMapping(localMappingFilepath);

			importedObjects = importer.importData(simpleMapping.getObjectClass(), simpleMapping.getMappingRows());

			if(importedObjects != null) {
				DataEditor dataEditor = new DataEditor(importedObjects, simpleMapping.getObjectClass());
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

	public List<?> getImportedObjects() {
		return importedObjects;
	}

}
