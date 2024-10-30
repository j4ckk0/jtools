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

import com.jtools.data.provider.IDataClassProvider;
import com.jtools.mappings.block.BlockMapping;
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

	private transient IDataClassProvider dataClassProvider;

	public BlockMappingCreateAction(String name) {
		super(name);
	}

	public BlockMappingCreateAction(String name, Icon icon) {
		super(name, icon);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (dataClassProvider == null) {
			Logger.getLogger(getClass().getName()).log(Level.WARNING, "Data class provider not been set");
			JOptionPane.showMessageDialog(null, "No data class provider has been set", "No provider", JOptionPane.WARNING_MESSAGE);
			return;
		}

		List<Class<?>> possibleDataClasses = dataClassProvider.getPossibleDataClasses();
		Class<?> dataClass = dataClassProvider.getDataClass();

		try {
			BlockMapping<?> mapping = new BlockMapping<>(dataClass);

			BlockMappingEditor<?> mappingEditor = new BlockMappingEditor<>(mapping, MappingUtils.getPossibleColumns(), CommonUtils.classListToArray(possibleDataClasses));
			showEditor(mappingEditor);
		} catch (IOException ex) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, ex.getMessage());
			Logger.getLogger(getClass().getName()).log(Level.FINE, ex.getMessage(), ex);
		}
	}

	public void setDataClassProvider(IDataClassProvider dataProvider) {
		this.dataClassProvider = dataProvider;
	}
}
