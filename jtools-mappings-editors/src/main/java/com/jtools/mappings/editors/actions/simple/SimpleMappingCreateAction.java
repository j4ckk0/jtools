/**
 * 
 */
package com.jtools.mappings.editors.actions.simple;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.Icon;
import javax.swing.JOptionPane;

import com.jtools.data.provider.IDataProvider;
import com.jtools.mappings.common.MappingRegistry;
import com.jtools.mappings.editors.simple.SimpleMappingEditor;
import com.jtools.mappings.simple.SimpleMapping;
import com.jtools.utils.gui.editor.AEditorAction;

/**
 * @author j4ckk0
 *
 */
public class SimpleMappingCreateAction extends AEditorAction {

	private static final long serialVersionUID = 5655082933456528045L;
	
	private transient IDataProvider dataProvider;

	public SimpleMappingCreateAction(String name) {
		super(name);
	}

	public SimpleMappingCreateAction(String name, Icon icon) {
		super(name, icon);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if(dataProvider == null) {
			Logger.getLogger(getClass().getName()).log(Level.WARNING, "Data provider not been set");
			JOptionPane.showMessageDialog(null, "Data provider not been set", "No data", JOptionPane.WARNING_MESSAGE);
			return;
		}

		Class<?> dataClass = dataProvider.getDataClass();

		try {
			SimpleMapping<?> mapping = new SimpleMapping<>(dataClass);
			
			MappingRegistry.instance().registerSimpleMapping(mapping);
			
			SimpleMappingEditor<?> mappingEditor = new SimpleMappingEditor<>(mapping);
			showEditor(mappingEditor);
		} catch (IOException ex) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, ex.getMessage(), ex);
		}
	}

	public void setDataProvider(IDataProvider dataProvider) {
		this.dataProvider = dataProvider;
	}
}
