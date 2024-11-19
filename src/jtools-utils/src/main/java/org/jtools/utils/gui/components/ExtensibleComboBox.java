package org.jtools.utils.gui.components;

/*-
 * #%L
 * Java Tools - Utils
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
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;
public class ExtensibleComboBox extends AutoCompleteComboBox {

	private static final long serialVersionUID = -8806799253651420722L;

	public ExtensibleComboBox() {
		JTextField tf;
		if (getEditor() != null) {
			tf = (JTextField) getEditor().getEditorComponent();
			if (tf != null) {
				tf.setDocument(new CBDocument());
				addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						if (actionCommand.equals("comboBoxEdited")) {
							String currentValue = tf.getText();
							DefaultComboBoxModel<String> model = (DefaultComboBoxModel<String>) getModel();
							if (model.getIndexOf(currentValue) == -1) {
								model.addElement(currentValue);
							}
						}
					}
				});
			}
		}
	}
}
