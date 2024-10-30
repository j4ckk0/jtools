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

import com.jtools.data.provider.IDataClassProvider;
import com.jtools.mappings.editors.simple.SimpleMappingEditor;
import com.jtools.mappings.simple.SimpleMapping;
import com.jtools.utils.gui.editor.AEditorAction;

/**
 * @author j4ckk0
 *
 */
public class SimpleMappingCreateAction extends AEditorAction {

	private static final long serialVersionUID = 5655082933456528045L;
	
	private transient IDataClassProvider dataClassProvider;

	public SimpleMappingCreateAction(String name) {
		super(name);
	}

	public SimpleMappingCreateAction(String name, Icon icon) {
		super(name, icon);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if(dataClassProvider == null) {
			Logger.getLogger(getClass().getName()).log(Level.WARNING, "Data class provider not been set");
			JOptionPane.showMessageDialog(null, "No data class provider has been set", "No provider", JOptionPane.WARNING_MESSAGE);
			return;
		}

		Class<?> dataClass = dataClassProvider.getDataClass();

		try {
			SimpleMapping<?> mapping = new SimpleMapping<>(dataClass);
			
			SimpleMappingEditor<?> mappingEditor = new SimpleMappingEditor<>(mapping);
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
