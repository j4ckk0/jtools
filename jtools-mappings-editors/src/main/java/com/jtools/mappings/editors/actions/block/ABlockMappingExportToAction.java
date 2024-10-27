/**
 * 
 */
package com.jtools.mappings.editors.actions.block;

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

import com.jtools.data.provider.IDataProvider;
import com.jtools.mappings.block.BlockMapping;
import com.jtools.mappings.block.exporters.ABlockMappingExporter;
import com.jtools.mappings.block.io.BlockMappingFileManager;
import com.jtools.mappings.common.MappingException;
import com.jtools.utils.CommonUtils;

/**
 * @author j4ckk0
 *
 */
public abstract class ABlockMappingExportToAction extends AbstractAction {

	private static final long serialVersionUID = -1660411517249745107L;

	private transient IDataProvider dataProvider;

	private transient BlockMapping<?> blockMapping;

	private transient ABlockMappingExporter exporter;

	protected ABlockMappingExportToAction(String name, Icon icon, ABlockMappingExporter exporter) {
		super(name, icon);
		this.exporter = exporter;
	}

	protected ABlockMappingExportToAction(String name, ABlockMappingExporter exporter) {
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
