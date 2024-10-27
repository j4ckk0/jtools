/**
 * 
 */
package com.jtools.mappings.editors.actions.block;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.Icon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import com.jtools.generic.data.provider.IDataProvider;
import com.jtools.mappings.block.BlockMapping;
import com.jtools.mappings.block.io.BlockMappingFileManager;
import com.jtools.mappings.common.MappingException;
import com.jtools.mappings.common.MappingUtils;
import com.jtools.mappings.editors.block.BlockMappingEditor;
import com.jtools.utils.CommonUtils;
import com.jtools.utils.gui.editor.AEditorAction;

/**
 * @author j4ckk0
 *
 */
public class BlockMappingLoadAction extends AEditorAction {

	private static final long serialVersionUID = -7300287893183273865L;
	
	private transient IDataProvider dataProvider;

	private String mappingFilepath;

	public BlockMappingLoadAction(String name, Icon icon) {
		super(name, icon);
		this.mappingFilepath = null;
	}

	public BlockMappingLoadAction(String name) {
		super(name);
		this.mappingFilepath = null;
	}

	public BlockMappingLoadAction(String name, Icon icon, String mappingsFilepath) {
		super(name, icon);
		this.mappingFilepath = mappingsFilepath;
	}

	public BlockMappingLoadAction(String name, String mappingsFilepath) {
		super(name);
		this.mappingFilepath = mappingsFilepath;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		String localMappingFilepath;
		if (this.mappingFilepath == null || this.mappingFilepath.length() == 0) {
			File mappingFile = CommonUtils.chooseFile(JFileChooser.OPEN_DIALOG, null, BlockMappingFileManager.LOAD_BLOCK_MAPPING_DIALOG_TITLE, BlockMappingFileManager.BLOCK_MAPPING_FILE_EXTENSION);
			if(mappingFile == null) {
				return;
			}
			localMappingFilepath = mappingFile.getAbsolutePath();
		} else {
			localMappingFilepath = this.mappingFilepath;
		}
		
		Class<?>[] additionalPossibleClasses = null;
		if(dataProvider != null) {
			additionalPossibleClasses = CommonUtils.classListToArray(dataProvider.getPossibleDataClasses());
		}

		try {
			BlockMapping<?> blockMapping = BlockMappingFileManager.instance().loadMapping(localMappingFilepath);
			
			BlockMappingEditor<?> mappingEditor = new BlockMappingEditor<>(blockMapping, MappingUtils.getPossibleColumns(), additionalPossibleClasses);
			showEditor(mappingEditor);
		} catch (InstantiationException | IOException e) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage());
			Logger.getLogger(getClass().getName()).log(Level.FINE, e.getMessage(), e);
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error while loading editor", JOptionPane.ERROR_MESSAGE);
		} catch(MappingException e) {
			Logger.getLogger(getClass().getName()).log(Level.WARNING, e.getMessage());
			Logger.getLogger(getClass().getName()).log(Level.FINE, e.getMessage(), e);
			JOptionPane.showMessageDialog(null, e.getMessage(), "Bad mapping", JOptionPane.ERROR_MESSAGE);
		}
	}

	public void setDataProvider(IDataProvider dataProvider) {
		this.dataProvider = dataProvider;
	}
}
