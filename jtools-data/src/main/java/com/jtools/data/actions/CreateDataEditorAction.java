/**
 * 
 */
package com.jtools.data.actions;

import java.awt.event.ActionEvent;

import javax.swing.Icon;

import com.jtools.data.DataEditor;
import com.jtools.utils.gui.editor.AEditorAction;

/**
 * @author j4ckk0
 *
 */
public class CreateDataEditorAction extends AEditorAction {

	private static final long serialVersionUID = -8300069996813450384L;

	private final Class<?>[] dataClasses;

	public CreateDataEditorAction(String name, Icon icon, Class<?>[] dataClasses) {
		super(name, icon);
		this.dataClasses = dataClasses;
	}

	public CreateDataEditorAction(String name, Class<?>[] dataClasses) {
		super(name);
		this.dataClasses = dataClasses;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		DataEditor dataEditor = new DataEditor(dataClasses);

		showEditor(dataEditor);
	}

}
