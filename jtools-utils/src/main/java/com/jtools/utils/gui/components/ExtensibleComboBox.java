/**
 * 
 */
package com.jtools.utils.gui.components;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;

/**
 * @author j4ckk0
 *
 */
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
