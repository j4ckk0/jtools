/**
 * 
 */
package org.jtools.data.actions;

/*-
 * #%L
 * Java Tools - Data
 * %%
 * Copyright (C) 2024 j4ckk0
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import java.awt.event.ActionEvent;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.Icon;
import javax.swing.JOptionPane;

import org.jtools.data.DataEditor;
import org.jtools.data.DataException;
import org.jtools.data.io.DataFileManager;
import org.jtools.utils.gui.editor.AEditorAction;

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
