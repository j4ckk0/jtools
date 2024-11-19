package org.jtools.data.actions;

/*-
 * #%L
 * Java Tools - Data
 * %%
 * Copyright (C) 2024 jtools.org
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

import javax.swing.Icon;

import org.jtools.data.DataEditor;
import org.jtools.utils.gui.editor.AEditorAction;
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
