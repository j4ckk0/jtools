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

import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import com.jtools.generic.data.provider.IDataProvider;
import com.jtools.mappings.common.MappingException;
import com.jtools.mappings.simple.SimpleMappingRow;
import com.jtools.mappings.simple.exporters.ASimpleMappingExporter;
import com.jtools.mappings.simple.io.SimpleMappingFileManager;
import com.jtools.utils.CommonUtils;

/**
 * @author j4ckk0
 *
 */
public abstract class ASimpleMappingExportToAction extends AbstractAction {

	private static final long serialVersionUID = -1660411517249745107L;

	private transient IDataProvider dataProvider;

	private transient String mappingFilepath;

	private transient ASimpleMappingExporter exporter;

	protected ASimpleMappingExportToAction(String name, Icon icon, ASimpleMappingExporter exporter) {
		super(name, icon);
		this.exporter = exporter;
		this.mappingFilepath = null;
	}

	protected ASimpleMappingExportToAction(String name, ASimpleMappingExporter exporter) {
		super(name);
		this.exporter = exporter;
		this.mappingFilepath = null;
	}

	protected ASimpleMappingExportToAction(String name, Icon icon, ASimpleMappingExporter exporter, String mappingsFilepath) {
		super(name, icon);
		this.exporter = exporter;
		this.mappingFilepath = mappingsFilepath;
	}

	protected ASimpleMappingExportToAction(String name, ASimpleMappingExporter exporter, String mappingsFilepath) {
		super(name);
		this.exporter = exporter;
		this.mappingFilepath = mappingsFilepath;
	}

	public void setDataProvider(IDataProvider dataProvider) {
		this.dataProvider = dataProvider;
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
		
		List<?> objectsToExport = dataProvider.getDataList();
		
		if (objectsToExport == null || objectsToExport.isEmpty()) {
			Logger.getLogger(getClass().getName()).log(Level.WARNING, "No data to export");
			JOptionPane.showMessageDialog(null, "No data to export", "No data", JOptionPane.WARNING_MESSAGE);
			return;
		}

		Object object = objectsToExport.iterator().next();
		Class<?> objectClass = object.getClass();
		
		String mappingFilepathLocal = this.mappingFilepath;
		if (mappingFilepathLocal == null || mappingFilepathLocal.length() == 0) {
			File defaultMappingFile = SimpleMappingFileManager.instance().getDefaultMappingFile(objectClass);
			File choosenMappingFile = CommonUtils.chooseFile(JFileChooser.OPEN_DIALOG, defaultMappingFile, SimpleMappingFileManager.LOAD_SIMPLE_MAPPING_DIALOG_TITLE, SimpleMappingFileManager.SIMPLE_MAPPING_FILE_EXTENSION);
			mappingFilepathLocal = choosenMappingFile.getAbsolutePath();
		}

		try {
			List<SimpleMappingRow> rows = SimpleMappingFileManager.instance().loadRows(objectClass, mappingFilepathLocal);

			exporter.exportData(objectsToExport, rows);
		} catch (IOException e) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage());
			Logger.getLogger(getClass().getName()).log(Level.FINE, e.getMessage(), e);
		} catch(MappingException e) {
			Logger.getLogger(getClass().getName()).log(Level.WARNING, e.getMessage());
			Logger.getLogger(getClass().getName()).log(Level.FINE, e.getMessage(), e);
			JOptionPane.showMessageDialog(null, e.getMessage(), "Bad mapping", JOptionPane.ERROR_MESSAGE);
		}

	}

}
