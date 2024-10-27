/**
 * 
 */
package com.jtools.data.actions;

import java.awt.event.ActionEvent;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.Icon;
import javax.swing.JOptionPane;

import com.jtools.data.DataEditor;
import com.jtools.data.DataException;
import com.jtools.data.io.DataFileManager;
import com.jtools.data.provider.DataProviderRegistry;
import com.jtools.utils.gui.editor.AEditorAction;

/**
 * @author j4ckk0
 *
 */
public class LoadDataAction extends AEditorAction {

	private static final long serialVersionUID = -1709026773373353372L;

	private final Class<?>[] dataClasses;

	private boolean showEditor;

	public LoadDataAction(String name, Icon icon, Class<?>[] dataClasses) {
		super(name, icon);
		this.dataClasses = dataClasses;
		this.showEditor = true;
	}

	public LoadDataAction(String name, Class<?>[] dataClasses) {
		super(name);
		this.dataClasses = dataClasses;
		this.showEditor = true;
	}

	public void setShowEditor(boolean showEditor) {
		this.showEditor = showEditor;
	}

	@Override
	public void actionPerformed(ActionEvent event) {

		try {
			List<?> dataList = DataFileManager.instance().load();

			if(showEditor) {
				DataEditor dataEditor = new DataEditor(dataList, dataClasses);
				
				DataProviderRegistry.instance().register(dataEditor);

				showEditor(dataEditor);
			}

		} catch (InstantiationException e) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage());
			Logger.getLogger(getClass().getName()).log(Level.FINE, e.getMessage(), e);
		} catch(DataException e) {
			Logger.getLogger(getClass().getName()).log(Level.WARNING, e.getMessage());
			Logger.getLogger(getClass().getName()).log(Level.FINE, e.getMessage(), e);
			JOptionPane.showMessageDialog(null, e.getMessage(), "Bad data", JOptionPane.ERROR_MESSAGE);
		}
	}
}
