/**
 * 
 */
package com.jtools.mappings.editors.actions.block;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.Icon;
import javax.swing.JOptionPane;

import com.jtools.data.provider.IDataProvider;
import com.jtools.mappings.block.BlockMapping;
import com.jtools.mappings.common.MappingRegistry;
import com.jtools.mappings.common.MappingUtils;
import com.jtools.mappings.editors.block.BlockMappingEditor;
import com.jtools.utils.CommonUtils;
import com.jtools.utils.gui.editor.AEditorAction;

/**
 * @author j4ckk0
 *
 */
public class BlockMappingCreateAction extends AEditorAction {

	private static final long serialVersionUID = 5655082933456528045L;

	private transient IDataProvider dataProvider;

	public BlockMappingCreateAction(String name) {
		super(name);
	}

	public BlockMappingCreateAction(String name, Icon icon) {
		super(name, icon);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (dataProvider == null) {
			Logger.getLogger(getClass().getName()).log(Level.WARNING, "Data provider not been set");
			JOptionPane.showMessageDialog(null, "Data provider not been set", "No data", JOptionPane.WARNING_MESSAGE);
			return;
		}

		List<Class<?>> possibleDataClasses = dataProvider.getPossibleDataClasses();
		Class<?> dataClass = dataProvider.getDataClass();

		try {
			BlockMapping<?> mapping = new BlockMapping<>(dataClass);
			
			MappingRegistry.instance().registerBlockMapping(mapping);

			BlockMappingEditor<?> mappingEditor = new BlockMappingEditor<>(mapping, MappingUtils.getPossibleColumns(), CommonUtils.classListToArray(possibleDataClasses));
			showEditor(mappingEditor);
		} catch (IOException ex) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, ex.getMessage(), ex);
		}
	}

	public void setDataProvider(IDataProvider dataProvider) {
		this.dataProvider = dataProvider;
	}
}
