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
import java.awt.event.KeyEvent;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
// TODO: Auto-generated Javadoc

/**
 * The Class AutoCompleteComboBox.
 */
public class AutoCompleteComboBox extends JComboBox<String> implements JComboBox.KeySelectionManager {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -790677937456857146L;

	/** The search for. */
	private String searchFor;
	
	/** The lap. */
	private long lap;
	
	/**
	 * Instantiates a new auto complete combo box.
	 */
	public AutoCompleteComboBox() {
		super();
		lap = new java.util.Date().getTime();
		setKeySelectionManager(this);
		JTextField tf;
		if (getEditor() != null) {
			tf = (JTextField) getEditor().getEditorComponent();
			if (tf != null) {
				tf.setDocument(new CBDocument());
				addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						JTextField tf = (JTextField) getEditor().getEditorComponent();
						String text = tf.getText();
						ComboBoxModel<String> aModel = getModel();
						String current;
						for (int i = 0; i < aModel.getSize(); i++) {
							current = aModel.getElementAt(i);
							if (current.toLowerCase().startsWith(text.toLowerCase())) {
								tf.setText(current);
								tf.setSelectionStart(text.length());
								tf.setSelectionEnd(current.length());
								break;
							}
						}
					}
				});
			}
		}
	}

	/**
	 * Sets the items.
	 *
	 * @param items the new items
	 */
	public void setItems(String[] items) {
		setModel(new DefaultComboBoxModel<String>(items));
	}

	/**
	 * Selection for key.
	 *
	 * @param aKey the a key
	 * @param aModel the a model
	 * @return the int
	 */
	public int selectionForKey(char aKey, ComboBoxModel<?> aModel) {
		long now = new java.util.Date().getTime();
		if (searchFor != null && aKey == KeyEvent.VK_BACK_SPACE && searchFor.length() > 0) {
			searchFor = searchFor.substring(0, searchFor.length() - 1);
		} else {
			if (lap + 1000 < now)
				searchFor = "" + aKey;
			else
				searchFor = searchFor + aKey;
		}
		lap = now;
		String current;
		for (int i = 0; i < aModel.getSize(); i++) {
			current = aModel.getElementAt(i).toString().toLowerCase();
			if (current.toLowerCase().startsWith(searchFor.toLowerCase()))
				return i;
		}
		return -1;
	}

	/**
	 * Fire action event.
	 */
	@Override
	public void fireActionEvent() {
		super.fireActionEvent();
	}
	
	/**
	 * The Class CBDocument.
	 */
	public class CBDocument extends PlainDocument {
		
		/** The Constant serialVersionUID. */
		private static final long serialVersionUID = 7985172243562749588L;

		/**
		 * Instantiates a new CB document.
		 */
		protected CBDocument() {
			super();
		}
		
		/**
		 * Insert string.
		 *
		 * @param offset the offset
		 * @param str the str
		 * @param a the a
		 * @throws BadLocationException the bad location exception
		 */
		@Override
		public void insertString(int offset, String str, AttributeSet a) throws BadLocationException {
			if (str == null)
				return;
			super.insertString(offset, str, a);
			if (!isPopupVisible() && str.length() != 0)
				fireActionEvent();
		}
	}
}
